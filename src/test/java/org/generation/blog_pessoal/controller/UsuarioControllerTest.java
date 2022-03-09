package org.generation.blog_pessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.generation.blog_pessoal.model.Usuario;
import org.generation.blog_pessoal.repository.UsuarioRepository;
import org.generation.blog_pessoal.service.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

	private @Autowired TestRestTemplate testRestTemplate;
	
	private @Autowired UsuarioService usuarioService;
	
	private @Autowired UsuarioRepository repository;
	
	@BeforeAll
	void start() {
		repository.deleteAll();
	}
	
	@Test
	@Order(1)
	@DisplayName("Register User")
	public void CreateUser() {
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario("Fernando Porto", "bizinho", "134652"));
		
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
	}
	
	@Test
	@Order(2)
	@DisplayName("Do not allow user duplication")
	public void notDuplicationUser() {
		
		usuarioService.CadastrarUsuario(new Usuario("Fernando Porto", "bizinho", "134652"));
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario("Fernando Porto", "bizinho", "134652"));
		
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
	}
}
