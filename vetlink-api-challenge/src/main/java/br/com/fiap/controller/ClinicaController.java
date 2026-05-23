package br.com.fiap.controller;

import br.com.fiap.dto.ClinicaDTO;
import br.com.fiap.service.ClinicaService;
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
@RequestMapping("/api/v1/clinicas")
@RequiredArgsConstructor
@Tag(
        name = "Clínicas",
        description = "API para gerenciamento de clínicas veterinárias"
)

public class ClinicaController {

    private final ClinicaService clinicaService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar clínica por ID")
    public ResponseEntity<ClinicaDTO> findById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                clinicaService.findById(id)
        );
    }

    @GetMapping
    @Operation(summary = "Listar clínicas")
    public ResponseEntity<Page<ClinicaDTO>> findAll(

            @PageableDefault(
                    size = 10,
                    page = 0,
                    sort = "id",
                    direction = Sort.Direction.ASC
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                clinicaService.findAll(pageable)
        );
    }

    @PostMapping
    @Operation(summary = "Criar clínica")
    public ResponseEntity<ClinicaDTO> create(

            @Valid
            @RequestBody
            ClinicaDTO clinicaDTO
    ) {

        ClinicaDTO clinicaCriada =
                clinicaService.create(clinicaDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clinicaCriada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar clínica")
    public ResponseEntity<ClinicaDTO> update(

            @PathVariable Long id,

            @Valid
            @RequestBody
            ClinicaDTO clinicaDTO
    ) {

        return ResponseEntity.ok(
                clinicaService.update(id, clinicaDTO)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar clínica")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        clinicaService.delete(id);

        return ResponseEntity.noContent().build();
    }
}