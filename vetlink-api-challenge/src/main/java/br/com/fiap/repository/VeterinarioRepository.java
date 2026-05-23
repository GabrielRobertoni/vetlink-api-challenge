package br.com.fiap.repository;

import br.com.fiap.entity.Veterinario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {

    Optional<Veterinario> findByCrmv(String crmv);

    Page<Veterinario> findByClinicaId(Long clinicaId, Pageable pageable);

    Page<Veterinario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Veterinario> findByEspecialidade(String especialidade, Pageable pageable);
}
