package br.com.motta.medagenda.controller;

import br.com.motta.medagenda.config.CommonApiResponses;
import br.com.motta.medagenda.config.WebSecurityConfig;
import br.com.motta.medagenda.dto.MedicoEspecialidadeRequestDTO;
import br.com.motta.medagenda.dto.MedicoEspecialidadeResponseDTO;
import br.com.motta.medagenda.service.MedicoEspecialidadeService;
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
@RequestMapping("/medico-especialidades")
@Tag(name = "Medico-Especialidade", description = "Controlador do crud de medico-especialidade")
@SecurityRequirement(name = WebSecurityConfig.SECURITY)
public class MedicoEspecialidadeController {

    private final MedicoEspecialidadeService medicoEspecialidadeService;

    public MedicoEspecialidadeController(MedicoEspecialidadeService medicoEspecialidadeService) {
        this.medicoEspecialidadeService = medicoEspecialidadeService;
    }

    @PostMapping
    @Operation(summary = "Cadastro de medico-especialidade", description = "Vincula um medico a uma especialidade")
    @ApiResponse(responseCode = "201", description = "Vinculo realizado com sucesso")
    @CommonApiResponses
    public ResponseEntity<MedicoEspecialidadeResponseDTO> cadastrar(@RequestBody @Valid MedicoEspecialidadeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoEspecialidadeService.cadastrar(dto));
    }

    @GetMapping("/{medicoId}/especialidades")
    @Operation(summary = "Lista de especialidades de um medico", description = "Lista as especialidades de um medico pelo id")
    @ApiResponse(responseCode = "200", description = "Especialidades do medico encontradas")
    @CommonApiResponses
    public ResponseEntity<List<MedicoEspecialidadeResponseDTO>> listarPorMedicoId(@PathVariable Long medicoId) {
        return ResponseEntity.ok(medicoEspecialidadeService.listarPorMedicoId(medicoId));
    }

    @GetMapping
    @Operation(summary = "Busca de especialidade por nome", description = "Busca dinamica de especialidades pelo fragmento do nome")
    @ApiResponse(responseCode = "201", description = "Especialidades encontradas")
    @CommonApiResponses
    public ResponseEntity<List<MedicoEspecialidadeResponseDTO>> listarPorNomeContendo(@RequestParam String nome) {
        return ResponseEntity.ok(medicoEspecialidadeService.listarPorEspecialidadeNome(nome));
    }

    @DeleteMapping("/{medicoId}/especialidades/{especialidadeId}")
    @Operation(summary = "Excluir vinculo de medico-especialidade")
    @ApiResponse(responseCode = "204", description = "Medico e especialidade desvinculados")
    @CommonApiResponses
    public ResponseEntity<Void> excluir(@PathVariable Long medicoId, @PathVariable Long especialidadeId) {
        medicoEspecialidadeService.excluir(medicoId, especialidadeId);
        return ResponseEntity.noContent().build();
    }
}
