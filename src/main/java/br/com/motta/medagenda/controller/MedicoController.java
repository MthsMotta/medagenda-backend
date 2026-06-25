package br.com.motta.medagenda.controller;

import br.com.motta.medagenda.dto.MedicoRequestDTO;
import br.com.motta.medagenda.dto.MedicoResponseDTO;
import br.com.motta.medagenda.dto.MedicoUpdateDTO;
import br.com.motta.medagenda.service.MedicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    public ResponseEntity<MedicoResponseDTO> salvar(@RequestBody @Valid MedicoRequestDTO medicoRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoService.cadastrar(medicoRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<MedicoResponseDTO>> listar(@RequestParam(required = false) String nome) {
        if (nome != null){
            return ResponseEntity.ok(medicoService.listarPorNomeContendo(nome));
        }
        return ResponseEntity.ok(medicoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(medicoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid MedicoUpdateDTO medicoUpdateDTO) {
        return ResponseEntity.ok(medicoService.atualizar(id, medicoUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        medicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
