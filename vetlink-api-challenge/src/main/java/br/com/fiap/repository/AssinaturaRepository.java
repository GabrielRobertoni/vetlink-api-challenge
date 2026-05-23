package br.com.fiap.repository;

import br.com.fiap.entity.Assinatura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssinaturaRepository
        extends JpaRepository<Assinatura, Long> {

    Page<Assinatura> findByTutorId(
            Long tutorId,
            Pageable pageable
    );

    Page<Assinatura> findByStatus(
            String status,
            Pageable pageable
    );

    Page<Assinatura> findByTipoPlano(
            String tipoPlano,
            Pageable pageable
    );
}