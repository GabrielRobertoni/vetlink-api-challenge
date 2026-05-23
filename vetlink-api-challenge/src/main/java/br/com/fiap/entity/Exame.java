package br.com.fiap.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "T_VET_EXAME")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Exame {

    @Id
    @Column(name = "CD_EXAME")
    private Long id;

    @NotBlank(message = "Nome do exame é obrigatório")
    @Size(
            min = 3,
            max = 150,
            message = "Nome deve ter entre 3 e 150 caracteres"
    )
    @Column(
            name = "DS_EXAME",
            nullable = false,
            length = 150
    )
    private String nome;

    @NotNull(message = "Data da solicitação é obrigatória")
    @Column(
            name = "DT_SOLICITACAO",
            nullable = false
    )
    private LocalDate dataSolicitacao;

    @Column(name = "DT_RESULTADO")
    private LocalDate dataResultado;

    @Column(
            name = "DS_RESULTADO",
            length = 500
    )
    private String resultado;

    @Column(
            name = "DS_OBSERVACOES",
            length = 500
    )
    private String observacoes;

    @Column(name = "ID_CONSULTA", nullable = false)
    private Long consultaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ID_CLINICA",
            nullable = false
    )
    @JsonBackReference
    private Clinica clinica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "CD_TUTOR",
            nullable = false
    )
    @JsonBackReference
    private Tutor tutor;
}