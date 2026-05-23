package br.com.fiap.service;

import br.com.fiap.dto.ExameDTO;
import br.com.fiap.entity.Clinica;
import br.com.fiap.entity.Exame;
import br.com.fiap.entity.Tutor;
import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.repository.ClinicaRepository;
import br.com.fiap.repository.ExameRepository;
import br.com.fiap.repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional

public class ExameService {

    private final ExameRepository exameRepository;
    private final ClinicaRepository clinicaRepository;
    private final TutorRepository tutorRepository;

    @Transactional(readOnly = true)
    public ExameDTO findById(Long id) {

        Exame exame = exameRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Exame não encontrado com ID: " + id
                        )
                );

        return convertToDTO(exame);
    }

    @Transactional(readOnly = true)
    public Page<ExameDTO> findAll(Pageable pageable) {

        return exameRepository
                .findAll(pageable)
                .map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    public Page<ExameDTO> findByConsultaId(
            Long consultaId,
            Pageable pageable
    ) {

        return exameRepository
                .findByConsultaId(consultaId, pageable)
                .map(this::convertToDTO);
    }

    public ExameDTO create(ExameDTO dto) {

        Clinica clinica = clinicaRepository
                .findById(dto.getClinicaId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Clínica não encontrada com ID: "
                                        + dto.getClinicaId()
                        )
                );

        Tutor tutor = tutorRepository
                .findById(dto.getTutorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tutor não encontrado com ID: "
                                        + dto.getTutorId()
                        )
                );

        Exame exame = new Exame();

        exame.setId(dto.getId());
        exame.setNome(dto.getNome());
        exame.setDataSolicitacao(dto.getDataSolicitacao());
        exame.setDataResultado(dto.getDataResultado());
        exame.setResultado(dto.getResultado());
        exame.setObservacoes(dto.getObservacoes());
        exame.setConsultaId(dto.getConsultaId());
        exame.setClinica(clinica);
        exame.setTutor(tutor);

        Exame exameSalvo =
                exameRepository.saveAndFlush(exame);

        return convertToDTO(exameSalvo);
    }

    public ExameDTO update(
            Long id,
            ExameDTO dto
    ) {

        Exame exame = exameRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Exame não encontrado com ID: " + id
                        )
                );

        Clinica clinica = clinicaRepository
                .findById(dto.getClinicaId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Clínica não encontrada com ID: "
                                        + dto.getClinicaId()
                        )
                );

        Tutor tutor = tutorRepository
                .findById(dto.getTutorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tutor não encontrado com ID: "
                                        + dto.getTutorId()
                        )
                );

        exame.setNome(dto.getNome());
        exame.setDataSolicitacao(dto.getDataSolicitacao());
        exame.setDataResultado(dto.getDataResultado());
        exame.setResultado(dto.getResultado());
        exame.setObservacoes(dto.getObservacoes());
        exame.setConsultaId(dto.getConsultaId());
        exame.setClinica(clinica);
        exame.setTutor(tutor);

        Exame exameAtualizado =
                exameRepository.saveAndFlush(exame);

        return convertToDTO(exameAtualizado);
    }

    public void delete(Long id) {

        Exame exame = exameRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Exame não encontrado com ID: " + id
                        )
                );

        exameRepository.delete(exame);
    }

    private ExameDTO convertToDTO(Exame exame) {

        return ExameDTO.builder()
                .id(exame.getId())
                .nome(exame.getNome())
                .dataSolicitacao(exame.getDataSolicitacao())
                .dataResultado(exame.getDataResultado())
                .resultado(exame.getResultado())
                .observacoes(exame.getObservacoes())
                .consultaId(exame.getConsultaId())
                .clinicaId(exame.getClinica().getId())
                .tutorId(exame.getTutor().getId())
                .build();
    }
}