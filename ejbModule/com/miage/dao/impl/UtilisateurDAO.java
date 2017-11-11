package com.miage.dao.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.miage.dao.IUtilisateurDAO;
import com.miage.entity.User;

/**
 * Classe DAO pour les utilisateurs
 * @author tarik
 * @author Eric
 */
@Stateful
public class UtilisateurDAO implements IUtilisateurDAO {
	
	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public User findById(String idNom) {
		final User result = entityManager.find(User.class, idNom);
		return result;
	}
	

	@Override
	public void delete(User entity) {
		User attached = entityManager.merge(entity);
		entityManager.remove(attached);
	}

	@Override
	public User save(User entity) {
		final User savedUtilisateurEntity = entityManager.merge(entity);
		return savedUtilisateurEntity;
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return entityManager
				.createQuery("select u from User u")
				.getResultList();
	}

	@Override
	public int countAll() {
		return findAll().size() ; 
	}
}
