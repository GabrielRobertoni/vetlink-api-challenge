package br.com.fiap.service;

import br.com.fiap.dto.MedicamentoDTO;
import br.com.fiap.entity.Medicamento;
import br.com.fiap.entity.Pet;
import br.com.fiap.entity.Tutor;
import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.repository.MedicamentoRepository;
import br.com.fiap.repository.PetRepository;
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

public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;
    private final PetRepository petRepository;
    private final TutorRepository tutorRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "medicamentos", key = "#id")
    public MedicamentoDTO findById(Long id) {

        Medicamento medicamento = medicamentoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Medicamento não encontrado com ID: " + id
                        )
                );

        return convertToDTO(medicamento);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "medicamentosPage",
            key = "#pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public Page<MedicamentoDTO> findAll(Pageable pageable) {

        return medicamentoRepository
                .findAll(pageable)
                .map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "medicamentosByPet",
            key = "#petId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public Page<MedicamentoDTO> findByPetId(
            Long petId,
            Pageable pageable
    ) {

        return medicamentoRepository
                .findByPetId(petId, pageable)
                .map(this::convertToDTO);
    }

    @CacheEvict(
            value = {
                    "medicamentos",
                    "medicamentosPage",
                    "medicamentosByPet"
            },
            allEntries = true
    )
    public MedicamentoDTO create(
            MedicamentoDTO medicamentoDTO
    ) {

        Pet pet = petRepository.findById(
                        medicamentoDTO.getPetId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pet não encontrado com ID: "
                                        + medicamentoDTO.getPetId()
                        )
                );

        Tutor tutor = tutorRepository.findById(
                        medicamentoDTO.getTutorId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tutor não encontrado com ID: "
                                        + medicamentoDTO.getTutorId()
                        )
                );

        Medicamento medicamento = convertToEntity(
                medicamentoDTO,
                pet,
                tutor
        );

        Medicamento medicamentoSalvo =
                medicamentoRepository.save(medicamento);

        return convertToDTO(medicamentoSalvo);
    }

    @CacheEvict(
            value = {
                    "medicamentos",
                    "medicamentosPage",
                    "medicamentosByPet"
            },
            allEntries = true
    )
    public MedicamentoDTO update(
            Long id,
            MedicamentoDTO medicamentoDTO
    ) {

        Medicamento medicamento = medicamentoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Medicamento não encontrado com ID: " + id
                        )
                );

        medicamento.setNome(medicamentoDTO.getNome());
        medicamento.setDosagem(medicamentoDTO.getDosagem());
        medicamento.setFrequencia(medicamentoDTO.getFrequencia());
        medicamento.setObservacoes(medicamentoDTO.getObservacoes());

        Medicamento medicamentoAtualizado =
                medicamentoRepository.save(medicamento);

        return convertToDTO(medicamentoAtualizado);
    }

    @CacheEvict(
            value = {
                    "medicamentos",
                    "medicamentosPage",
                    "medicamentosByPet"
            },
            allEntries = true
    )
    public void delete(Long id) {

        Medicamento medicamento = medicamentoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Medicamento não encontrado com ID: " + id
                        )
                );

        medicamentoRepository.delete(medicamento);
    }

    private MedicamentoDTO convertToDTO(
            Medicamento medicamento
    ) {

        return MedicamentoDTO.builder()
                .id(medicamento.getId())
                .nome(medicamento.getNome())
                .dosagem(medicamento.getDosagem())
                .frequencia(medicamento.getFrequencia())
                .observacoes(medicamento.getObservacoes())
                .petId(medicamento.getPet().getId())
                .tutorId(medicamento.getTutor().getId())
                .build();
    }

    private Medicamento convertToEntity(
            MedicamentoDTO dto,
            Pet pet,
            Tutor tutor
    ) {

        return Medicamento.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .dosagem(dto.getDosagem())
                .frequencia(dto.getFrequencia())
                .observacoes(dto.getObservacoes())
                .pet(pet)
                .tutor(tutor)
                .build();
    }
}