package br.com.fiap.repository;

import br.com.fiap.entity.Tutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {

    Optional<Tutor> findByCpf(String cpf);

    Page<Tutor> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Tutor> findByStatus(String status, Pageable pageable);

    @Query("SELECT t FROM Tutor t WHERE t.status = :status ORDER BY t.nome ASC")
    Page<Tutor> findActiveTutors(String status, Pageable pageable);
}
