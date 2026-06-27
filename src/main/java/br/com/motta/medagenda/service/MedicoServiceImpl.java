package br.com.motta.medagenda.service;

import br.com.motta.medagenda.dto.MedicoRequestDTO;
import br.com.motta.medagenda.dto.MedicoResponseDTO;
import br.com.motta.medagenda.dto.MedicoUpdateDTO;
import br.com.motta.medagenda.exception.RecursoNaoEncontradoException;
import br.com.motta.medagenda.exception.RegraDeNegocioException;
import br.com.motta.medagenda.mapper.MedicoMapper;
import br.com.motta.medagenda.model.Medico;
import br.com.motta.medagenda.model.Usuario;
import br.com.motta.medagenda.repository.MedicoRepository;
import br.com.motta.medagenda.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository medicoRepository;
    private final UsuarioRepository usuarioRepository;

    public MedicoServiceImpl(MedicoRepository medicoRepository, UsuarioRepository usuarioRepository) {
        this.medicoRepository = medicoRepository;
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public MedicoResponseDTO cadastrar(MedicoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId()).orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
        if (medicoRepository.findByCrm(dto.crm()).isPresent()){
            throw new RegraDeNegocioException("Já existe um medico com esse crm");
        }
        if (medicoRepository.findByUsuarioId(dto.usuarioId()).isPresent()){
            throw new RegraDeNegocioException("Esse usuário já possui vinculo com um médico");
        }
        Medico medico = MedicoMapper.toEntity(dto, usuario);
        Medico salvo = medicoRepository.save(medico);
        return MedicoMapper.toDTO(salvo);
    }

    @Override
    @Transactional
    public MedicoResponseDTO atualizar(Long id, MedicoUpdateDTO dto) {
        Medico medicoAtualizado = medicoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Medico não encontrado"));
        MedicoMapper.updateEntityFromDTO(dto, medicoAtualizado);
        return MedicoMapper.toDTO(medicoAtualizado);
    }

    @Override
    public void excluir(Long id) {
        Medico medico = medicoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Medico não encontrado"));
        medicoRepository.delete(medico);
    }

    @Override
    public List<MedicoResponseDTO> listarTodos() {
        List<Medico> medicos = medicoRepository.findAll();
        return medicos.stream().map(MedicoMapper::toDTO).toList();
    }

    @Override
    public List<MedicoResponseDTO> listarPorNomeContendo(String nome) {
        List<Medico> medicos = medicoRepository.findByUsuarioNomeContainingIgnoreCase(nome);
        return medicos.stream().map(MedicoMapper::toDTO).toList();
    }

    @Override
    public MedicoResponseDTO buscarPorId(Long id) {
        Medico medico = medicoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Médico não encontrado"));
        return MedicoMapper.toDTO(medico);
    }
}
