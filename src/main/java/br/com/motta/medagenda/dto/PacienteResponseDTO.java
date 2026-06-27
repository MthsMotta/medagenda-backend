package br.com.motta.medagenda.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record PacienteResponseDTO(Long id, String nome, @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataNascimento, String telefone) {
}
