package br.com.motta.medagenda.controller;

import br.com.motta.medagenda.config.CommonApiResponses;
import br.com.motta.medagenda.config.WebSecurityConfig;
import br.com.motta.medagenda.dto.ConsultaRequestDTO;
import br.com.motta.medagenda.dto.ConsultaResponseDTO;
import br.com.motta.medagenda.dto.ConsultaUpdateDTO;
import br.com.motta.medagenda.model.StatusConsulta;
import br.com.motta.medagenda.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/consultas")
@Tag(name = "Consulta", description = "Controlador do crud de consultas")
@SecurityRequirement(name = WebSecurityConfig.SECURITY)
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    @Operation(summary = "Cadastro de consulta")
    @ApiResponse(responseCode = "201", description = "Consulta cadastrada com sucesso")
    @CommonApiResponses
    public ResponseEntity<ConsultaResponseDTO> cadastrar(@Valid @RequestBody ConsultaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultaService.cadastrar(dto));
    }

    @GetMapping
    @Operation(summary = "Lista de consultas", description = "Filtro dinamico que lista as consultas do paciente logado ou as consultas de um medico")
    @ApiResponse(responseCode = "200", description = "Consultas disponiveis")
    @CommonApiResponses
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
    @Operation(summary = "Buscar consulta", description = "busca consulta pelo id")
    @ApiResponse(responseCode = "200", description = "Consulta disponivel")
    @CommonApiResponses
    public ResponseEntity<ConsultaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar consulta", description = "Método para atualizar dados de uma consulta")
    @ApiResponse(responseCode = "200", description = "Consulta atualizada com sucesso")
    @CommonApiResponses
    public ResponseEntity<ConsultaResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid ConsultaUpdateDTO dto) {
        return ResponseEntity.ok(consultaService.atualizar(id, dto));
    }

    @PutMapping("/{id}/confirmar")
    @Operation(summary = "Confirmar consulta", description = "Método para confirmar uma consulta")
    @ApiResponse(responseCode = "200", description = "Consulta confirmada com sucesso")
    @CommonApiResponses
    public ResponseEntity<ConsultaResponseDTO> confirmar(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.confirmar(id));
    }

    @PutMapping("/{id}/realizada")
    @Operation(summary = "Finalizar consulta", description = "Método para finalizar uma consulta")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso")
    @CommonApiResponses
    public ResponseEntity<ConsultaResponseDTO> realizada(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.realizada(id));
    }

    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar consulta", description = "Método para cancelar uma consulta")
    @ApiResponse(responseCode = "200", description = "Consulta cancelada com sucesso")
    @CommonApiResponses
    public ResponseEntity<ConsultaResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.cancelar(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir consulta", description = "Método para excluir uma consulta")
    @ApiResponse(responseCode = "204", description = "Consulta excluida com sucesso")
    @CommonApiResponses
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        consultaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
