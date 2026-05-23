package br.com.fiap.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "T_VET_VACINA")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Vacina {

    @Id
    @Column(name = "CD_VACINA")
    private Long id;

    @NotBlank(message = "Nome da vacina é obrigatório")
    @Size(
            min = 3,
            max = 50,
            message = "Nome deve ter entre 3 e 50 caracteres"
    )
    @Column(
            name = "DS_NOMEVACINA",
            nullable = false,
            length = 50
    )
    private String nome;

    @NotBlank(message = "Fabricante é obrigatório")
    @Column(
            name = "DS_FABRIICANTE",
            nullable = false,
            length = 50
    )
    private String fabricante;

    @NotBlank(message = "Lote é obrigatório")
    @Column(
            name = "NR_LOTE",
            nullable = false,
            precision = 10
    )
    private String lote;

    @NotNull(message = "Data de aplicação é obrigatória")
    @Column(
            name = "DT_APLICACAO",
            nullable = false
    )
    private LocalDate dataAplicacao;

    @Column(
            name = "DT_REFORCO",
            nullable = false
    )
    private LocalDate dataReforco;

    @Column(
            name = "DS_OBSERVACOES",
            length = 100
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