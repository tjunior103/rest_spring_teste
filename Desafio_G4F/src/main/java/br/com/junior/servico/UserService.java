package br.com.junior.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.junior.entidade.User;
import br.com.junior.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User salvar(User user) {
		user.setIs_enable(true);		
		return userRepository.save(user);
	}
}
