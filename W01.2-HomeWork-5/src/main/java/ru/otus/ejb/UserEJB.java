package ru.otus.web;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ru.otus.dataset.UserEntity;

@Stateless
public class UserEJB {
	
	@PersistenceContext(unitName="jpa")
	private EntityManager em;
	
	public UserEntity createUser(UserEntity user) {
		try {
			user.setPassword(ru.otus.web.AuthenticationUtils.encodeSHA256(user.getPassword()));
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		}

		em.persist(user);

		return user;
	}

	public UserEntity findUserByName(String name) {
		TypedQuery<UserEntity> query = em.createNamedQuery("findUserByName", UserEntity.class);
		query.setParameter("name", name);
		UserEntity user = null;
		try {
			user = query.getSingleResult();
		} catch (Exception e) {
			// getSingleResult throws NoResultException in case there is no user in DB
			// ignore exception and return NULL for user instead
		}
		return user;
	}
}
