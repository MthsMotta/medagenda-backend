package br.com.motta.medagenda.controller;

import br.com.motta.medagenda.dto.CadastroDTO;
import br.com.motta.medagenda.dto.LoginDTO;
import br.com.motta.medagenda.dto.LoginResponseDTO;
import br.com.motta.medagenda.dto.UsuarioResponseDTO;
import br.com.motta.medagenda.model.Usuario;
import br.com.motta.medagenda.security.TokenService;
import br.com.motta.medagenda.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO login){
        var usernamePassword = new UsernamePasswordAuthenticationToken(login.email(), login.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Valid CadastroDTO cadastro){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrar(cadastro));
    }
}
