package br.com.junior.repository.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.junior.entidade.User;
import br.com.junior.repository.filter.UserFilter;


public interface UserRepositoryQuery{
	
	Page<User> filtrar(UserFilter filter, Pageable pageable);

}
