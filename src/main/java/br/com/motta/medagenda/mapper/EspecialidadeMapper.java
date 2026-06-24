package br.com.motta.medagenda.mapper;

import br.com.motta.medagenda.dto.EspecialidadeRequestDTO;
import br.com.motta.medagenda.dto.EspecialidadeResponseDTO;
import br.com.motta.medagenda.model.Especialidade;

public class EspecialidadeMapper {
    public static EspecialidadeResponseDTO toDTO(Especialidade especialidade){
        return new EspecialidadeResponseDTO(especialidade.getId(), especialidade.getNome(), especialidade.getDescricao());
    }

    public static Especialidade toEntity(EspecialidadeRequestDTO dto){
        Especialidade especialidade = new Especialidade();
        especialidade.setNome(dto.nome());
        especialidade.setDescricao(dto.descricao());
        return especialidade;
    }

    public static void updateEntityFromDTO(EspecialidadeRequestDTO dto, Especialidade especialidade){
        especialidade.setNome(dto.nome());
        especialidade.setDescricao(dto.descricao());
    }
}
