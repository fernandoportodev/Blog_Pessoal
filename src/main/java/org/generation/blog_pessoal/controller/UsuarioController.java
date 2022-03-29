package org.generation.blog_pessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.generation.blog_pessoal.dtos.UsuarioLogin;
import org.generation.blog_pessoal.model.Usuario;
import org.generation.blog_pessoal.repository.UsuarioRepository;
import org.generation.blog_pessoal.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    
    private @Autowired UsuarioService service;

    private @Autowired UsuarioRepository repository;

    @GetMapping("/all")
    public ResponseEntity <List<Usuario>> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable long id){
        return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/logar")
    public ResponseEntity<UsuarioLogin> autenticationUsuario (@RequestBody Optional<UsuarioLogin> usuario){
        return service.logarUsuario(usuario).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
    
    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> postUsuario (@Valid @RequestBody Usuario usuario){
        return service.cadastrarUsuario(usuario).map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp)).orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Usuario> putUsuario(@Valid @RequestBody Usuario usuario){
        return service.atualizarUsuario(usuario).map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp)).orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}
