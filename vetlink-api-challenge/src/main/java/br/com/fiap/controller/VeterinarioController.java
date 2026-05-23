package br.com.fiap.controller;

import br.com.fiap.dto.VeterinarioDTO;
import br.com.fiap.service.VeterinarioService;
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
@RequestMapping("/api/v1/veterinarios")
@RequiredArgsConstructor
@Tag(name = "Veterinários", description = "API para gerenciamento de veterinários")
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar veterinário por ID")
    public ResponseEntity<VeterinarioDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(veterinarioService.findById(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os veterinários com paginação")
    public ResponseEntity<Page<VeterinarioDTO>> findAll(
            @PageableDefault(size = 10, page = 0, sort = "nome", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.ok(veterinarioService.findAll(pageable));
    }

    @GetMapping("/clinica/{clinicaId}")
    @Operation(summary = "Listar veterinários de uma clínica específica")
    public ResponseEntity<Page<VeterinarioDTO>> findByClinicaId(
            @PathVariable Long clinicaId,
            @PageableDefault(size = 10, page = 0, sort = "nome", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.ok(veterinarioService.findByClinicaId(clinicaId, pageable));
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar veterinários por nome")
    public ResponseEntity<Page<VeterinarioDTO>> findByNome(
            @RequestParam String nome,
            @PageableDefault(size = 10, page = 0, sort = "nome", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.ok(veterinarioService.findByNome(nome, pageable));
    }

    @PostMapping
    @Operation(summary = "Criar novo veterinário")
    public ResponseEntity<VeterinarioDTO> create(@Valid @RequestBody VeterinarioDTO veterinarioDTO) {
        VeterinarioDTO created = veterinarioService.create(veterinarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do veterinário")
    public ResponseEntity<VeterinarioDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody VeterinarioDTO veterinarioDTO) {
        return ResponseEntity.ok(veterinarioService.update(id, veterinarioDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar veterinário")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        veterinarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
