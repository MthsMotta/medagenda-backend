package br.com.motta.medagenda.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CadastroDTO(@NotBlank @Size(max = 100) String nome,
                          @NotBlank @Email @Size(max = 100)  String email,
                          @NotBlank @Size(min = 8, max = 100)  String senha,
                          @NotBlank @Size(max = 14) String cpf,
                          @Past @NotNull @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataNascimento,
                          @NotBlank @Size(max = 20) String telefone) { }
