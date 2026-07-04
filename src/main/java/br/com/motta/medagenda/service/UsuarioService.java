package br.com.motta.medagenda.service;

import br.com.motta.medagenda.dto.UsuarioResponseDTO;
import br.com.motta.medagenda.dto.UsuarioUpdateDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO atualizar(Long id, UsuarioUpdateDTO dto);
    void excluir(Long id);
    UsuarioResponseDTO buscarPorId(Long id);
    List<UsuarioResponseDTO> listarTodos();
}
