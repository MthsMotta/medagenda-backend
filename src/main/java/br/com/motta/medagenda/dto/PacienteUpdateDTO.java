package br.com.motta.medagenda.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PacienteUpdateDTO(@NotBlank @Size(max = 14) String cpf,
                                @Past @NotNull @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataNascimento,
                                @NotBlank @Size(max = 20) String telefone) {
}
