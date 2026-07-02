package br.com.motta.medagenda.service;

import br.com.motta.medagenda.dto.MedicoEspecialidadeRequestDTO;
import br.com.motta.medagenda.dto.MedicoEspecialidadeResponseDTO;
import br.com.motta.medagenda.exception.RecursoNaoEncontradoException;
import br.com.motta.medagenda.exception.RegraDeNegocioException;
import br.com.motta.medagenda.mapper.MedicoEspecialidadeMapper;
import br.com.motta.medagenda.model.Especialidade;
import br.com.motta.medagenda.model.Medico;
import br.com.motta.medagenda.model.MedicoEspecialidade;
import br.com.motta.medagenda.model.MedicoEspecialidadeId;
import br.com.motta.medagenda.repository.EspecialidadeRepository;
import br.com.motta.medagenda.repository.MedicoEspecialidadeRepository;
import br.com.motta.medagenda.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoEspecialidadeServiceImpl implements MedicoEspecialidadeService {

    private final MedicoEspecialidadeRepository medicoEspecialidadeRepository;
    private final EspecialidadeRepository especialidadeRepository;
    private final MedicoRepository medicoRepository;

    public MedicoEspecialidadeServiceImpl(MedicoEspecialidadeRepository medicoEspecialidadeRepository, EspecialidadeRepository especialidadeRepository, MedicoRepository medicoRepository) {
        this.medicoEspecialidadeRepository = medicoEspecialidadeRepository;
        this.especialidadeRepository = especialidadeRepository;
        this.medicoRepository = medicoRepository;
    }


    @Override
    public MedicoEspecialidadeResponseDTO cadastrar(MedicoEspecialidadeRequestDTO dto) {
        Medico medico = medicoRepository.findById(dto.medicoId()).orElseThrow(() -> new RecursoNaoEncontradoException("Medico nao encontrado"));
        Especialidade especialidade = especialidadeRepository.findById(dto.especialidadeId()).orElseThrow(() -> new RecursoNaoEncontradoException("Especialidade nao encontrada"));
        MedicoEspecialidadeId id = new MedicoEspecialidadeId(dto.medicoId(), dto.especialidadeId());
        if (medicoEspecialidadeRepository.existsById(id)) {
            throw new RegraDeNegocioException("Medico ja vinculado a essa especialidade");
        }
        MedicoEspecialidade medicoEspecialidade = MedicoEspecialidadeMapper.toEntity(dto, medico, especialidade);
        return MedicoEspecialidadeMapper.toDto(medicoEspecialidadeRepository.save(medicoEspecialidade));
    }

    @Override
    public void excluir(Long medicoId, Long especialidadeId) {
        MedicoEspecialidadeId id = new MedicoEspecialidadeId(medicoId, especialidadeId);
        if(!medicoEspecialidadeRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Vinculo nao encontrado");
        }
        medicoEspecialidadeRepository.deleteById(id);
    }

    @Override
    public List<MedicoEspecialidadeResponseDTO> listarPorMedicoId(Long medicoId) {
        List<MedicoEspecialidade> medicoEspecialidades = medicoEspecialidadeRepository.findByMedicoId(medicoId);
        return medicoEspecialidades.stream().map(MedicoEspecialidadeMapper::toDto).toList();
    }

    @Override
    public List<MedicoEspecialidadeResponseDTO> listarPorEspecialidadeNome(String especialidadeNome) {
        List<MedicoEspecialidade> especialidades = medicoEspecialidadeRepository.findByEspecialidadeNomeContainingIgnoreCase(especialidadeNome);
        return especialidades.stream().map(MedicoEspecialidadeMapper::toDto).toList();
    }
}
