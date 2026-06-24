package br.com.motta.medagenda.mapper;

import br.com.motta.medagenda.dto.MedicoRequestDTO;
import br.com.motta.medagenda.dto.MedicoResponseDTO;
import br.com.motta.medagenda.dto.MedicoUpdateDTO;
import br.com.motta.medagenda.model.Medico;
import br.com.motta.medagenda.model.Usuario;

public class MedicoMapper {
    public static MedicoResponseDTO toDTO(Medico medico){
        return new MedicoResponseDTO(medico.getId(), medico.getUsuario().getNome(), medico.getCrm());
    }

    public static Medico toEntity(MedicoRequestDTO dto, Usuario usuario){
        Medico medico = new Medico();
        medico.setCrm(dto.crm());
        medico.setUsuario(usuario);
        return medico;
    }

    public static void updateEntityFromDTO(MedicoUpdateDTO dto, Medico medico){
        medico.setCrm(dto.crm());
    }
}
