package br.com.motta.medagenda.controller;

import br.com.motta.medagenda.config.CommonApiResponses;
import br.com.motta.medagenda.config.WebSecurityConfig;
import br.com.motta.medagenda.dto.MedicoRequestDTO;
import br.com.motta.medagenda.dto.MedicoResponseDTO;
import br.com.motta.medagenda.dto.MedicoUpdateDTO;
import br.com.motta.medagenda.service.MedicoService;
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
@RequestMapping("/medicos")
@Tag(name = "Medico", description = "Controlador do crud de medico")
@SecurityRequirement(name = WebSecurityConfig.SECURITY)
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    @Operation(summary = "Cadastro de medico", description = "Cadastro de medico feito por um admin vinculando um id de usuario que não seja paciente")
    @ApiResponse(responseCode = "201", description = "Medico cadastrado com sucesso")
    @CommonApiResponses
    public ResponseEntity<MedicoResponseDTO> salvar(@RequestBody @Valid MedicoRequestDTO medicoRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoService.cadastrar(medicoRequestDTO));
    }

    @GetMapping
    @Operation(summary = "Lista de medicos")
    @ApiResponse(responseCode = "200", description = "Lista de medicos")
    @CommonApiResponses
    public ResponseEntity<List<MedicoResponseDTO>> listar(@RequestParam(required = false) String nome) {
        if (nome != null){
            return ResponseEntity.ok(medicoService.listarPorNomeContendo(nome));
        }
        return ResponseEntity.ok(medicoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca medico pelo id")
    @ApiResponse(responseCode = "201", description = "Medico encontrado")
    @CommonApiResponses
    public ResponseEntity<MedicoResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(medicoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do medico")
    @ApiResponse(responseCode = "200", description = "Dados do medico atualizado com sucesso")
    @CommonApiResponses
    public ResponseEntity<MedicoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid MedicoUpdateDTO medicoUpdateDTO) {
        return ResponseEntity.ok(medicoService.atualizar(id, medicoUpdateDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir medico")
    @ApiResponse(responseCode = "204", description = "Medico excluido com sucesso")
    @CommonApiResponses
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        medicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
