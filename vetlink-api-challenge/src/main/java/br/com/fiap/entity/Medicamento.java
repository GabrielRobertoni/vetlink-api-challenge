package br.com.fiap.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "T_VET_MEDICAMENTO")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Medicamento {

    @Id
    @Column(name = "CD_MEDICAMENTO")
    private Long id;

    @NotBlank(message = "Nome do medicamento é obrigatório")
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

    @NotNull(message = "Dosagem é obrigatória")
    @Column(
            name = "NR_DOSAGEM",
            nullable = false
    )
    private Double dosagem;

    @NotBlank(message = "Frequência é obrigatória")
    @Column(
            name = "DS_FREQUENCIA",
            nullable = false,
            length = 20
    )
    private String frequencia;

    @Column(
            name = "DS_OBSERVACOES",
            length = 300
    )
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PET", nullable = false)
    @JsonBackReference
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TUTOR", nullable = false)
    @JsonBackReference
    private Tutor tutor;
}