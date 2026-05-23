package br.com.fiap.service;

import br.com.fiap.dto.PetDTO;
import br.com.fiap.entity.Pet;
import br.com.fiap.entity.Tutor;
import br.com.fiap.exception.ResourceNotFoundException;
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

public class PetService {

    private final PetRepository petRepository;
    private final TutorRepository tutorRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "pets", key = "#id")
    public PetDTO findById(Long id) {

        Pet pet = petRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pet não encontrado com ID: " + id
                        )
                );

        return convertToDTO(pet);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "petsPage",
            key = "#pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public Page<PetDTO> findAll(Pageable pageable) {

        return petRepository
                .findAll(pageable)
                .map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "petsByTutor",
            key = "#tutorId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public Page<PetDTO> findByTutorId(
            Long tutorId,
            Pageable pageable
    ) {

        return petRepository
                .findByTutorId(tutorId, pageable)
                .map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "petsByNome",
            key = "#nome + '-' + #pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public Page<PetDTO> findByNome(
            String nome,
            Pageable pageable
    ) {

        return petRepository
                .findByNomeContainingIgnoreCase(nome, pageable)
                .map(this::convertToDTO);
    }

    @CacheEvict(
            value = {
                    "pets",
                    "petsPage",
                    "petsByTutor",
                    "petsByNome"
            },
            allEntries = true
    )
    public PetDTO create(PetDTO petDTO) {

        Tutor tutor = tutorRepository.findById(
                        petDTO.getTutorId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tutor não encontrado com ID: "
                                        + petDTO.getTutorId()
                        )
                );

        Pet pet = convertToEntity(
                petDTO,
                tutor
        );

        Pet petSalvo =
                petRepository.save(pet);

        return convertToDTO(petSalvo);
    }

    @CacheEvict(
            value = {
                    "pets",
                    "petsPage",
                    "petsByTutor",
                    "petsByNome"
            },
            allEntries = true
    )
    public PetDTO update(
            Long id,
            PetDTO petDTO
    ) {

        Pet pet = petRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pet não encontrado com ID: " + id
                        )
                );

        pet.setNome(petDTO.getNome());
        pet.setEspecie(petDTO.getEspecie());
        pet.setRaca(petDTO.getRaca());
        pet.setSexo(petDTO.getSexo());
        pet.setPeso(petDTO.getPeso());
        pet.setMicrochip(petDTO.getMicrochip());
        pet.setStatus(petDTO.getStatus());

        Pet petAtualizado =
                petRepository.save(pet);

        return convertToDTO(petAtualizado);
    }

    @CacheEvict(
            value = {
                    "pets",
                    "petsPage",
                    "petsByTutor",
                    "petsByNome"
            },
            allEntries = true
    )
    public void delete(Long id) {

        Pet pet = petRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pet não encontrado com ID: " + id
                        )
                );

        petRepository.delete(pet);
    }

    private PetDTO convertToDTO(Pet pet) {

        return PetDTO.builder()
                .id(pet.getId())
                .nome(pet.getNome())
                .especie(pet.getEspecie())
                .raca(pet.getRaca())
                .sexo(pet.getSexo())
                .peso(pet.getPeso())
                .microchip(pet.getMicrochip())
                .status(pet.getStatus())
                .tutorId(pet.getTutor().getId())
                .build();
    }

    private Pet convertToEntity(
            PetDTO dto,
            Tutor tutor
    ) {

        return Pet.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .especie(dto.getEspecie())
                .raca(dto.getRaca())
                .sexo(dto.getSexo())
                .peso(dto.getPeso())
                .microchip(dto.getMicrochip())
                .status(dto.getStatus())
                .tutor(tutor)
                .build();
    }
}