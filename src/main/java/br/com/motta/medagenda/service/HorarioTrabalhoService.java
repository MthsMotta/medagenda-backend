package br.com.motta.medagenda.service;

import br.com.motta.medagenda.dto.HorarioTrabalhoRequestDTO;
import br.com.motta.medagenda.dto.HorarioTrabalhoResponseDTO;
import br.com.motta.medagenda.dto.HorarioTrabalhoUpdateDTO;

import java.util.List;

public interface HorarioTrabalhoService {
    HorarioTrabalhoResponseDTO cadastrar(HorarioTrabalhoRequestDTO dto);
    HorarioTrabalhoResponseDTO atualizar(Long id, HorarioTrabalhoUpdateDTO dto);
    void excluir(Long id);
    List<HorarioTrabalhoResponseDTO> buscarPeloMedicoId(Long medicoId);
}
