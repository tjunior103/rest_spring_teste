package br.com.junior.repository.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.junior.entidade.User;
import br.com.junior.repository.filter.UserFilter;

public class UserRepositoryImpl implements UserRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<User> filtrar(UserFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> root = criteria.from(User.class);

		Predicate[] predicates = criarRetricooes(filter, builder, root);
		criteria.where(predicates);

		TypedQuery<User> consulta = manager.createQuery(criteria);

		adicionarRestricoesPaginacao(consulta, pageable); // montar pagina para a consulta
		return new PageImpl<>(consulta.getResultList(), pageable, total(filter));

	}

	private Predicate[] criarRetricooes(UserFilter filter, CriteriaBuilder builder, Root<User> root) {
		List<Predicate> predicates = new ArrayList<>();
		if (StringUtils.hasLength(filter.getName())) { 
			predicates.add(builder.like(root.get("name"), "%" + filter.getName() + "%"));
		}
		
		if (StringUtils.hasText(filter.getUsername())) {
			predicates.add(builder.like(root.get("username"), "%" + filter.getUsername() + "%"));
		}
		
		if (StringUtils.hasText(filter.getEmail())) { 
			predicates.add(builder.equal(root.get("email"), filter.getEmail()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void adicionarRestricoesPaginacao(TypedQuery<User> consulta, Pageable pageable) {
		int paginaAtualTela = pageable.getPageNumber(); // vem a pagina atual da tela
		int totalRegistroPorPagina = pageable.getPageSize();// quantos registro esta sendo mostrado por tela
		int primeiroRegistroTela = paginaAtualTela * totalRegistroPorPagina;
		consulta.setFirstResult(primeiroRegistroTela);
		consulta.setMaxResults(totalRegistroPorPagina);
	}

	private long total(UserFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<User> root = criteria.from(User.class);

		Predicate[] predicates = criarRetricooes(filter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));

		return manager.createQuery(criteria).getSingleResult();
	}

}
