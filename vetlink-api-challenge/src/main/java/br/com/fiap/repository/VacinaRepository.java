package br.com.fiap.repository;

import br.com.fiap.entity.Vacina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VacinaRepository
        extends JpaRepository<Vacina, Long> {

    Page<Vacina> findByPetId(
            Long petId,
            Pageable pageable
    );

    Page<Vacina> findByTutorId(
            Long tutorId,
            Pageable pageable
    );

    List<Vacina> findByPetId(Long petId);

    Page<Vacina> findByDataReforcoBeforeAndDataReforcoIsNotNull(
            LocalDate data,
            Pageable pageable
    );
}