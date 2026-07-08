package br.com.motta.medagenda.service;

import br.com.motta.medagenda.dto.PacienteResponseDTO;
import br.com.motta.medagenda.dto.PacienteUpdateDTO;
import br.com.motta.medagenda.exception.RecursoNaoEncontradoException;
import br.com.motta.medagenda.exception.RegraDeNegocioException;
import br.com.motta.medagenda.mapper.PacienteMapper;
import br.com.motta.medagenda.model.Paciente;
import br.com.motta.medagenda.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    @Transactional
    public PacienteResponseDTO atualizar(Long id, PacienteUpdateDTO dto) {
        Paciente pacienteAtualizado = pacienteRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado"));
        Optional<Paciente> pacienteComEsseCpf = pacienteRepository.findByCpf(dto.cpf());
        if (pacienteComEsseCpf.isPresent() && !pacienteComEsseCpf.get().getId().equals(id)) {
            throw new RegraDeNegocioException("CPF já cadastrado para outro paciente");
        }
        PacienteMapper.updateEntityFromDTO(dto, pacienteAtualizado);
        return PacienteMapper.toDTO(pacienteAtualizado);
    }

    @Override
    public void excluir(Long id) {
        Paciente paciente = pacienteRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado"));
        pacienteRepository.delete(paciente);
    }

    @Override
    public List<PacienteResponseDTO> listarTodos() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        return pacientes.stream().map(PacienteMapper::toDTO).toList();
    }

    @Override
    public PacienteResponseDTO buscarPorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado"));
        return PacienteMapper.toDTO(paciente);
    }
}
