package br.com.motta.medagenda.dto;

import br.com.motta.medagenda.model.StatusConsulta;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ConsultaResponseDTO(Long id,
                                  String nomePaciente,
                                  String nomeMedico,
                                  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime dataHora,
                                  Integer duracaoMinutos,
                                  BigDecimal valor,
                                  StatusConsulta statusConsulta,
                                  String observacao) {
}
