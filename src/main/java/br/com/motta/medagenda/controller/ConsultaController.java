package br.com.motta.medagenda.controller;

import br.com.motta.medagenda.dto.ConsultaRequestDTO;
import br.com.motta.medagenda.dto.ConsultaResponseDTO;
import br.com.motta.medagenda.dto.ConsultaUpdateDTO;
import br.com.motta.medagenda.model.StatusConsulta;
import br.com.motta.medagenda.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> cadastrar(@Valid @RequestBody ConsultaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultaService.cadastrar(dto));
    }

    @GetMapping
    public ResponseEntity<Page<ConsultaResponseDTO>> listar(
            @RequestParam(required = false) Long medicoId,
            @RequestParam(required = false) Long pacienteId,
            @RequestParam(required = false) StatusConsulta statusConsulta,
            @RequestParam(required = false) LocalDateTime inicio,
            @RequestParam(required = false) LocalDateTime fim,
            Pageable pageable) {
        return ResponseEntity.ok(consultaService.listarTodos(medicoId, pacienteId, statusConsulta, inicio, fim, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid ConsultaUpdateDTO dto) {
        return ResponseEntity.ok(consultaService.atualizar(id, dto));
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<ConsultaResponseDTO> confirmar(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.confirmar(id));
    }

    @PutMapping("/{id}/realizada")
    public ResponseEntity<ConsultaResponseDTO> realizada(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.realizada(id));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ConsultaResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.cancelar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        consultaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
