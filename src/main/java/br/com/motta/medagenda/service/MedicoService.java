package br.com.motta.medagenda.service;

import br.com.motta.medagenda.dto.MedicoRequestDTO;
import br.com.motta.medagenda.dto.MedicoResponseDTO;
import br.com.motta.medagenda.dto.MedicoUpdateDTO;

import java.util.List;

public interface MedicoService {
    MedicoResponseDTO cadastrar(MedicoRequestDTO dto);
    MedicoResponseDTO atualizar(Long id, MedicoUpdateDTO dto);
    void excluir(Long id);
    List<MedicoResponseDTO> listarTodos();
    List<MedicoResponseDTO> listarPorNomeContendo(String nome);
    MedicoResponseDTO buscarPorId(Long id);
}
