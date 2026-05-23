package br.com.fiap.controller;

import br.com.fiap.dto.MedicamentoDTO;
import br.com.fiap.service.MedicamentoService;
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
@RequestMapping("/api/v1/medicamentos")
@RequiredArgsConstructor
@Tag(
        name = "Medicamentos",
        description = "API para gerenciamento de medicamentos"
)

public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar medicamento por ID")
    public ResponseEntity<MedicamentoDTO> findById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                medicamentoService.findById(id)
        );
    }

    @GetMapping
    @Operation(summary = "Listar todos os medicamentos com paginação")
    public ResponseEntity<Page<MedicamentoDTO>> findAll(

            @PageableDefault(
                    size = 10,
                    page = 0,
                    sort = "nome",
                    direction = Sort.Direction.ASC
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                medicamentoService.findAll(pageable)
        );
    }

    @GetMapping("/pet/{petId}")
    @Operation(summary = "Listar medicamentos de um pet específico")
    public ResponseEntity<Page<MedicamentoDTO>> findByPetId(

            @PathVariable Long petId,

            @PageableDefault(
                    size = 10,
                    page = 0,
                    sort = "nome",
                    direction = Sort.Direction.ASC
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                medicamentoService.findByPetId(
                        petId,
                        pageable
                )
        );
    }

    @PostMapping
    @Operation(summary = "Criar novo medicamento")
    public ResponseEntity<MedicamentoDTO> create(

            @Valid
            @RequestBody
            MedicamentoDTO medicamentoDTO
    ) {

        MedicamentoDTO created =
                medicamentoService.create(medicamentoDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do medicamento")
    public ResponseEntity<MedicamentoDTO> update(

            @PathVariable Long id,

            @Valid
            @RequestBody
            MedicamentoDTO medicamentoDTO
    ) {

        return ResponseEntity.ok(
                medicamentoService.update(id, medicamentoDTO)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar medicamento")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        medicamentoService.delete(id);

        return ResponseEntity.noContent().build();
    }
}