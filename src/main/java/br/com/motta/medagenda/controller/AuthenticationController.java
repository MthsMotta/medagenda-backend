package br.com.motta.medagenda.controller;

import br.com.motta.medagenda.config.CommonApiResponses;
import br.com.motta.medagenda.config.WebSecurityConfig;
import br.com.motta.medagenda.dto.CadastroDTO;
import br.com.motta.medagenda.dto.LoginDTO;
import br.com.motta.medagenda.dto.LoginResponseDTO;
import br.com.motta.medagenda.dto.UsuarioResponseDTO;
import br.com.motta.medagenda.model.Usuario;
import br.com.motta.medagenda.security.TokenService;
import br.com.motta.medagenda.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Controlador para login de usuarios e cadastro de pacientes")
@SecurityRequirement(name = WebSecurityConfig.SECURITY)
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, UsuarioService usuarioService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuario")
    @ApiResponse(responseCode = "200", description = "Usuario logado com sucesso")
    @CommonApiResponses
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO login){
        var usernamePassword = new UsernamePasswordAuthenticationToken(login.email(), login.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastro de paciente")
    @ApiResponse(responseCode = "201", description = "Paciente cadastrado com sucesso")
    @CommonApiResponses
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Valid CadastroDTO cadastro){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrar(cadastro));
    }
}
