package br.com.motta.medagenda.service;

import br.com.motta.medagenda.dto.EspecialidadeRequestDTO;
import br.com.motta.medagenda.dto.EspecialidadeResponseDTO;
import br.com.motta.medagenda.exception.RecursoNaoEnconstradoException;
import br.com.motta.medagenda.exception.RegraDeNegocioException;
import br.com.motta.medagenda.mapper.EspecialidadeMapper;
import br.com.motta.medagenda.model.Especialidade;
import br.com.motta.medagenda.repository.EspecialidadeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EspecialidadeServiceImpl implements EspecialidadeService {

    private final EspecialidadeRepository especialidadeRepository;

    public EspecialidadeServiceImpl(EspecialidadeRepository especialidadeRepository) {
        this.especialidadeRepository = especialidadeRepository;
    }


    @Override
    public EspecialidadeResponseDTO cadastrar(EspecialidadeRequestDTO especialidadeDTO) {
        if (especialidadeRepository.findByNome(especialidadeDTO.nome()).isPresent()) {
            throw new RegraDeNegocioException("Já existe um especialidade com esse nome.");
        }
        Especialidade especialidade = EspecialidadeMapper.toEntity(especialidadeDTO);
        Especialidade salva = especialidadeRepository.save(especialidade);
        return EspecialidadeMapper.toDTO(salva);
    }

    @Override
    @Transactional
    public EspecialidadeResponseDTO atualizar(Long id, EspecialidadeRequestDTO especialidadeDTO) {
            Especialidade especialidade = especialidadeRepository.findById(id)
                    .orElseThrow(() -> new RecursoNaoEnconstradoException("Especialidade não encontrada."));
           EspecialidadeMapper.updateEntityFromDTO(especialidadeDTO, especialidade);
           return EspecialidadeMapper.toDTO(especialidade);
    }

    @Override
    public void excluir(Long id) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEnconstradoException("Especialidade não encontrada."));
        especialidadeRepository.delete(especialidade);
    }

    @Override
    public List<EspecialidadeResponseDTO> listarTodos() {
        List<Especialidade> listarEspecialidades = especialidadeRepository.findAll();
        return listarEspecialidades.stream().map(EspecialidadeMapper::toDTO).toList();
    }

    @Override
    public List<EspecialidadeResponseDTO> listarPorNomeContendo(String nome) {
        List<Especialidade> listarEspecialidades = especialidadeRepository.findByNomeContainingIgnoreCase(nome);
        return listarEspecialidades.stream().map(EspecialidadeMapper::toDTO).toList();
    }

    @Override
    public EspecialidadeResponseDTO buscarPorId(Long id) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEnconstradoException("Especialidade não encontrada."));
        return EspecialidadeMapper.toDTO(especialidade);
    }
}
