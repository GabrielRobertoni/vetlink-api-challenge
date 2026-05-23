package br.com.fiap.controller;

import br.com.fiap.dto.ConsultaDTO;
import br.com.fiap.service.ConsultaService;
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
@RequestMapping("/api/v1/consultas")
@RequiredArgsConstructor
@Tag(
        name = "Consultas",
        description = "API para gerenciamento de consultas veterinárias"
)

public class ConsultaController {

    private final ConsultaService consultaService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar consulta por ID")
    public ResponseEntity<ConsultaDTO> findById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                consultaService.findById(id)
        );
    }

    @GetMapping
    @Operation(summary = "Listar todas as consultas com paginação")
    public ResponseEntity<Page<ConsultaDTO>> findAll(

            @PageableDefault(
                    size = 10,
                    page = 0,
                    sort = "dataConsulta",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                consultaService.findAll(pageable)
        );
    }

    @GetMapping("/tutor/{tutorId}")
    @Operation(summary = "Listar consultas de um tutor específico")
    public ResponseEntity<Page<ConsultaDTO>> findByTutorId(

            @PathVariable Long tutorId,

            @PageableDefault(
                    size = 10,
                    page = 0,
                    sort = "dataConsulta",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                consultaService.findByTutorId(
                        tutorId,
                        pageable
                )
        );
    }

    @PostMapping
    @Operation(summary = "Criar nova consulta")
    public ResponseEntity<ConsultaDTO> create(

            @Valid
            @RequestBody
            ConsultaDTO consultaDTO
    ) {

        ConsultaDTO created =
                consultaService.create(consultaDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados da consulta")
    public ResponseEntity<ConsultaDTO> update(

            @PathVariable Long id,

            @Valid
            @RequestBody
            ConsultaDTO consultaDTO
    ) {

        return ResponseEntity.ok(
                consultaService.update(id, consultaDTO)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar consulta")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        consultaService.delete(id);

        return ResponseEntity.noContent().build();
    }
}