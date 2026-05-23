package br.com.fiap.repository;

import br.com.fiap.entity.Consulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRepository
        extends JpaRepository<Consulta, Long> {

    Page<Consulta> findByTutorId(
            Long tutorId,
            Pageable pageable
    );

    Page<Consulta> findByVeterinarioId(
            Long veterinarioId,
            Pageable pageable
    );

    Page<Consulta> findByClinicaId(
            Long clinicaId,
            Pageable pageable
    );

    Page<Consulta> findByStatus(
            String status,
            Pageable pageable
    );
}