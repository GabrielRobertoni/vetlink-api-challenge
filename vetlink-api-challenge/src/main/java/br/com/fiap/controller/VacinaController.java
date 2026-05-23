package br.com.fiap.controller;

import br.com.fiap.dto.VacinaDTO;
import br.com.fiap.service.VacinaService;
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
@RequestMapping("/api/v1/vacinas")
@RequiredArgsConstructor
@Tag(
        name = "Vacinas",
        description = "API para gerenciamento de vacinas"
)

public class VacinaController {

    private final VacinaService vacinaService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar vacina por ID")
    public ResponseEntity<VacinaDTO> findById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                vacinaService.findById(id)
        );
    }

    @GetMapping
    @Operation(summary = "Listar todas as vacinas com paginação")
    public ResponseEntity<Page<VacinaDTO>> findAll(

            @PageableDefault(
                    size = 10,
                    page = 0,
                    sort = "dataAplicacao",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                vacinaService.findAll(pageable)
        );
    }

    @GetMapping("/pet/{petId}")
    @Operation(summary = "Listar vacinas de um pet específico")
    public ResponseEntity<Page<VacinaDTO>> findByPetId(

            @PathVariable Long petId,

            @PageableDefault(
                    size = 10,
                    page = 0,
                    sort = "dataAplicacao",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                vacinaService.findByPetId(
                        petId,
                        pageable
                )
        );
    }

    @PostMapping
    @Operation(summary = "Registrar nova vacina")
    public ResponseEntity<VacinaDTO> create(

            @Valid
            @RequestBody
            VacinaDTO vacinaDTO
    ) {

        VacinaDTO created =
                vacinaService.create(vacinaDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados da vacina")
    public ResponseEntity<VacinaDTO> update(

            @PathVariable Long id,

            @Valid
            @RequestBody
            VacinaDTO vacinaDTO
    ) {

        return ResponseEntity.ok(
                vacinaService.update(id, vacinaDTO)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar vacina")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        vacinaService.delete(id);

        return ResponseEntity.noContent().build();
    }
}