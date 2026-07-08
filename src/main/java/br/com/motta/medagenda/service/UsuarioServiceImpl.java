package br.com.motta.medagenda.service;

import br.com.motta.medagenda.dto.CadastroDTO;
import br.com.motta.medagenda.dto.UsuarioAdminRequestDTO;
import br.com.motta.medagenda.dto.UsuarioResponseDTO;
import br.com.motta.medagenda.dto.UsuarioUpdateDTO;
import br.com.motta.medagenda.exception.RecursoNaoEncontradoException;
import br.com.motta.medagenda.exception.RegraDeNegocioException;
import br.com.motta.medagenda.mapper.PacienteMapper;
import br.com.motta.medagenda.mapper.UsuarioMapper;
import br.com.motta.medagenda.model.Medico;
import br.com.motta.medagenda.model.Paciente;
import br.com.motta.medagenda.model.StatusConsulta;
import br.com.motta.medagenda.model.Usuario;
import br.com.motta.medagenda.repository.ConsultaRepository;
import br.com.motta.medagenda.repository.MedicoRepository;
import br.com.motta.medagenda.repository.PacienteRepository;
import br.com.motta.medagenda.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final ConsultaRepository consultaRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PacienteRepository pacienteRepository, MedicoRepository medicoRepository, ConsultaRepository consultaRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.consultaRepository = consultaRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public UsuarioResponseDTO cadastrar(CadastroDTO cadastro) {
        if (usuarioRepository.findByEmail(cadastro.email()).isPresent()) {
            throw new RegraDeNegocioException("Ja existe um usuario com esse email.");
        }
        Usuario usuario = UsuarioMapper.toEntity(cadastro, passwordEncoder.encode(cadastro.senha()));
        Usuario salvo = usuarioRepository.save(usuario);
        Paciente paciente = PacienteMapper.toEntity(cadastro, salvo);
        pacienteRepository.save(paciente);
        return UsuarioMapper.toDTO(salvo);
    }

    @Override
    public UsuarioResponseDTO cadastrarAdmin(UsuarioAdminRequestDTO cadastroAdmin) {
        if (usuarioRepository.findByEmail(cadastroAdmin.email()).isPresent()) {
            throw new RegraDeNegocioException("Ja existe um usuario com esse email.");
        }
        Usuario usuario = UsuarioMapper.toEntity(cadastroAdmin, passwordEncoder.encode(cadastroAdmin.senha()));
        Usuario salvo = usuarioRepository.save(usuario);
        return UsuarioMapper.toDTO(salvo);
    }


    @Override
    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioUpdateDTO dto) {
        Usuario usuarioAtualizado = usuarioRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Usuario nao encontrado"));
        UsuarioMapper.updateEntityFromDTO(dto, usuarioAtualizado);
        return UsuarioMapper.toDTO(usuarioAtualizado);
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Usuario nao encontrado"));
        Optional<Medico> medico = medicoRepository.findByUsuarioId(id);
        Optional<Paciente> paciente = pacienteRepository.findByUsuarioId(id);
        List<StatusConsulta> statusAtivos = List.of(StatusConsulta.AGENDADA, StatusConsulta.CONFIRMADA);
        if (medico.isPresent()) {
            if (consultaRepository.existsByMedicoAndStatusConsultaIn(medico.get(), statusAtivos)) {
                throw new RegraDeNegocioException("Exclusao nao efetuada, pois o medico tem consultas ativas");
            }
            medicoRepository.delete(medico.get());
        }
        if (paciente.isPresent()) {
            if (consultaRepository.existsByPacienteAndStatusConsultaIn(paciente.get(), statusAtivos)) {
                throw new RegraDeNegocioException("Exclusao nao efetuada, pois o paciente tem consultas ativas");
            }
            pacienteRepository.delete(paciente.get());
        }
        usuarioRepository.delete(usuario);
    }

    @Override
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Usuario nao encontrado"));
        return UsuarioMapper.toDTO(usuario);
    }

    @Override
    public List<UsuarioResponseDTO> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(UsuarioMapper::toDTO).toList();
    }
}
