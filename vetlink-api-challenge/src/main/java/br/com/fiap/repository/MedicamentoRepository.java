package br.com.fiap.repository;

import br.com.fiap.entity.Medicamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicamentoRepository
        extends JpaRepository<Medicamento, Long> {

    Page<Medicamento> findByPetId(
            Long petId,
            Pageable pageable
    );

    Page<Medicamento> findByTutorId(
            Long tutorId,
            Pageable pageable
    );

    List<Medicamento> findByPetId(Long petId);

    Page<Medicamento> findByNomeContainingIgnoreCase(
            String nome,
            Pageable pageable
    );
}