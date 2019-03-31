package com.example.PrototypeVaadin;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class LoginDAO {

	static Login login;

	public LoginDAO() {

	}

	@PersistenceContext(unitName = "athenos")
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public void register(Login login) {
		em.merge(login);

	}

	public boolean exists(String n, String p) {
		login = em.find(Login.class, n);
		if (login != null) {
			String name = login.getUsername();
			String password = login.getPassword();
			if (n.equals(name) && p.equals(password)) {
				return true;
			}
		}
		return false;
	}

}
