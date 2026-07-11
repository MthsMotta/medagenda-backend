package br.com.motta.medagenda.controller;


import br.com.motta.medagenda.config.CommonApiResponses;
import br.com.motta.medagenda.config.WebSecurityConfig;
import br.com.motta.medagenda.dto.PacienteResponseDTO;
import br.com.motta.medagenda.dto.PacienteUpdateDTO;
import br.com.motta.medagenda.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
@Tag(name = "Paciente", description = "Controlador do crud de paciente")
@SecurityRequirement(name = WebSecurityConfig.SECURITY)
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    @Operation(summary = "Lista de pacientes", description = "Lista todos os pacientes")
    @ApiResponse(responseCode = "200", description = "Lista de pacientes")
    @CommonApiResponses
    public ResponseEntity<List<PacienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(pacienteService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca paciente por id")
    @ApiResponse(responseCode = "200", description = "Paciente encontrado")
    @CommonApiResponses
    public ResponseEntity<PacienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados de paciente")
    @ApiResponse(responseCode = "200", description = "Dados do paciente atualizados com sucesso")
    @CommonApiResponses
    public ResponseEntity<PacienteResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid PacienteUpdateDTO pacienteUpdateDto) {
        return ResponseEntity.ok(pacienteService.atualizar(id, pacienteUpdateDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir paciente")
    @ApiResponse(responseCode = "204", description = "Paciente excluido com sucesso")
    @CommonApiResponses
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        pacienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
