package br.com.fiap.service;

import br.com.fiap.dto.ClinicaDTO;
import br.com.fiap.entity.Clinica;
import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.repository.ClinicaRepository;
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

public class ClinicaService {

    private final ClinicaRepository clinicaRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "clinicas", key = "#id")
    public ClinicaDTO findById(Long id) {

        Clinica clinica = clinicaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Clínica não encontrada com ID: " + id
                        )
                );

        return convertToDTO(clinica);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "clinicasPage",
            key = "#pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public Page<ClinicaDTO> findAll(Pageable pageable) {

        return clinicaRepository
                .findAll(pageable)
                .map(this::convertToDTO);
    }

    @CacheEvict(
            value = {
                    "clinicas",
                    "clinicasPage"
            },
            allEntries = true
    )
    public ClinicaDTO create(ClinicaDTO clinicaDTO) {

        if (clinicaRepository.findByCnpj(
                clinicaDTO.getCnpj()).isPresent()) {

            throw new IllegalArgumentException(
                    "CNPJ já cadastrado: "
                            + clinicaDTO.getCnpj()
            );
        }

        Clinica clinica = convertToEntity(clinicaDTO);

        Clinica clinicaSalva =
                clinicaRepository.save(clinica);

        return convertToDTO(clinicaSalva);
    }

    @CacheEvict(
            value = {
                    "clinicas",
                    "clinicasPage"
            },
            allEntries = true
    )
    public ClinicaDTO update(
            Long id,
            ClinicaDTO clinicaDTO
    ) {

        Clinica clinica = clinicaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Clínica não encontrada com ID: " + id
                        )
                );

        clinica.setTelefone(clinicaDTO.getTelefone());
        clinica.setEndereco(clinicaDTO.getEndereco());
        clinica.setNotaClinica(clinicaDTO.getNotaClinica());

        Clinica clinicaAtualizada =
                clinicaRepository.save(clinica);

        return convertToDTO(clinicaAtualizada);
    }

    @CacheEvict(
            value = {
                    "clinicas",
                    "clinicasPage"
            },
            allEntries = true
    )
    public void delete(Long id) {

        Clinica clinica = clinicaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Clínica não encontrada com ID: " + id
                        )
                );

        clinicaRepository.delete(clinica);
    }

    private ClinicaDTO convertToDTO(Clinica clinica) {

        return ClinicaDTO.builder()
                .id(clinica.getId())
                .cnpj(clinica.getCnpj())
                .telefone(clinica.getTelefone())
                .endereco(clinica.getEndereco())
                .notaClinica(clinica.getNotaClinica())
                .build();
    }

    private Clinica convertToEntity(ClinicaDTO dto) {

        return Clinica.builder()
                .id(dto.getId())
                .cnpj(dto.getCnpj())
                .telefone(dto.getTelefone())
                .endereco(dto.getEndereco())
                .notaClinica(dto.getNotaClinica())
                .build();
    }
}