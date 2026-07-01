package br.com.motta.medagenda.service;

import br.com.motta.medagenda.dto.HorarioTrabalhoRequestDTO;
import br.com.motta.medagenda.dto.HorarioTrabalhoResponseDTO;
import br.com.motta.medagenda.dto.HorarioTrabalhoUpdateDTO;
import br.com.motta.medagenda.exception.RecursoNaoEncontradoException;
import br.com.motta.medagenda.exception.RegraDeNegocioException;
import br.com.motta.medagenda.mapper.HorarioTrabalhoMapper;
import br.com.motta.medagenda.model.HorarioTrabalho;
import br.com.motta.medagenda.model.Medico;
import br.com.motta.medagenda.repository.HorarioTrabalhoRepository;
import br.com.motta.medagenda.repository.MedicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HorarioTrabalhoServiceImpl implements HorarioTrabalhoService{

    private final HorarioTrabalhoRepository horarioTrabalhoRepository;
    private final MedicoRepository medicoRepository;

    public HorarioTrabalhoServiceImpl(HorarioTrabalhoRepository horarioTrabalhoRepository, MedicoRepository medicoRepository) {
        this.horarioTrabalhoRepository = horarioTrabalhoRepository;
        this.medicoRepository = medicoRepository;
    }


    @Override
    public HorarioTrabalhoResponseDTO cadastrar(HorarioTrabalhoRequestDTO dto) {
        if (horarioTrabalhoRepository.findByMedicoIdAndDiaSemanaAndHoraInicio(dto.medicoId(), dto.diaSemana(), dto.horaInicio()).isPresent()) {
            throw new RegraDeNegocioException("Já foi realizado o cadastro desse horário de trabalho");
        }
        if (dto.horaInicio().isAfter(dto.horaFim())) {
            throw new RegraDeNegocioException("Horarios inválidos, verifique se o horário de inicio está antes do horario de encerramento do trabalho");
        }
        Medico medico =  medicoRepository.findById(dto.medicoId()).orElseThrow(() -> new RecursoNaoEncontradoException("Medico nao encontrado"));
        HorarioTrabalho horarioTrabalho = HorarioTrabalhoMapper.toEntity(dto, medico);
        return HorarioTrabalhoMapper.toDTO(horarioTrabalhoRepository.save(horarioTrabalho));
    }

    @Override
    @Transactional
    public HorarioTrabalhoResponseDTO atualizar(Long id, HorarioTrabalhoUpdateDTO dto) {
        HorarioTrabalho horarioTrabalhoAtualizado = horarioTrabalhoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Horário de trabalho não encontrado"));
        HorarioTrabalhoMapper.updateEntityFromDTO(dto, horarioTrabalhoAtualizado);
        return HorarioTrabalhoMapper.toDTO(horarioTrabalhoAtualizado);
    }

    @Override
    public void excluir(Long id) {
        HorarioTrabalho horarioTrabalho = horarioTrabalhoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Horario de trabalho não encontrado"));
        horarioTrabalhoRepository.delete(horarioTrabalho);
    }

    @Override
    public List<HorarioTrabalhoResponseDTO> buscarPeloMedicoId(Long medicoId) {
        List<HorarioTrabalho> horarios = horarioTrabalhoRepository.findByMedicoId(medicoId);
        return horarios.stream().map(HorarioTrabalhoMapper::toDTO).toList();
    }
}
