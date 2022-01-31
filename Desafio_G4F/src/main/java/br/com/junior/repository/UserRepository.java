package br.com.junior.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.junior.entidade.User;
import br.com.junior.repository.dao.UserRepositoryQuery;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryQuery {

}
