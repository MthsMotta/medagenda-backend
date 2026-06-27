package br.com.motta.medagenda.service;

import br.com.motta.medagenda.dto.PacienteRequestDTO;
import br.com.motta.medagenda.dto.PacienteResponseDTO;
import br.com.motta.medagenda.dto.PacienteUpdateDTO;

import java.util.List;

public interface PacienteService {
    PacienteResponseDTO cadastrar(PacienteRequestDTO dto);
    PacienteResponseDTO atualizar(Long id, PacienteUpdateDTO dto);
    void excluir(Long id);
    List<PacienteResponseDTO> listarTodos();
    PacienteResponseDTO buscarPorId(Long id);
}
