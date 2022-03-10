package org.generation.blog_pessoal.controller;

import java.util.Optional;

import org.generation.blog_pessoal.model.UserLogin;
import org.generation.blog_pessoal.model.Usuario;
import org.generation.blog_pessoal.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Logar usuario")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuario Logado",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserLogin.class))}
        ),

        @ApiResponse(
            responseCode = "401",
            description = "Usuario não autorizado",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserLogin.class))}
        )
    })
    @PostMapping("/logar")
    public ResponseEntity<UserLogin> Autentication (@RequestBody Optional<UserLogin> user){
        return usuarioService.Logar(user).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @Operation(summary = "Cadastrar usuario")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Usuario Criado",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))}
        ),

        @ApiResponse(
            responseCode = "422",
            description = "Usuario já cadastrado",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))}
        )
    })
    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> Post (@RequestBody Usuario usuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.CadastrarUsuario(usuario));
    }
    
}
