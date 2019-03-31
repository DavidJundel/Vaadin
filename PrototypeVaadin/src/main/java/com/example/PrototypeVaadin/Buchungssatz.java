package com.example.PrototypeVaadin;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


import com.vaadin.cdi.UIScoped;

@UIScoped
@Entity
@Table(name = "buchungssatz")
public class Buchungssatz implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	public interface Columns {
		public static final String ID = "id";
		public static final String KONTO = "konto";
		public static final String BUCHUNGSTEXT = "buchungstext";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") // , nullable=false
	// @Basic(optional=false)
	private Long id;

	@Column(name = "konto")
	private String konto;

	@Column(name = "gegenkonto")
	private String gegenkonto;

	@Column(name = "buchungstext", length = 4000)
	private String buchungstext;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private EStatus status;

	@Column(name = "betrag")
	private Double betrag;

	@Column(name = "buchungsdatum")
	@Temporal(TemporalType.TIMESTAMP)
	private Date buchungsdatum;

	@Transient
	private Integer hashCode;

	public Buchungssatz() {

	}

	public Buchungssatz(Long id, String konto, String gegenkonto, String buchungstext, EStatus status, double betrag,
			Date buchungsdatum, Integer hashCode) {
		super();
		this.id = id;
		this.konto = konto;
		this.gegenkonto = gegenkonto;
		this.buchungstext = buchungstext;
		this.status = status;
		this.betrag = betrag;
		this.buchungsdatum = buchungsdatum;
		this.hashCode = hashCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKonto() {
		return konto;
	}

	public void setKonto(String konto) {
		this.konto = konto;
	}

	public String getGegenkonto() {
		return gegenkonto;
	}

	public void setGegenkonto(String gegenkonto) {
		this.gegenkonto = gegenkonto;
	}

	public String getBuchungstext() {
		return buchungstext;
	}

	public void setBuchungstext(String buchungstext) {
		this.buchungstext = buchungstext;
	}

	public EStatus getStatus() {
		return status;
	}

	public void setStatus(EStatus status) {
		this.status = status;
	}

	public Double getBetrag() {
		return betrag;
	}

	public void setBetrag(Double betrag) {
		this.betrag = betrag;
	}

	public Date getBuchungsdatum() {
		return buchungsdatum;
	}

	public void setBuchungsdatum(Date buchungsdatum) {
		this.buchungsdatum = buchungsdatum;
	}

	public Integer getHashCode() {
		return hashCode;
	}

	public void setHashCode(Integer hashCode) {
		this.hashCode = hashCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(betrag);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((buchungsdatum == null) ? 0 : buchungsdatum.hashCode());
		result = prime * result + ((buchungstext == null) ? 0 : buchungstext.hashCode());
		result = prime * result + ((gegenkonto == null) ? 0 : gegenkonto.hashCode());
		result = prime * result + ((hashCode == null) ? 0 : hashCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((konto == null) ? 0 : konto.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Buchungssatz other = (Buchungssatz) obj;
		if (Double.doubleToLongBits(betrag) != Double.doubleToLongBits(other.betrag))
			return false;
		if (buchungsdatum == null) {
			if (other.buchungsdatum != null)
				return false;
		} else if (!buchungsdatum.equals(other.buchungsdatum))
			return false;
		if (buchungstext == null) {
			if (other.buchungstext != null)
				return false;
		} else if (!buchungstext.equals(other.buchungstext))
			return false;
		if (gegenkonto == null) {
			if (other.gegenkonto != null)
				return false;
		} else if (!gegenkonto.equals(other.gegenkonto))
			return false;
		if (hashCode == null) {
			if (other.hashCode != null)
				return false;
		} else if (!hashCode.equals(other.hashCode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (konto == null) {
			if (other.konto != null)
				return false;
		} else if (!konto.equals(other.konto))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Buchungssatz [id=" + id + ", konto=" + konto + ", gegenkonto=" + gegenkonto + ", buchungstext="
				+ buchungstext + ", status=" + status + ", betrag=" + betrag + ", buchungsdatum=" + buchungsdatum
				+ ", hashCode=" + hashCode + "]";
	}

}