package br.com.motta.medagenda.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ConsultaUpdateDTO(@NotNull @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime dataHora,
                                @NotNull @Positive Integer duracaoMinutos,
                                @NotNull @Positive BigDecimal valor,
                                @Size(max = 500) String observacao) {
}
