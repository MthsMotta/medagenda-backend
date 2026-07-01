package br.com.motta.medagenda.mapper;

import br.com.motta.medagenda.dto.HorarioTrabalhoRequestDTO;
import br.com.motta.medagenda.dto.HorarioTrabalhoResponseDTO;
import br.com.motta.medagenda.dto.HorarioTrabalhoUpdateDTO;
import br.com.motta.medagenda.model.HorarioTrabalho;
import br.com.motta.medagenda.model.Medico;

public class HorarioTrabalhoMapper {

    public static HorarioTrabalhoResponseDTO toDTO(HorarioTrabalho horarioTrabalho){
        return new HorarioTrabalhoResponseDTO(horarioTrabalho.getId(), horarioTrabalho.getDiaSemana(), horarioTrabalho.getHoraInicio(),
                horarioTrabalho.getHoraFim(), horarioTrabalho.getMedico().getId());
    }

    public static HorarioTrabalho toEntity(HorarioTrabalhoRequestDTO dto, Medico medico){
        HorarioTrabalho horarioTrabalho = new HorarioTrabalho();
        horarioTrabalho.setDiaSemana(dto.diaSemana());
        horarioTrabalho.setHoraInicio(dto.horaInicio());
        horarioTrabalho.setHoraFim(dto.horaFim());
        horarioTrabalho.setMedico(medico);
        return horarioTrabalho;
    }

    public static void updateEntityFromDTO(HorarioTrabalhoUpdateDTO dto, HorarioTrabalho horarioTrabalho){
        horarioTrabalho.setDiaSemana(dto.diaSemana());
        horarioTrabalho.setHoraInicio(dto.horaInicio());
        horarioTrabalho.setHoraFim(dto.horaFim());
    }
}
