package br.com.motta.medagenda.service;

import br.com.motta.medagenda.dto.EspecialidadeRequestDTO;
import br.com.motta.medagenda.dto.EspecialidadeResponseDTO;

import java.util.List;

public interface EspecialidadeService {
    EspecialidadeResponseDTO cadastrar(EspecialidadeRequestDTO especialidadeDTO);
    EspecialidadeResponseDTO atualizar(Long id, EspecialidadeRequestDTO especialidadeDTO);
    void excluir(Long id);
    List<EspecialidadeResponseDTO> listarTodos();
    List<EspecialidadeResponseDTO> listarPorNomeContendo(String nome);
    EspecialidadeResponseDTO buscarPorId(Long id);
}