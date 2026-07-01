package br.com.motta.medagenda.controller;

import br.com.motta.medagenda.dto.HorarioTrabalhoRequestDTO;
import br.com.motta.medagenda.dto.HorarioTrabalhoResponseDTO;
import br.com.motta.medagenda.dto.HorarioTrabalhoUpdateDTO;
import br.com.motta.medagenda.service.HorarioTrabalhoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horariostrabalhos")
public class HorarioTrabalhoController {

    private final HorarioTrabalhoService horarioTrabalhoService;

    public HorarioTrabalhoController(HorarioTrabalhoService horarioTrabalhoService) {
        this.horarioTrabalhoService = horarioTrabalhoService;
    }

    @PostMapping
    public ResponseEntity<HorarioTrabalhoResponseDTO> cadastrar(@RequestBody @Valid HorarioTrabalhoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(horarioTrabalhoService.cadastrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<HorarioTrabalhoResponseDTO>> buscarPorMedicoId(@RequestParam(required = false) Long medicoId) {
        return ResponseEntity.ok(horarioTrabalhoService.buscarPeloMedicoId(medicoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioTrabalhoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid HorarioTrabalhoUpdateDTO dto) {
        return ResponseEntity.ok(horarioTrabalhoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        horarioTrabalhoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
