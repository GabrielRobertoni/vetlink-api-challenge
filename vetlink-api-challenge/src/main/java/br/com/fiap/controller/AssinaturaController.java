package br.com.fiap.controller;

import br.com.fiap.dto.AssinaturaDTO;
import br.com.fiap.service.AssinaturaService;
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
@RequestMapping("/api/v1/assinaturas")
@RequiredArgsConstructor
@Tag(
        name = "Assinaturas",
        description = "API para gerenciamento de planos de assinatura"
)

public class AssinaturaController {

    private final AssinaturaService assinaturaService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar assinatura por ID")
    public ResponseEntity<AssinaturaDTO> findById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                assinaturaService.findById(id)
        );
    }

    @GetMapping
    @Operation(summary = "Listar todas as assinaturas com paginação")
    public ResponseEntity<Page<AssinaturaDTO>> findAll(

            @PageableDefault(
                    size = 10,
                    page = 0,
                    sort = "dataInicio",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                assinaturaService.findAll(pageable)
        );
    }

    @GetMapping("/tutor/{tutorId}")
    @Operation(summary = "Listar assinaturas de um tutor específico")
    public ResponseEntity<Page<AssinaturaDTO>> findByTutorId(

            @PathVariable Long tutorId,

            @PageableDefault(
                    size = 10,
                    page = 0,
                    sort = "dataInicio",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                assinaturaService.findByTutorId(
                        tutorId,
                        pageable
                )
        );
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar assinaturas por status")
    public ResponseEntity<Page<AssinaturaDTO>> findByStatus(

            @PathVariable String status,

            @PageableDefault(
                    size = 10,
                    page = 0,
                    sort = "dataInicio",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                assinaturaService.findByStatus(
                        status,
                        pageable
                )
        );
    }

    @PostMapping
    @Operation(summary = "Criar nova assinatura")
    public ResponseEntity<AssinaturaDTO> create(

            @Valid
            @RequestBody
            AssinaturaDTO assinaturaDTO
    ) {

        AssinaturaDTO created =
                assinaturaService.create(assinaturaDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados da assinatura")
    public ResponseEntity<AssinaturaDTO> update(

            @PathVariable Long id,

            @Valid
            @RequestBody
            AssinaturaDTO assinaturaDTO
    ) {

        return ResponseEntity.ok(
                assinaturaService.update(id, assinaturaDTO)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar assinatura")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        assinaturaService.delete(id);

        return ResponseEntity.noContent().build();
    }
}