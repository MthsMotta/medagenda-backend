package br.com.motta.medagenda.mapper;

import br.com.motta.medagenda.dto.MedicoEspecialidadeRequestDTO;
import br.com.motta.medagenda.dto.MedicoEspecialidadeResponseDTO;
import br.com.motta.medagenda.model.Especialidade;
import br.com.motta.medagenda.model.Medico;
import br.com.motta.medagenda.model.MedicoEspecialidade;
import br.com.motta.medagenda.model.MedicoEspecialidadeId;

public class MedicoEspecialidadeMapper {

    public static MedicoEspecialidadeResponseDTO toDto(MedicoEspecialidade medicoEspecialidade) {
        return new MedicoEspecialidadeResponseDTO(medicoEspecialidade.getMedico().getId(),
                medicoEspecialidade.getEspecialidade().getId(),
                medicoEspecialidade.getMedico().getUsuario().getNome(),
                medicoEspecialidade.getEspecialidade().getNome());
    }

    public static MedicoEspecialidade toEntity(MedicoEspecialidadeRequestDTO dto, Medico medico, Especialidade especialidade) {
        MedicoEspecialidadeId medicoEspecialidadeId = new MedicoEspecialidadeId(dto.medicoId(), dto.especialidadeId());
        MedicoEspecialidade medicoEspecialidade = new MedicoEspecialidade();
        medicoEspecialidade.setId(medicoEspecialidadeId);
        medicoEspecialidade.setMedico(medico);
        medicoEspecialidade.setEspecialidade(especialidade);
        return medicoEspecialidade;
    }
}
