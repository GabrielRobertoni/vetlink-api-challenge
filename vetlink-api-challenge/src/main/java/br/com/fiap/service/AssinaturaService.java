package br.com.fiap.service;

import br.com.fiap.dto.AssinaturaDTO;
import br.com.fiap.entity.Assinatura;
import br.com.fiap.entity.Tutor;
import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.repository.AssinaturaRepository;
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

public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;
    private final TutorRepository tutorRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "assinaturas", key = "#id")
    public AssinaturaDTO findById(Long id) {

        Assinatura assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Assinatura não encontrada com ID: " + id
                        )
                );

        return convertToDTO(assinatura);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "assinaturasPage",
            key = "#pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public Page<AssinaturaDTO> findAll(Pageable pageable) {

        return assinaturaRepository
                .findAll(pageable)
                .map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "assinaturasByTutor",
            key = "#tutorId + '-' + #pageable.pageNumber"
    )
    public Page<AssinaturaDTO> findByTutorId(
            Long tutorId,
            Pageable pageable
    ) {

        return assinaturaRepository
                .findByTutorId(tutorId, pageable)
                .map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "assinaturasByStatus",
            key = "#status + '-' + #pageable.pageNumber"
    )
    public Page<AssinaturaDTO> findByStatus(
            String status,
            Pageable pageable
    ) {

        return assinaturaRepository
                .findByStatus(status, pageable)
                .map(this::convertToDTO);
    }

    @CacheEvict(
            value = {
                    "assinaturas",
                    "assinaturasPage",
                    "assinaturasByTutor",
                    "assinaturasByStatus"
            },
            allEntries = true
    )
    public AssinaturaDTO create(
            AssinaturaDTO assinaturaDTO
    ) {

        Tutor tutor = tutorRepository.findById(
                        assinaturaDTO.getTutorId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tutor não encontrado com ID: "
                                        + assinaturaDTO.getTutorId()
                        )
                );

        Assinatura assinatura =
                convertToEntity(assinaturaDTO, tutor);

        Assinatura assinaturaSalva =
                assinaturaRepository.save(assinatura);

        return convertToDTO(assinaturaSalva);
    }

    @CacheEvict(
            value = {
                    "assinaturas",
                    "assinaturasPage",
                    "assinaturasByTutor",
                    "assinaturasByStatus"
            },
            allEntries = true
    )
    public AssinaturaDTO update(
            Long id,
            AssinaturaDTO assinaturaDTO
    ) {

        Assinatura assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Assinatura não encontrada com ID: " + id
                        )
                );

        assinatura.setTipoPlano(assinaturaDTO.getTipoPlano());
        assinatura.setDataInicio(assinaturaDTO.getDataInicio());
        assinatura.setDataFim(assinaturaDTO.getDataFim());
        assinatura.setStatus(assinaturaDTO.getStatus());
        assinatura.setValorMensal(assinaturaDTO.getValorMensal());
        assinatura.setRenovacaoAutomatica(
                assinaturaDTO.getRenovacaoAutomatica()
        );
        assinatura.setMetodoPagamento(
                assinaturaDTO.getMetodoPagamento()
        );

        Assinatura assinaturaAtualizada =
                assinaturaRepository.save(assinatura);

        return convertToDTO(assinaturaAtualizada);
    }

    @CacheEvict(
            value = {
                    "assinaturas",
                    "assinaturasPage",
                    "assinaturasByTutor",
                    "assinaturasByStatus"
            },
            allEntries = true
    )
    public void delete(Long id) {

        Assinatura assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Assinatura não encontrada com ID: " + id
                        )
                );

        assinaturaRepository.delete(assinatura);
    }

    private AssinaturaDTO convertToDTO(
            Assinatura assinatura
    ) {

        return AssinaturaDTO.builder()
                .id(assinatura.getId())
                .tipoPlano(assinatura.getTipoPlano())
                .dataInicio(assinatura.getDataInicio())
                .dataFim(assinatura.getDataFim())
                .status(assinatura.getStatus())
                .valorMensal(assinatura.getValorMensal())
                .renovacaoAutomatica(
                        assinatura.getRenovacaoAutomatica()
                )
                .metodoPagamento(
                        assinatura.getMetodoPagamento()
                )
                .tutorId(assinatura.getTutor().getId())
                .build();
    }

    private Assinatura convertToEntity(
            AssinaturaDTO dto,
            Tutor tutor
    ) {

        return Assinatura.builder()
                .id(dto.getId())
                .tipoPlano(dto.getTipoPlano())
                .dataInicio(dto.getDataInicio())
                .dataFim(dto.getDataFim())
                .status(dto.getStatus())
                .valorMensal(dto.getValorMensal())
                .renovacaoAutomatica(
                        dto.getRenovacaoAutomatica()
                )
                .metodoPagamento(
                        dto.getMetodoPagamento()
                )
                .tutor(tutor)
                .build();
    }
}