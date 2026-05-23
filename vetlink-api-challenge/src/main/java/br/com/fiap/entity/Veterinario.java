package br.com.fiap.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "T_VET_VETERINARIO")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Veterinario {

    @Id
    @Column(name = "ID_VETERINARIO")
    private Long id;

    @Column(name = "VETERINARIO_ID")
    private Long clinicaId;

    @NotBlank(message = "Nome do veterinário é obrigatório")
    @Size(
            min = 3,
            max = 100,
            message = "Nome deve ter entre 3 e 100 caracteres"
    )
    @Column(
            name = "DS_NOME",
            nullable = false,
            length = 100
    )
    private String nome;

    @NotBlank(message = "CRMV é obrigatório")
    @Column(
            name = "NR_CRMV",
            nullable = false,
            length = 11
    )
    private String crmv;

    @NotBlank(message = "Especialidade é obrigatória")
    @Column(
            name = "DS_ESPECIALIDADE",
            nullable = false,
            length = 50
    )
    private String especialidade;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(
            regexp = "\\d{11}",
            message = "Telefone deve conter 11 dígitos"
    )
    @Column(
            name = "NR_TELEFONE",
            nullable = false,
            length = 11
    )
    private String telefone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "VETERINARIO_ID",
            referencedColumnName = "ID_CLINICA",
            insertable = false,
            updatable = false
    )
    @JsonBackReference
    private Clinica clinica;
}