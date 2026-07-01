package br.com.motta.medagenda.dto;

import br.com.motta.medagenda.model.DiaSemana;

import java.time.LocalTime;

public record HorarioTrabalhoResponseDTO(Long id, DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFim, Long idMedico) {
}
