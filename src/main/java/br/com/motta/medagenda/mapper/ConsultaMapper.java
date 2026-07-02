package br.com.motta.medagenda.mapper;

import br.com.motta.medagenda.dto.ConsultaRequestDTO;
import br.com.motta.medagenda.dto.ConsultaResponseDTO;
import br.com.motta.medagenda.dto.ConsultaUpdateDTO;
import br.com.motta.medagenda.model.Consulta;
import br.com.motta.medagenda.model.Medico;
import br.com.motta.medagenda.model.Paciente;
import br.com.motta.medagenda.model.StatusConsulta;

public class ConsultaMapper {

    public static ConsultaResponseDTO toDto(Consulta consulta) {
        return new ConsultaResponseDTO(consulta.getId(),
                consulta.getPaciente().getUsuario().getNome(),
                consulta.getMedico().getUsuario().getNome(),
                consulta.getDataHora(),
                consulta.getDuracaoMinutos(),
                consulta.getValor(),
                consulta.getStatusConsulta(),
                consulta.getObservacao());
    }

    public static Consulta toEntity(ConsultaRequestDTO dto, Medico medico, Paciente paciente) {
        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataHora(dto.dataHora());
        consulta.setDuracaoMinutos(dto.duracaoMinutos());
        consulta.setValor(dto.valor());
        consulta.setStatusConsulta(StatusConsulta.AGENDADA);
        consulta.setObservacao(dto.observacao());
        return consulta;
    }

    public static void updateEntityFromDTO(ConsultaUpdateDTO dto, Consulta consulta){
        consulta.setDataHora(dto.dataHora());
        consulta.setDuracaoMinutos(dto.duracaoMinutos());
        consulta.setValor(dto.valor());
        consulta.setObservacao(dto.observacao());
    }
}
