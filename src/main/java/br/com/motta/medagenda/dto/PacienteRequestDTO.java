package br.com.motta.medagenda.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PacienteRequestDTO(@NotBlank @Size(max = 14) String cpf,
                                 @Past @NotNull @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataNascimento,
                                 @NotBlank @Size(max = 20) String telefone,
                                 @NotNull @Positive Long usuarioId) {
}
