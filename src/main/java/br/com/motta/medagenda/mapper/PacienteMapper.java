package br.com.motta.medagenda.mapper;

import br.com.motta.medagenda.dto.CadastroDTO;
import br.com.motta.medagenda.dto.PacienteResponseDTO;
import br.com.motta.medagenda.dto.PacienteUpdateDTO;
import br.com.motta.medagenda.model.Paciente;
import br.com.motta.medagenda.model.Usuario;

public class PacienteMapper {

    public static PacienteResponseDTO toDTO(Paciente paciente) {
        return new PacienteResponseDTO(paciente.getId(), paciente.getUsuario().getNome(), paciente.getDataNascimento(), paciente.getTelefone());
    }

    public static Paciente toEntity(CadastroDTO dto, Usuario usuario) {
        Paciente paciente = new Paciente();
        paciente.setCpf(dto.cpf());
        paciente.setDataNascimento(dto.dataNascimento());
        paciente.setTelefone(dto.telefone());
        paciente.setUsuario(usuario);
        return paciente;
    }

    public static void updateEntityFromDTO(PacienteUpdateDTO dto, Paciente paciente){
        paciente.setCpf(dto.cpf());
        paciente.setDataNascimento(dto.dataNascimento());
        paciente.setTelefone(dto.telefone());
    }
}
