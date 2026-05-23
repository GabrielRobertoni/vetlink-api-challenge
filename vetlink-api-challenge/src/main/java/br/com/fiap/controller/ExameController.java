package br.com.fiap.controller;

import br.com.fiap.dto.ExameDTO;
import br.com.fiap.service.ExameService;
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
@RequestMapping("/api/v1/exames")
@RequiredArgsConstructor
@Tag(
        name = "Exames",
        description = "API para gerenciamento de exames"
)

public class ExameController {

    private final ExameService exameService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar exame por ID")
    public ResponseEntity<ExameDTO> findById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                exameService.findById(id)
        );
    }

    @GetMapping
    @Operation(summary = "Listar todos os exames com paginação")
    public ResponseEntity<Page<ExameDTO>> findAll(

            @PageableDefault(
                    size = 10,
                    page = 0,
                    sort = "dataSolicitacao",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                exameService.findAll(pageable)
        );
    }

    @GetMapping("/consulta/{consultaId}")
    @Operation(summary = "Listar exames de uma consulta específica")
    public ResponseEntity<Page<ExameDTO>> findByConsultaId(

            @PathVariable Long consultaId,

            @PageableDefault(
                    size = 10,
                    page = 0,
                    sort = "dataSolicitacao",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                exameService.findByConsultaId(
                        consultaId,
                        pageable
                )
        );
    }

    @PostMapping
    @Operation(summary = "Registrar novo exame")
    public ResponseEntity<ExameDTO> create(

            @Valid
            @RequestBody
            ExameDTO exameDTO
    ) {

        ExameDTO created =
                exameService.create(exameDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do exame")
    public ResponseEntity<ExameDTO> update(

            @PathVariable Long id,

            @Valid
            @RequestBody
            ExameDTO exameDTO
    ) {

        return ResponseEntity.ok(
                exameService.update(id, exameDTO)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar exame")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        exameService.delete(id);

        return ResponseEntity.noContent().build();
    }
}