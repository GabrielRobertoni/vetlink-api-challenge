package br.com.fiap.service;

import br.com.fiap.dto.ConsultaDTO;
import br.com.fiap.entity.Clinica;
import br.com.fiap.entity.Consulta;
import br.com.fiap.entity.Tutor;
import br.com.fiap.exception.ResourceNotFoundException;
import br.com.fiap.repository.ClinicaRepository;
import br.com.fiap.repository.ConsultaRepository;
import br.com.fiap.repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional

public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final TutorRepository tutorRepository;
    private final ClinicaRepository clinicaRepository;

    @Transactional(readOnly = true)
    public ConsultaDTO findById(Long id) {

        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Consulta não encontrada com ID: " + id
                        )
                );

        return convertToDTO(consulta);
    }

    @Transactional(readOnly = true)
    public Page<ConsultaDTO> findAll(Pageable pageable) {

        return consultaRepository
                .findAll(pageable)
                .map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    public Page<ConsultaDTO> findByTutorId(
            Long tutorId,
            Pageable pageable
    ) {

        return consultaRepository
                .findByTutorId(tutorId, pageable)
                .map(this::convertToDTO);
    }

    public ConsultaDTO create(ConsultaDTO dto) {

        Tutor tutor = tutorRepository.findById(dto.getTutorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tutor não encontrado com ID: "
                                        + dto.getTutorId()
                        )
                );

        Clinica clinica = clinicaRepository.findById(dto.getClinicaId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Clínica não encontrada com ID: "
                                        + dto.getClinicaId()
                        )
                );

        Consulta consulta = Consulta.builder()
                .id(dto.getId())
                .dataConsulta(dto.getDataConsulta())
                .status(dto.getStatus())
                .valor(dto.getValor())
                .observacoes(dto.getObservacoes())
                .tutor(tutor)
                .clinica(clinica)
                .veterinarioId(dto.getVeterinarioId())
                .build();

        Consulta consultaSalva =
                consultaRepository.save(consulta);

        return convertToDTO(consultaSalva);
    }

    public ConsultaDTO update(
            Long id,
            ConsultaDTO dto
    ) {

        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Consulta não encontrada com ID: " + id
                        )
                );

        Tutor tutor = tutorRepository.findById(dto.getTutorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tutor não encontrado com ID: "
                                        + dto.getTutorId()
                        )
                );

        Clinica clinica = clinicaRepository.findById(dto.getClinicaId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Clínica não encontrada com ID: "
                                        + dto.getClinicaId()
                        )
                );

        consulta.setDataConsulta(dto.getDataConsulta());
        consulta.setStatus(dto.getStatus());
        consulta.setValor(dto.getValor());
        consulta.setObservacoes(dto.getObservacoes());
        consulta.setTutor(tutor);
        consulta.setClinica(clinica);
        consulta.setVeterinarioId(dto.getVeterinarioId());

        Consulta consultaAtualizada =
                consultaRepository.save(consulta);

        return convertToDTO(consultaAtualizada);
    }

    public void delete(Long id) {

        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Consulta não encontrada com ID: " + id
                        )
                );

        consultaRepository.delete(consulta);
    }

    private ConsultaDTO convertToDTO(Consulta consulta) {

        return ConsultaDTO.builder()
                .id(consulta.getId())
                .dataConsulta(consulta.getDataConsulta())
                .status(consulta.getStatus())
                .valor(consulta.getValor())
                .observacoes(consulta.getObservacoes())
                .tutorId(consulta.getTutor().getId())
                .clinicaId(consulta.getClinica().getId())
                .veterinarioId(consulta.getVeterinarioId())
                .build();
    }
}