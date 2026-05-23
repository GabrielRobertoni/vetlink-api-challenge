package br.com.fiap.repository;

import br.com.fiap.entity.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClinicaRepository
        extends JpaRepository<Clinica, Long> {

    Optional<Clinica> findByCnpj(String cnpj);
}