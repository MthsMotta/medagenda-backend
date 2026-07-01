package br.com.motta.medagenda.dto;

import br.com.motta.medagenda.model.DiaSemana;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record HorarioTrabalhoUpdateDTO(@NotNull DiaSemana diaSemana,
                                       @NotNull LocalTime horaInicio,
                                       @NotNull LocalTime horaFim) {
}
