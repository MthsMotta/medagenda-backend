package br.com.motta.medagenda.controller;

import br.com.motta.medagenda.config.CommonApiResponses;
import br.com.motta.medagenda.config.WebSecurityConfig;
import br.com.motta.medagenda.dto.UsuarioAdminRequestDTO;
import br.com.motta.medagenda.dto.UsuarioResponseDTO;
import br.com.motta.medagenda.dto.UsuarioUpdateDTO;
import br.com.motta.medagenda.service.UsuarioService;
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
@RequestMapping("/usuarios")
@Tag(name = "Usuario", description = "Controlador do crud de usuario")
@SecurityRequirement(name = WebSecurityConfig.SECURITY)
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @Operation(summary = "Cadastro de usuario", description = "Cadastro de usuario realizado pelo admin para criar medicos ou novos admins")
    @ApiResponse(responseCode = "201", description = "Usuario cadastrado com sucesso")
    @CommonApiResponses
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Valid UsuarioAdminRequestDTO cadastroAdmin) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarAdmin(cadastroAdmin));
    }

    @GetMapping
    @Operation(summary = "Lista de usuarios", description = "Admin acessa todos os usuarios")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios")
    @CommonApiResponses
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos(){
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca usuario pelo id")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @CommonApiResponses
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados de usuario")
    @ApiResponse(responseCode = "200", description = "Usuario atualizado com sucesso")
    @CommonApiResponses
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioUpdateDTO dto){
        return ResponseEntity.ok(usuarioService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuario")
    @ApiResponse(responseCode = "204", description = "Usuario excluido com sucesso")
    @CommonApiResponses
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
