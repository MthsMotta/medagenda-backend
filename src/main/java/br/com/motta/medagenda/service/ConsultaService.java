package br.com.motta.medagenda.service;

import br.com.motta.medagenda.dto.ConsultaRequestDTO;
import br.com.motta.medagenda.dto.ConsultaResponseDTO;
import br.com.motta.medagenda.dto.ConsultaUpdateDTO;
import br.com.motta.medagenda.model.StatusConsulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ConsultaService {

    ConsultaResponseDTO cadastrar(ConsultaRequestDTO dto);
    ConsultaResponseDTO atualizar(Long id, ConsultaUpdateDTO dto);
    void excluir (Long id);
    Page<ConsultaResponseDTO> listarTodos(Long medicoId, Long pacienteId, StatusConsulta statusConsulta, LocalDateTime inicio, LocalDateTime fim, Pageable pageable);
    ConsultaResponseDTO confirmar(Long id);
    ConsultaResponseDTO cancelar(Long id);
    ConsultaResponseDTO realizada(Long id);
    ConsultaResponseDTO buscarPorId(Long id);
}
