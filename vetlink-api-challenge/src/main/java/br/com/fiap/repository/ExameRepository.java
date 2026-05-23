package br.com.fiap.repository;

import br.com.fiap.entity.Exame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExameRepository
        extends JpaRepository<Exame, Long> {

    Page<Exame> findByConsultaId(
            Long consultaId,
            Pageable pageable
    );

    Page<Exame> findByClinicaId(
            Long clinicaId,
            Pageable pageable
    );

    Page<Exame> findByTutorId(
            Long tutorId,
            Pageable pageable
    );

    Page<Exame> findByNomeContainingIgnoreCase(
            String nome,
            Pageable pageable
    );
}