package br.com.junior.resource;

import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.junior.entidade.User;
import br.com.junior.evento.RecursoCriadoEvento;
import br.com.junior.repository.UserRepository;
import br.com.junior.repository.filter.UserFilter;
import br.com.junior.servico.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationEventPublisher publicadorEvento;

	@GetMapping
	public Page<User> listarTodo(UserFilter userFilter, Pageable pageable) {
		return userRepository.filtrar(userFilter, pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> listarTodo(@PathVariable Integer id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<User> salvar(@Valid @RequestBody User user, HttpServletResponse response) {
		User objetoUser = userService.salvar(user);
		publicadorEvento.publishEvent(new RecursoCriadoEvento(this, response, objetoUser.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(objetoUser);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Integer id) {
		userRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> atualizar(@PathVariable Integer id, @RequestBody User user){
		if(!userRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		user.setId(id);
		userRepository.save(user);
		return ResponseEntity.ok(user);
	}
	
}
