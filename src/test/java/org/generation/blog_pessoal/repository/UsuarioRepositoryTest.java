package org.generation.blog_pessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.generation.blog_pessoal.model.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

		
		@Autowired
		private UsuarioRepository repository;
		
		@BeforeAll
		void start() {
			repository.deleteAll();
			
			repository.save(new Usuario("Fernando Porto", "bizinho", "134652"));
			repository.save(new Usuario("Gustavo Porto", "gugul5", "123456"));
			repository.save(new Usuario("Taynara Porto", "taybalau", "987654"));
		}
		
		@Test
		@DisplayName("Teste FindByUsuario")
		void searchUserReturnTrue() {
			
			//WHEN
			Optional<Usuario> optional = repository.findByUsuario("bizinho");
			
			//THEN
			assertTrue(optional.get().getUsuario().equals("bizinho"));
		}
		
		@Test
		@DisplayName("Teste FindAll")
		void searchAllReturnTreeUsers() {
			
			//WHEN
			List<Usuario> list = repository.findAllByNomeContainingIgnoreCase("Porto");
			
			//THEN
			assertEquals(3, list.size());
			assertTrue(list.get(0).getNome().equals("Fernando Porto"));
			assertTrue(list.get(1).getNome().equals("Gustavo Porto"));
			assertTrue(list.get(2).getNome().equals("Taynara Porto"));
}
}