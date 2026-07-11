package br.com.motta.medagenda.service;

import br.com.motta.medagenda.dto.PacienteResponseDTO;
import br.com.motta.medagenda.dto.PacienteUpdateDTO;
import br.com.motta.medagenda.exception.RecursoNaoEncontradoException;
import br.com.motta.medagenda.exception.RegraDeNegocioException;
import br.com.motta.medagenda.mapper.PacienteMapper;
import br.com.motta.medagenda.model.*;
import br.com.motta.medagenda.repository.ConsultaRepository;
import br.com.motta.medagenda.repository.MedicoRepository;
import br.com.motta.medagenda.repository.PacienteRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository, ConsultaRepository consultaRepository, MedicoRepository medicoRepository) {
        this.pacienteRepository = pacienteRepository;
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
    }

    @Override
    @Transactional
    public PacienteResponseDTO atualizar(Long id, PacienteUpdateDTO dto) {
        Paciente pacienteAtualizado = pacienteRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado"));
        Optional<Paciente> pacienteComEsseCpf = pacienteRepository.findByCpf(dto.cpf());
        if (pacienteComEsseCpf.isPresent() && !pacienteComEsseCpf.get().getId().equals(id)) {
            throw new RegraDeNegocioException("CPF já cadastrado para outro paciente");
        }
        var usuarioLogado = (Usuario) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        if (usuarioLogado == null) {
            throw new RegraDeNegocioException("Usuario nao autenticado");
        }
        if(!(usuarioLogado.getId().equals(pacienteAtualizado.getUsuario().getId()) || usuarioLogado.getRole() == Role.ADMIN)) {
            throw new RegraDeNegocioException("Apenas o dono da conta e admins podem atualizar o cadastro");
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
        var usuarioLogado = (Usuario) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        if (usuarioLogado == null) {
            throw new RegraDeNegocioException("Usuario nao autenticado");
        }
        if (!(usuarioLogado.getId().equals(paciente.getUsuario().getId())
                || usuarioLogado.getRole() == Role.ADMIN)) {
            throw new RegraDeNegocioException("Apenas o paciente ou admins tem acesso ao seu perfil");
        }
        return PacienteMapper.toDTO(paciente);
    }
}
