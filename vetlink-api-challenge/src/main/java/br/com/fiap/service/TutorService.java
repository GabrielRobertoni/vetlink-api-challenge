package br.com.fiap.service;

import br.com.fiap.dto.TutorDTO;
import br.com.fiap.entity.Tutor;
import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.repository.TutorRepository;
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

public class TutorService {

    private final TutorRepository tutorRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "tutores", key = "#id")
    public TutorDTO findById(Long id) {

        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tutor não encontrado com ID: " + id
                        )
                );

        return convertToDTO(tutor);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "tutores", key = "#cpf")
    public TutorDTO findByCpf(String cpf) {

        Tutor tutor = tutorRepository.findByCpf(cpf)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tutor não encontrado com CPF: " + cpf
                        )
                );

        return convertToDTO(tutor);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "tutoresPage",
            key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort"
    )
    public Page<TutorDTO> findAll(Pageable pageable) {

        return tutorRepository
                .findAll(pageable)
                .map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "tutoresByNome",
            key = "#nome + '-' + #pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public Page<TutorDTO> findByNome(
            String nome,
            Pageable pageable
    ) {

        return tutorRepository
                .findByNomeContainingIgnoreCase(nome, pageable)
                .map(this::convertToDTO);
    }

    @CacheEvict(
            value = {
                    "tutores",
                    "tutoresPage",
                    "tutoresByNome"
            },
            allEntries = true
    )
    public TutorDTO create(TutorDTO tutorDTO) {

        if (tutorRepository.findByCpf(
                tutorDTO.getCpf()).isPresent()) {

            throw new IllegalArgumentException(
                    "CPF já cadastrado: "
                            + tutorDTO.getCpf()
            );
        }

        Tutor tutor = convertToEntity(tutorDTO);

        Tutor tutorSalvo =
                tutorRepository.save(tutor);

        return convertToDTO(tutorSalvo);
    }

    @CacheEvict(
            value = {
                    "tutores",
                    "tutoresPage",
                    "tutoresByNome"
            },
            allEntries = true
    )
    public TutorDTO update(
            Long id,
            TutorDTO tutorDTO
    ) {

        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tutor não encontrado com ID: " + id
                        )
                );

        tutor.setNome(tutorDTO.getNome());
        tutor.setTelefone(tutorDTO.getTelefone());
        tutor.setDataNascimento(tutorDTO.getDataNascimento());
        tutor.setSexo(tutorDTO.getSexo());
        tutor.setStatus(tutorDTO.getStatus());

        Tutor tutorAtualizado =
                tutorRepository.save(tutor);

        return convertToDTO(tutorAtualizado);
    }

    @CacheEvict(
            value = {
                    "tutores",
                    "tutoresPage",
                    "tutoresByNome"
            },
            allEntries = true
    )
    public void delete(Long id) {

        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tutor não encontrado com ID: " + id
                        )
                );

        tutorRepository.delete(tutor);
    }

    private TutorDTO convertToDTO(Tutor tutor) {

        return TutorDTO.builder()
                .id(tutor.getId())
                .nome(tutor.getNome())
                .cpf(tutor.getCpf())
                .telefone(tutor.getTelefone())
                .dataNascimento(tutor.getDataNascimento())
                .sexo(tutor.getSexo())
                .status(tutor.getStatus())
                .build();
    }

    private Tutor convertToEntity(TutorDTO dto) {

        return Tutor.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .telefone(dto.getTelefone())
                .dataNascimento(dto.getDataNascimento())
                .sexo(dto.getSexo())
                .status(dto.getStatus())
                .build();
    }
}