package br.com.fiap.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_VET_TUTOR")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Tutor {

    @Id
    @Column(name = "CD_TUTOR")
    private Long id;

    @NotBlank(message = "Nome do tutor é obrigatório")
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

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(
            regexp = "\\d{11}",
            message = "CPF deve conter 11 dígitos"
    )
    @Column(
            name = "NR_CPF",
            unique = true,
            nullable = false,
            length = 11
    )
    private String cpf;

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

    @NotBlank(message = "Situação é obrigatória")
    @Column(
            name = "DS_SITUACAO",
            nullable = false,
            length = 10
    )
    private String status;

    @NotBlank(message = "Sexo é obrigatório")
    @Column(
            name = "DS_SEXO",
            nullable = false,
            length = 15
    )
    private String sexo;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Column(
            name = "DT_NASCIMENTO",
            nullable = false
    )
    private LocalDate dataNascimento;

    @OneToMany(
            mappedBy = "tutor",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<Pet> pets = new ArrayList<>();

    @OneToMany(
            mappedBy = "tutor",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<Consulta> consultas = new ArrayList<>();

    @OneToMany(
            mappedBy = "tutor",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<Medicamento> medicamentos = new ArrayList<>();

    @OneToMany(
            mappedBy = "tutor",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<Vacina> vacinas = new ArrayList<>();

    @OneToMany(
            mappedBy = "tutor",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<Exame> exames = new ArrayList<>();

    @OneToMany(
            mappedBy = "tutor",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    @Builder.Default
    private List<Assinatura> assinaturas = new ArrayList<>();
}