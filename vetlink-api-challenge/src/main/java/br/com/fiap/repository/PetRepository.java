package br.com.fiap.repository;

import br.com.fiap.entity.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Page<Pet> findByTutorId(Long tutorId, Pageable pageable);

    Page<Pet> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Pet> findByEspecie(String especie, Pageable pageable);

    List<Pet> findByTutorId(Long tutorId);
}
