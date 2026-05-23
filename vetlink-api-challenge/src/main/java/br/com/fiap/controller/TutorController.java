package br.com.fiap.controller;

import br.com.fiap.dto.TutorDTO;
import br.com.fiap.service.TutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/v1/tutores")
@RequiredArgsConstructor
@Tag(name = "Tutores", description = "API para gerenciamento de tutores de pets")
public class TutorController {

    private final TutorService tutorService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tutor por ID", description = "Retorna os detalhes de um tutor específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tutor encontrado"),
            @ApiResponse(responseCode = "404", description = "Tutor não encontrado")
    })
    public ResponseEntity<TutorDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tutorService.findById(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os tutores", description = "Retorna uma lista paginada de tutores")
    @ApiResponse(responseCode = "200", description = "Lista de tutores retornada com sucesso")
    public ResponseEntity<Page<TutorDTO>> findAll(
            @PageableDefault(size = 10, page = 0, sort = "nome", direction = Sort.Direction.ASC)
            @Parameter(description = "Parâmetros de paginação e ordenação")
            Pageable pageable) {
        return ResponseEntity.ok(tutorService.findAll(pageable));
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar tutores por nome", description = "Busca tutores por nome com paginação")
    @ApiResponse(responseCode = "200", description = "Tutores encontrados")
    public ResponseEntity<Page<TutorDTO>> findByNome(
            @RequestParam String nome,
            @PageableDefault(size = 10, page = 0, sort = "nome", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.ok(tutorService.findByNome(nome, pageable));
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar tutor por CPF", description = "Retorna um tutor específico pelo CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tutor encontrado"),
            @ApiResponse(responseCode = "404", description = "Tutor não encontrado")
    })
    public ResponseEntity<TutorDTO> findByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(tutorService.findByCpf(cpf));
    }

    @PostMapping
    @Operation(summary = "Criar novo tutor", description = "Cria um novo registro de tutor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tutor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<TutorDTO> create(@Valid @RequestBody TutorDTO tutorDTO) {
        TutorDTO created = tutorService.create(tutorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tutor", description = "Atualiza os dados de um tutor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tutor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tutor não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<TutorDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody TutorDTO tutorDTO) {
        return ResponseEntity.ok(tutorService.update(id, tutorDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar tutor", description = "Remove um tutor do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tutor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tutor não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tutorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
