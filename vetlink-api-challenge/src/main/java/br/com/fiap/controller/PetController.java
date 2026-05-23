package br.com.fiap.controller;

import br.com.fiap.dto.PetDTO;
import br.com.fiap.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pets")
@RequiredArgsConstructor
@Tag(name = "Pets", description = "API para gerenciamento de pets")
public class PetController {

    private final PetService petService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pet por ID")
    public ResponseEntity<PetDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(petService.findById(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os pets com paginação")
    public ResponseEntity<Page<PetDTO>> findAll(
            @PageableDefault(size = 10, page = 0, sort = "nome", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.ok(petService.findAll(pageable));
    }

    @GetMapping("/tutor/{tutorId}")
    @Operation(summary = "Listar pets de um tutor específico")
    public ResponseEntity<Page<PetDTO>> findByTutorId(
            @PathVariable Long tutorId,
            @PageableDefault(size = 10, page = 0, sort = "nome", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.ok(petService.findByTutorId(tutorId, pageable));
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar pets por nome")
    public ResponseEntity<Page<PetDTO>> findByNome(
            @RequestParam String nome,
            @PageableDefault(size = 10, page = 0, sort = "nome", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.ok(petService.findByNome(nome, pageable));
    }

    @PostMapping
    @Operation(summary = "Criar novo pet")
    public ResponseEntity<PetDTO> create(@Valid @RequestBody PetDTO petDTO) {
        PetDTO created = petService.create(petDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do pet")
    public ResponseEntity<PetDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PetDTO petDTO) {
        return ResponseEntity.ok(petService.update(id, petDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar pet")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
