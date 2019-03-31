package com.example.PrototypeVaadin;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Named
@ApplicationScoped
@Transactional
public class BestellungDAO {
	
	Bestellung bestellung;
	
	public BestellungDAO()
	{
		
	}
	
	@PersistenceContext(unitName = "athenos")
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}
	
	@SuppressWarnings("unchecked")
	public List<Bestellung> findAlleBestellungen(int anzahl) {
		
		List<Bestellung> resultList = em.createQuery("FROM Bestellung").setMaxResults(anzahl).getResultList();
		return resultList;
	}

}
