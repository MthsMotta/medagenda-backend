package br.com.motta.medagenda.service;

import br.com.motta.medagenda.dto.MedicoEspecialidadeRequestDTO;
import br.com.motta.medagenda.dto.MedicoEspecialidadeResponseDTO;

import java.util.List;

public interface MedicoEspecialidadeService {

    MedicoEspecialidadeResponseDTO cadastrar(MedicoEspecialidadeRequestDTO dto);
    void excluir(Long medicoId, Long especialidadeId);
    List<MedicoEspecialidadeResponseDTO> listarPorMedicoId(Long medicoId);
    List<MedicoEspecialidadeResponseDTO> listarPorEspecialidadeNome(String especialidadeNome);
}
