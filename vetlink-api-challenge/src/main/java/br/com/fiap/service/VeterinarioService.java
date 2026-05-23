package br.com.fiap.service;

import br.com.fiap.dto.VeterinarioDTO;
import br.com.fiap.entity.Clinica;
import br.com.fiap.entity.Veterinario;
import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.repository.ClinicaRepository;
import br.com.fiap.repository.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional

public class VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;
    private final ClinicaRepository clinicaRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "veterinarios", key = "#id")
    public VeterinarioDTO findById(Long id) {

        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Veterinário não encontrado com ID: " + id
                        )
                );

        return convertToDTO(veterinario);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "veterinariosPage",
            key = "#pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public Page<VeterinarioDTO> findAll(Pageable pageable) {

        return veterinarioRepository
                .findAll(pageable)
                .map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "veterinariosByClinica",
            key = "#clinicaId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public Page<VeterinarioDTO> findByClinicaId(
            Long clinicaId,
            Pageable pageable
    ) {

        return veterinarioRepository
                .findByClinicaId(clinicaId, pageable)
                .map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "veterinariosByNome",
            key = "#nome + '-' + #pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public Page<VeterinarioDTO> findByNome(
            String nome,
            Pageable pageable
    ) {

        return veterinarioRepository
                .findByNomeContainingIgnoreCase(nome, pageable)
                .map(this::convertToDTO);
    }

    @CacheEvict(
            value = {
                    "veterinarios",
                    "veterinariosPage",
                    "veterinariosByClinica",
                    "veterinariosByNome"
            },
            allEntries = true
    )
    public VeterinarioDTO create(
            VeterinarioDTO veterinarioDTO
    ) {

        if (veterinarioRepository.findByCrmv(
                veterinarioDTO.getCrmv()).isPresent()) {

            throw new IllegalArgumentException(
                    "CRMV já cadastrado: "
                            + veterinarioDTO.getCrmv()
            );
        }

        Clinica clinica = clinicaRepository.findById(
                        veterinarioDTO.getClinicaId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Clínica não encontrada com ID: "
                                        + veterinarioDTO.getClinicaId()
                        )
                );

        Veterinario veterinario = convertToEntity(
                veterinarioDTO,
                clinica
        );

        Veterinario veterinarioSalvo =
                veterinarioRepository.save(veterinario);

        return convertToDTO(veterinarioSalvo);
    }

    @CacheEvict(
            value = {
                    "veterinarios",
                    "veterinariosPage",
                    "veterinariosByClinica",
                    "veterinariosByNome"
            },
            allEntries = true
    )
    public VeterinarioDTO update(
            Long id,
            VeterinarioDTO veterinarioDTO
    ) {

        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Veterinário não encontrado com ID: " + id
                        )
                );

        veterinario.setNome(veterinarioDTO.getNome());
        veterinario.setEspecialidade(veterinarioDTO.getEspecialidade());
        veterinario.setTelefone(veterinarioDTO.getTelefone());

        Veterinario veterinarioAtualizado =
                veterinarioRepository.save(veterinario);

        return convertToDTO(veterinarioAtualizado);
    }

    @CacheEvict(
            value = {
                    "veterinarios",
                    "veterinariosPage",
                    "veterinariosByClinica",
                    "veterinariosByNome"
            },
            allEntries = true
    )
    public void delete(Long id) {

        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Veterinário não encontrado com ID: " + id
                        )
                );

        veterinarioRepository.delete(veterinario);
    }

    private VeterinarioDTO convertToDTO(
            Veterinario veterinario
    ) {

        return VeterinarioDTO.builder()
                .id(veterinario.getId())
                .nome(veterinario.getNome())
                .crmv(veterinario.getCrmv())
                .especialidade(veterinario.getEspecialidade())
                .telefone(veterinario.getTelefone())
                .clinicaId(veterinario.getClinica().getId())
                .build();
    }

    private Veterinario convertToEntity(
            VeterinarioDTO dto,
            Clinica clinica
    ) {

        return Veterinario.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .crmv(dto.getCrmv())
                .especialidade(dto.getEspecialidade())
                .telefone(dto.getTelefone())
                .clinica(clinica)
                .build();
    }
}