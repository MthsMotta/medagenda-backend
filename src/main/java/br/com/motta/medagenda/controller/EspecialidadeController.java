package br.com.motta.medagenda.controller;

import br.com.motta.medagenda.dto.EspecialidadeRequestDTO;
import br.com.motta.medagenda.dto.EspecialidadeResponseDTO;
import br.com.motta.medagenda.service.EspecialidadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadeController {

    private final EspecialidadeService especialidadeService;

    public EspecialidadeController(EspecialidadeService especialidadeService) {
        this.especialidadeService = especialidadeService;
    }


    @PostMapping
    public ResponseEntity<EspecialidadeResponseDTO> cadastrar(@RequestBody @Valid EspecialidadeRequestDTO especialidade){
        return ResponseEntity.status(HttpStatus.CREATED).body(especialidadeService.cadastrar(especialidade));
    }

    @GetMapping
    public ResponseEntity<List<EspecialidadeResponseDTO>> listar(
            @RequestParam(required = false) String nome){
        if (nome != null){
            return ResponseEntity.ok(especialidadeService.listarPorNomeContendo(nome));
        }
        return ResponseEntity.ok(especialidadeService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok().body(especialidadeService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadeResponseDTO> atualizarEspecialidade(@PathVariable Long id, @RequestBody @Valid EspecialidadeRequestDTO especialidade){
        return ResponseEntity.ok().body(especialidadeService.atualizar(id, especialidade));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEspecialidade(@PathVariable Long id){
        especialidadeService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
