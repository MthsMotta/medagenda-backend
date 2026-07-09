package br.com.motta.medagenda.service;

import br.com.motta.medagenda.dto.ConsultaRequestDTO;
import br.com.motta.medagenda.dto.ConsultaResponseDTO;
import br.com.motta.medagenda.dto.ConsultaUpdateDTO;
import br.com.motta.medagenda.exception.RecursoNaoEncontradoException;
import br.com.motta.medagenda.exception.RegraDeNegocioException;
import br.com.motta.medagenda.mapper.ConsultaMapper;
import br.com.motta.medagenda.model.*;
import br.com.motta.medagenda.repository.ConsultaRepository;
import br.com.motta.medagenda.repository.MedicoRepository;
import br.com.motta.medagenda.repository.PacienteRepository;
import br.com.motta.medagenda.specification.ConsultaSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;


    public ConsultaServiceImpl(ConsultaRepository consultaRepository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }


    @Override
    public ConsultaResponseDTO cadastrar(ConsultaRequestDTO dto) {
        Medico medico = medicoRepository.findById(dto.idMedico()).orElseThrow(() -> new RecursoNaoEncontradoException("Medico não encontrado"));
        Paciente paciente = pacienteRepository.findById(dto.idPaciente()).orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado"));
        if (consultaRepository.findByMedicoIdAndPacienteIdAndDataHora(dto.idMedico(), dto.idPaciente(), dto.dataHora()).isPresent()) {
            throw new RegraDeNegocioException("Consulta já marcada");
        }
        if (dto.dataHora().isBefore(LocalDateTime.now())) {
            throw new RegraDeNegocioException("A data e hora do agendamento estão no passado");
        }
        var usuarioLogado = (Usuario) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        if(usuarioLogado.getRole() == Role.PACIENTE){
            Paciente pacienteLogado = pacienteRepository.findByUsuarioId(usuarioLogado.getId()).orElseThrow(()
                    -> new RecursoNaoEncontradoException("Paciente nao encontrado"));
            if(!pacienteLogado.getId().equals(dto.idPaciente())){
                throw new RegraDeNegocioException("Voce so pode marcar consultas para si mesmo");
            }
        }
        LocalDateTime inicioDoDia = dto.dataHora().toLocalDate().atStartOfDay();
        LocalDateTime fimDoDia = dto.dataHora().toLocalDate().atTime(23, 59, 59);
        List<Consulta> consultasExistentes = consultaRepository.findByMedicoIdAndDataHoraBetween(dto.idMedico(), inicioDoDia, fimDoDia);
        LocalDateTime novaConsultaInicio = dto.dataHora();
        LocalDateTime novaConsultaFim = dto.dataHora().plusMinutes(dto.duracaoMinutos());
        if (consultasExistentes.stream().anyMatch(c ->
                !(novaConsultaFim.isBefore(c.getDataHora()) ||
                        novaConsultaInicio.isAfter(c.getDataHora().plusMinutes(c.getDuracaoMinutos()))))){
            throw new RegraDeNegocioException("Já existe uma consulta marcada dentro desse horário");
        }
        Consulta consulta = ConsultaMapper.toEntity(dto, medico, paciente);
        return ConsultaMapper.toDto(consultaRepository.save(consulta));
    }

    @Override
    @Transactional
    public ConsultaResponseDTO atualizar(Long id, ConsultaUpdateDTO dto) {
        Consulta consultaAtualizada = consultaRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada"));
        if (consultaAtualizada.getStatusConsulta() == StatusConsulta.REALIZADA || consultaAtualizada.getStatusConsulta() == StatusConsulta.CANCELADA) {
            throw new RegraDeNegocioException("A consulta já foi encerrada");
        }
        ConsultaMapper.updateEntityFromDTO(dto, consultaAtualizada);
        return ConsultaMapper.toDto(consultaAtualizada);
    }

    @Override
    public void excluir(Long id) {
        Consulta consulta = consultaRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada"));
        var usuarioLogado = (Usuario) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        if(usuarioLogado == null){
            throw new RegraDeNegocioException("Usuario não autenticado");
        }
        if(!(usuarioLogado.getId().equals(consulta.getPaciente().getUsuario().getId()) ||
                usuarioLogado.getId().equals(consulta.getMedico().getUsuario().getId()) ||
                usuarioLogado.getRole() == Role.ADMIN)) {
            throw new RegraDeNegocioException("Apenas o paciente ou medico da consulta ou admins podem cancela-la");
        }
        consultaRepository.delete(consulta);
    }

    @Override
    public Page<ConsultaResponseDTO> listarTodos(Long medicoId,
                                                 Long pacienteId,
                                                 StatusConsulta statusConsulta,
                                                 LocalDateTime inicio,
                                                 LocalDateTime fim,
                                                 Pageable pageable) {
        var usuarioLogado = (Usuario) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        if(usuarioLogado.getRole() == Role.PACIENTE){
            Paciente paciente = pacienteRepository.findByUsuarioId(usuarioLogado.getId()).orElseThrow(()
                    -> new RecursoNaoEncontradoException("Paciente nao encontrado para o usuario logado"));
            pacienteId = paciente.getId();
            medicoId = null;
        }
        if(usuarioLogado.getRole() == Role.MEDICO){
            Medico medico = medicoRepository.findByUsuarioId(usuarioLogado.getId()).orElseThrow(()
                    -> new RecursoNaoEncontradoException("Medico nao encontrado para o usuario logado"));
            medicoId = medico.getId();
            pacienteId = null;
        }
        Specification<Consulta> spec = Specification
                .where(ConsultaSpecification.comMedicoId(medicoId))
                .and(ConsultaSpecification.comPacienteId(pacienteId))
                .and(ConsultaSpecification.comStatus(statusConsulta))
                .and(ConsultaSpecification.entreDataHora(inicio, fim));
        Page<Consulta> consultas = consultaRepository.findAll(spec, pageable);
        return consultas.map(ConsultaMapper::toDto);
    }

    @Override
    @Transactional
    public ConsultaResponseDTO confirmar(Long id) {
        Consulta consulta = consultaRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada"));
        if (consulta.getStatusConsulta() != StatusConsulta.AGENDADA) {
            throw new RegraDeNegocioException("Só é possível confirmar consultas com status AGENDADA");
        }
        var usuarioLogado = (Usuario)  Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        if(usuarioLogado == null){
            throw new RegraDeNegocioException("Usuario nao autenticado");
        }
        if(!(usuarioLogado.getId().equals(consulta.getPaciente().getUsuario().getId()) || usuarioLogado.getRole() == Role.ADMIN)){
            throw new RegraDeNegocioException("Apenas o paciente da consulta ou admins podem confirma-la");
        }
        consulta.setStatusConsulta(StatusConsulta.CONFIRMADA);
        return ConsultaMapper.toDto(consulta);
    }

    @Override
    @Transactional
    public ConsultaResponseDTO cancelar(Long id) {
        Consulta consulta = consultaRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada"));
        if (consulta.getStatusConsulta() == StatusConsulta.REALIZADA || consulta.getStatusConsulta() == StatusConsulta.CANCELADA) {
            throw new RegraDeNegocioException("Só é possível cancelar consultas com status AGENDADA ou CONFIRMADA");
        }
        var usuarioLogado = (Usuario) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        if(usuarioLogado == null){
            throw new RegraDeNegocioException("Usuario nao autenticado");
        }
        if(!(usuarioLogado.getId().equals(consulta.getPaciente().getUsuario().getId()) ||
                usuarioLogado.getId().equals(consulta.getMedico().getUsuario().getId()) ||
                usuarioLogado.getRole() == Role.ADMIN)) {
            throw new RegraDeNegocioException("Apenas o paciente ou medicos da consulta ou admins podem cancela-la");
        }
        consulta.setStatusConsulta(StatusConsulta.CANCELADA);
        return ConsultaMapper.toDto(consulta);
    }

    @Override
    @Transactional
    public ConsultaResponseDTO realizada(Long id) {
        Consulta consulta = consultaRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada"));
        if (consulta.getStatusConsulta() != StatusConsulta.CONFIRMADA) {
            throw new RegraDeNegocioException("Só é possível realizar consultas com status CONFIRMADA");
        }
        var usuarioLogado = (Usuario) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        if(usuarioLogado == null){
            throw new RegraDeNegocioException("Usuario nao autenticado");
        }
        if(!(usuarioLogado.getId().equals(consulta.getMedico().getUsuario().getId()) || usuarioLogado.getRole() == Role.ADMIN)) {
            throw new RegraDeNegocioException("Apenas o medico da consulta ou admins podem marca-la como realizada");
        }
        consulta.setStatusConsulta(StatusConsulta.REALIZADA);
        return ConsultaMapper.toDto(consulta);
    }

    @Override
    public ConsultaResponseDTO buscarPorId(Long id) {
        Consulta consulta = consultaRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada"));
        var usuarioLogado = (Usuario) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        if(usuarioLogado == null){
            throw new RegraDeNegocioException("Usuario nao autenticado");
        }
        if(!(usuarioLogado.getId().equals(consulta.getPaciente().getUsuario().getId()) ||
                usuarioLogado.getId().equals(consulta.getMedico().getUsuario().getId()) ||
                usuarioLogado.getRole() == Role.ADMIN)){
            throw new RegraDeNegocioException("Apenas o paciente ou medico da consulta ou admins podem buscar-la");
        }
        return ConsultaMapper.toDto(consulta);
    }
}
