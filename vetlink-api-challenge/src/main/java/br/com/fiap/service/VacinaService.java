package br.com.fiap.service;

import br.com.fiap.dto.VacinaDTO;
import br.com.fiap.entity.Pet;
import br.com.fiap.entity.Tutor;
import br.com.fiap.entity.Vacina;
import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.repository.PetRepository;
import br.com.fiap.repository.TutorRepository;
import br.com.fiap.repository.VacinaRepository;
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

public class VacinaService {

    private final VacinaRepository vacinaRepository;
    private final PetRepository petRepository;
    private final TutorRepository tutorRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "vacinas", key = "#id")
    public VacinaDTO findById(Long id) {

        Vacina vacina = vacinaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vacina não encontrada com ID: " + id
                        )
                );

        return convertToDTO(vacina);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "vacinasPage",
            key = "#pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public Page<VacinaDTO> findAll(Pageable pageable) {

        return vacinaRepository
                .findAll(pageable)
                .map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "vacinasByPet",
            key = "#petId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public Page<VacinaDTO> findByPetId(
            Long petId,
            Pageable pageable
    ) {

        return vacinaRepository
                .findByPetId(petId, pageable)
                .map(this::convertToDTO);
    }

    @CacheEvict(
            value = {
                    "vacinas",
                    "vacinasPage",
                    "vacinasByPet"
            },
            allEntries = true
    )
    public VacinaDTO create(
            VacinaDTO vacinaDTO
    ) {

        Pet pet = petRepository.findById(
                        vacinaDTO.getPetId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Pet não encontrado com ID: "
                                        + vacinaDTO.getPetId()
                        )
                );

        Tutor tutor = tutorRepository.findById(
                        vacinaDTO.getTutorId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tutor não encontrado com ID: "
                                        + vacinaDTO.getTutorId()
                        )
                );

        Vacina vacina = convertToEntity(
                vacinaDTO,
                pet,
                tutor
        );

        Vacina vacinaSalva =
                vacinaRepository.save(vacina);

        return convertToDTO(vacinaSalva);
    }

    @CacheEvict(
            value = {
                    "vacinas",
                    "vacinasPage",
                    "vacinasByPet"
            },
            allEntries = true
    )
    public VacinaDTO update(
            Long id,
            VacinaDTO vacinaDTO
    ) {

        Vacina vacina = vacinaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vacina não encontrada com ID: " + id
                        )
                );

        vacina.setNome(vacinaDTO.getNome());
        vacina.setFabricante(vacinaDTO.getFabricante());
        vacina.setLote(vacinaDTO.getLote());
        vacina.setDataAplicacao(vacinaDTO.getDataAplicacao());
        vacina.setDataReforco(vacinaDTO.getDataReforco());
        vacina.setObservacoes(vacinaDTO.getObservacoes());

        Vacina vacinaAtualizada =
                vacinaRepository.save(vacina);

        return convertToDTO(vacinaAtualizada);
    }

    @CacheEvict(
            value = {
                    "vacinas",
                    "vacinasPage",
                    "vacinasByPet"
            },
            allEntries = true
    )
    public void delete(Long id) {

        Vacina vacina = vacinaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vacina não encontrada com ID: " + id
                        )
                );

        vacinaRepository.delete(vacina);
    }

    private VacinaDTO convertToDTO(Vacina vacina) {

        return VacinaDTO.builder()
                .id(vacina.getId())
                .nome(vacina.getNome())
                .fabricante(vacina.getFabricante())
                .lote(vacina.getLote())
                .dataAplicacao(vacina.getDataAplicacao())
                .dataReforco(vacina.getDataReforco())
                .observacoes(vacina.getObservacoes())
                .petId(vacina.getPet().getId())
                .tutorId(vacina.getTutor().getId())
                .build();
    }

    private Vacina convertToEntity(
            VacinaDTO dto,
            Pet pet,
            Tutor tutor
    ) {

        return Vacina.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .fabricante(dto.getFabricante())
                .lote(dto.getLote())
                .dataAplicacao(dto.getDataAplicacao())
                .dataReforco(dto.getDataReforco())
                .observacoes(dto.getObservacoes())
                .pet(pet)
                .tutor(tutor)
                .build();
    }
}