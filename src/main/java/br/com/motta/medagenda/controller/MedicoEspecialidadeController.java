package br.com.motta.medagenda.controller;

import br.com.motta.medagenda.dto.MedicoEspecialidadeRequestDTO;
import br.com.motta.medagenda.dto.MedicoEspecialidadeResponseDTO;
import br.com.motta.medagenda.service.MedicoEspecialidadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medico-especialidades")
public class MedicoEspecialidadeController {

    private final MedicoEspecialidadeService medicoEspecialidadeService;

    public MedicoEspecialidadeController(MedicoEspecialidadeService medicoEspecialidadeService) {
        this.medicoEspecialidadeService = medicoEspecialidadeService;
    }

    @PostMapping
    public ResponseEntity<MedicoEspecialidadeResponseDTO> cadastrar(@RequestBody @Valid MedicoEspecialidadeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoEspecialidadeService.cadastrar(dto));
    }

    @GetMapping("/{medicoId}/especialidades")
    public ResponseEntity<List<MedicoEspecialidadeResponseDTO>> listarPorMedicoId(@PathVariable Long medicoId) {
        return ResponseEntity.ok(medicoEspecialidadeService.listarPorMedicoId(medicoId));
    }

    @GetMapping
    public ResponseEntity<List<MedicoEspecialidadeResponseDTO>> listarPorNomeContendo(@RequestParam String nome) {
        return ResponseEntity.ok(medicoEspecialidadeService.listarPorEspecialidadeNome(nome));
    }

    @DeleteMapping("/{medicoId}/especialidades/{especialidadeId}")
    public ResponseEntity<Void> excluir(@PathVariable Long medicoId, @PathVariable Long especialidadeId) {
        medicoEspecialidadeService.excluir(medicoId, especialidadeId);
        return ResponseEntity.noContent().build();
    }
}
