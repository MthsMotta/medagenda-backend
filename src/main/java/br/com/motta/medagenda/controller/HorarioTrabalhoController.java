package br.com.motta.medagenda.controller;

import br.com.motta.medagenda.config.CommonApiResponses;
import br.com.motta.medagenda.config.WebSecurityConfig;
import br.com.motta.medagenda.dto.HorarioTrabalhoRequestDTO;
import br.com.motta.medagenda.dto.HorarioTrabalhoResponseDTO;
import br.com.motta.medagenda.dto.HorarioTrabalhoUpdateDTO;
import br.com.motta.medagenda.service.HorarioTrabalhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agenda")
@Tag(name = "Horario de trabalho", description = "Controlador do crud de horario de trabalho dos medicos")
@SecurityRequirement(name = WebSecurityConfig.SECURITY)
public class HorarioTrabalhoController {

    private final HorarioTrabalhoService horarioTrabalhoService;

    public HorarioTrabalhoController(HorarioTrabalhoService horarioTrabalhoService) {
        this.horarioTrabalhoService = horarioTrabalhoService;
    }

    @PostMapping
    @Operation(summary = "Cadastro de horario de trabalho")
    @ApiResponse(responseCode = "201", description = "Horario de trabalho cadastrado com sucesso")
    @CommonApiResponses
    public ResponseEntity<HorarioTrabalhoResponseDTO> cadastrar(@RequestBody @Valid HorarioTrabalhoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(horarioTrabalhoService.cadastrar(dto));
    }

    @GetMapping
    @Operation(summary = "Busca do horario de trabalho pelo id do medico")
    @ApiResponse(responseCode = "200", description = "Horario de trabalho encontrado")
    @CommonApiResponses
    public ResponseEntity<List<HorarioTrabalhoResponseDTO>> buscarPorMedicoId(@RequestParam(required = false) Long medicoId) {
        return ResponseEntity.ok(horarioTrabalhoService.buscarPeloMedicoId(medicoId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar horario de trabalho")
    @ApiResponse(responseCode = "200", description = "Horario de trabalho atualizado com sucesso")
    @CommonApiResponses
    public ResponseEntity<HorarioTrabalhoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid HorarioTrabalhoUpdateDTO dto) {
        return ResponseEntity.ok(horarioTrabalhoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir horario de trabalho")
    @ApiResponse(responseCode = "204", description = "Horario de trabalho excluido com sucesso")
    @CommonApiResponses
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        horarioTrabalhoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
