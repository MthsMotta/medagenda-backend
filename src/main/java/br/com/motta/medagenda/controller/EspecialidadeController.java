package br.com.motta.medagenda.controller;

import br.com.motta.medagenda.config.CommonApiResponses;
import br.com.motta.medagenda.config.WebSecurityConfig;
import br.com.motta.medagenda.dto.EspecialidadeRequestDTO;
import br.com.motta.medagenda.dto.EspecialidadeResponseDTO;
import br.com.motta.medagenda.service.EspecialidadeService;
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
@RequestMapping("/especialidades")
@Tag(name = "Especialidade", description = "Controlador do crud de especialidades")
@SecurityRequirement(name = WebSecurityConfig.SECURITY)
public class EspecialidadeController {

    private final EspecialidadeService especialidadeService;

    public EspecialidadeController(EspecialidadeService especialidadeService) {
        this.especialidadeService = especialidadeService;
    }


    @PostMapping
    @Operation(summary = "Cadastro de especialidade")
    @ApiResponse(responseCode = "201", description = "Especialidade cadastrada com sucesso")
    @CommonApiResponses
    public ResponseEntity<EspecialidadeResponseDTO> cadastrar(@RequestBody @Valid EspecialidadeRequestDTO especialidade){
        return ResponseEntity.status(HttpStatus.CREATED).body(especialidadeService.cadastrar(especialidade));
    }

    @GetMapping
    @Operation(summary = "Lista de especialidades")
    @ApiResponse(responseCode = "200", description = "Especialidades listadas")
    @CommonApiResponses
    public ResponseEntity<List<EspecialidadeResponseDTO>> listar(
            @RequestParam(required = false) String nome){
        if (nome != null){
            return ResponseEntity.ok(especialidadeService.listarPorNomeContendo(nome));
        }
        return ResponseEntity.ok(especialidadeService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar especialidade por id")
    @ApiResponse(responseCode = "200", description = "Especialidade encontrada")
    @CommonApiResponses
    public ResponseEntity<EspecialidadeResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok().body(especialidadeService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar especialidade")
    @ApiResponse(responseCode = "200", description = "Especialidade alterada com sucesso")
    @CommonApiResponses
    public ResponseEntity<EspecialidadeResponseDTO> atualizarEspecialidade(@PathVariable Long id, @RequestBody @Valid EspecialidadeRequestDTO especialidade){
        return ResponseEntity.ok().body(especialidadeService.atualizar(id, especialidade));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "excluir especialidade")
    @ApiResponse(responseCode = "204", description = "Especialidade excluida com sucesso")
    @CommonApiResponses
    public ResponseEntity<Void> excluirEspecialidade(@PathVariable Long id){
        especialidadeService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
