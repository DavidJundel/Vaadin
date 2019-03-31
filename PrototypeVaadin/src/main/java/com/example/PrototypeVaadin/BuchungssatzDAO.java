package com.example.PrototypeVaadin;

import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Named
@ApplicationScoped
@Transactional
public class BuchungssatzDAO {

	Buchungssatz buchungssatz;

	public BuchungssatzDAO() {

	}

	@PersistenceContext(unitName = "athenos")
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public void save(Buchungssatz buchungssatz) {
		em.merge(buchungssatz);
	}

	public Buchungssatz load(Long l) {

		buchungssatz = em.find(Buchungssatz.class, l);
		return buchungssatz;

	}

	public Buchungssatz daten() {

		return buchungssatz;
	}

	public void delete(Long l) {
		buchungssatz = em.find(Buchungssatz.class, l);

		em.remove(buchungssatz);
	}

	public void edit(Long l, String konto, String gegenkonto, String buchungstext, Date buchungsdatum, double betrag) {

		buchungssatz = em.find(Buchungssatz.class, l);
		buchungssatz.setKonto(konto);
		buchungssatz.setGegenkonto(gegenkonto);
		buchungssatz.setBuchungstext(buchungstext);
		buchungssatz.setBuchungsdatum(buchungsdatum);
		buchungssatz.setBetrag(betrag);
		em.merge(buchungssatz);

	}

	public boolean login() {

		return true;

	}

	@SuppressWarnings("unchecked")
	public List<Long> getId() {
		Query q = em.createQuery("SELECT MAX(id) FROM Buchungssatz");
		List<Long> resultList = q.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public List<Buchungssatz> findAlleBuchungssaetze(int anzahl) {

		// em.createNativeQuery("")
		// session.createSQLQuery(arg0)

		// Session session = (Session) em.getDelegate();

		// Criteria criteria = session.createCriteria(Buchungssatz.class);
		// criteria.add(Restrictions.like(Buchungssatz.Columns.BUCHUNGSTEXT,
		// "%hallo%"));
		// criteria.addOrder(Order.asc(Buchungssatz.Columns.ID));
		// criteria.setMaxResults(10);
		// criteria.list();
		// criteria.scroll();

		// ScrollableResults scrollResult = session.createQuery("FROM
		// Buchungssatz").scroll(ScrollMode.FORWARD_ONLY);
		//
		// while (scrollResult.next()) {
		// Buchungssatz buchungssatz = (Buchungssatz) scrollResult.get()[0];
		// //GO ON
		// //LOGIK
		// }
		// scrollResult.close();

		List<Buchungssatz> resultList = em.createQuery("FROM Buchungssatz").getResultList();
		// .setMaxResults(anzahl)
		// System.out.println(" ################### " + resultList.size() + " -
		// " + anzahl);
		// for (Buchungssatz b : resultList) {
		// System.out.println(b.getId() + " -- " + b.getKonto());
		// }
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public List<Buchungssatz> findBuchungssaetze(int wahl, String name) {
		if (wahl == 1) {
			Query q = em.createQuery("FROM Buchungssatz WHERE konto like ?1");
			q.setParameter(1, "%" + name + "%");
			List<Buchungssatz> resultList = q.getResultList();
			return resultList;
		} else if (wahl == 2) {
			Query q = em.createQuery("FROM Buchungssatz WHERE gegenkonto like ?1");
			q.setParameter(1, "%" + name + "%");
			List<Buchungssatz> resultList = q.getResultList();
			return resultList;

		} else {
			Query q = em.createQuery("FROM Buchungssatz WHERE gegenkonto like ?1");
			q.setParameter(1, "%" + name + "%");
			List<Buchungssatz> resultList = q.getResultList();
			return resultList;

		}
	}

	@SuppressWarnings("unchecked")
	public List<Buchungssatz> findBuchungssaetzeDatum(Date von, Date bis) {
		Query q = em.createQuery("FROM Buchungssatz WHERE buchungsdatum BETWEEN ?1 AND ?2");
		q.setParameter(1, von);
		q.setParameter(2, bis);
		List<Buchungssatz> resultList = q.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public List<Buchungssatz> findBuchungssaetzeBetrag(Double betrag) {
		Query q = em.createQuery("FROM Buchungssatz WHERE betrag <= ?1");
		q.setParameter(1, betrag);
		List<Buchungssatz> resultList = q.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public List<Buchungssatz> findBuchungssaetzeKontoBetrag(String name, Double betrag) {
		Query q = em.createQuery("FROM Buchungssatz WHERE konto like ?1 AND betrag <= ?2");
		q.setParameter(1, "%" + name + "%");
		q.setParameter(2, betrag);
		List<Buchungssatz> resultList = q.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public List<Buchungssatz> findBuchungssaetzeKontoBetragDatum(String name, Double betrag, Date von, Date bis) {
		Query q = em.createQuery(
				"FROM Buchungssatz WHERE konto like ?1 AND betrag <= ?2 AND buchungsdatum BETWEEN ?3 AND ?4");
		q.setParameter(1, "%" + name + "%");
		q.setParameter(2, betrag);
		q.setParameter(3, von);
		q.setParameter(4, bis);
		List<Buchungssatz> resultList = q.getResultList();
		return resultList;

	}

	// }
}
