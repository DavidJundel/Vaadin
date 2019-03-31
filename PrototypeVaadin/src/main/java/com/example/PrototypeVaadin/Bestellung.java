package com.example.PrototypeVaadin;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vaadin.cdi.UIScoped;

@UIScoped
@Entity
@Table(name = "tribestellung")
public class Bestellung implements Serializable, Cloneable {
	
	public Bestellung()
	{
		
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "create_time")
	private Date created;

	@Column(name = "deleted")
	private boolean deleted;

	@Column(name = "last_update_time")
	private Date updated;

	@Column(name = "abgeschlossen")
	private boolean abgeschlossen;

	@Column(name = "bestelldatum")
	private boolean Date;

	@Column(name = "bezahlt")
	private boolean bezahlt;

	@Column(name = "gebucht")
	private boolean gebucht;

	@Column(name = "typ")
	private String typ;

	@Column(name = "mandant_id")
	private int madant;

	@Column(name = "lieferant_id")
	private int lieferant;

	@Column(name = "transportauftrag_id")
	private int transportauftrag;
	
	@Column(name="externebestellnummer")
	private String externebestellnummer;
	
	@Column(name="status")
	private String status;
	
	@Column(name="nummer")
	private String nummer;
	
	

	public Bestellung(Integer id, java.util.Date created, boolean deleted, java.util.Date updated,
			boolean abgeschlossen, boolean date, boolean bezahlt, boolean gebucht, String typ, int madant,
			int lieferant, int transportauftrag, String externebestellnummer, String status, String nummer) {
		super();
		this.id = id;
		this.created = created;
		this.deleted = deleted;
		this.updated = updated;
		this.abgeschlossen = abgeschlossen;
		Date = date;
		this.bezahlt = bezahlt;
		this.gebucht = gebucht;
		this.typ = typ;
		this.madant = madant;
		this.lieferant = lieferant;
		this.transportauftrag = transportauftrag;
		this.externebestellnummer = externebestellnummer;
		this.status = status;
		this.nummer = nummer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public boolean isAbgeschlossen() {
		return abgeschlossen;
	}

	public void setAbgeschlossen(boolean abgeschlossen) {
		this.abgeschlossen = abgeschlossen;
	}

	public boolean isDate() {
		return Date;
	}

	public void setDate(boolean date) {
		Date = date;
	}

	public boolean isBezahlt() {
		return bezahlt;
	}

	public void setBezahlt(boolean bezahlt) {
		this.bezahlt = bezahlt;
	}

	public boolean isGebucht() {
		return gebucht;
	}

	public void setGebucht(boolean gebucht) {
		this.gebucht = gebucht;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public int getMadant() {
		return madant;
	}

	public void setMadant(int madant) {
		this.madant = madant;
	}

	public int getLieferant() {
		return lieferant;
	}

	public void setLieferant(int lieferant) {
		this.lieferant = lieferant;
	}

	public int getTransportauftrag() {
		return transportauftrag;
	}

	public void setTransportauftrag(int transportauftrag) {
		this.transportauftrag = transportauftrag;
	}

	public String getExternebestellnummer() {
		return externebestellnummer;
	}

	public void setExternebestellnummer(String externebestellnummer) {
		this.externebestellnummer = externebestellnummer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNummer() {
		return nummer;
	}

	public void setNummer(String nummer) {
		this.nummer = nummer;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (Date ? 1231 : 1237);
		result = prime * result + (abgeschlossen ? 1231 : 1237);
		result = prime * result + (bezahlt ? 1231 : 1237);
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + (deleted ? 1231 : 1237);
		result = prime * result + ((externebestellnummer == null) ? 0 : externebestellnummer.hashCode());
		result = prime * result + (gebucht ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + lieferant;
		result = prime * result + madant;
		result = prime * result + ((nummer == null) ? 0 : nummer.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + transportauftrag;
		result = prime * result + ((typ == null) ? 0 : typ.hashCode());
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
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
		Bestellung other = (Bestellung) obj;
		if (Date != other.Date)
			return false;
		if (abgeschlossen != other.abgeschlossen)
			return false;
		if (bezahlt != other.bezahlt)
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (deleted != other.deleted)
			return false;
		if (externebestellnummer == null) {
			if (other.externebestellnummer != null)
				return false;
		} else if (!externebestellnummer.equals(other.externebestellnummer))
			return false;
		if (gebucht != other.gebucht)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lieferant != other.lieferant)
			return false;
		if (madant != other.madant)
			return false;
		if (nummer == null) {
			if (other.nummer != null)
				return false;
		} else if (!nummer.equals(other.nummer))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (transportauftrag != other.transportauftrag)
			return false;
		if (typ == null) {
			if (other.typ != null)
				return false;
		} else if (!typ.equals(other.typ))
			return false;
		if (updated == null) {
			if (other.updated != null)
				return false;
		} else if (!updated.equals(other.updated))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bestellung [id=" + id + ", created=" + created + ", deleted=" + deleted + ", updated=" + updated
				+ ", abgeschlossen=" + abgeschlossen + ", Date=" + Date + ", bezahlt=" + bezahlt + ", gebucht="
				+ gebucht + ", typ=" + typ + ", madant=" + madant + ", lieferant=" + lieferant + ", transportauftrag="
				+ transportauftrag + ", externebestellnummer=" + externebestellnummer + ", status=" + status
				+ ", nummer=" + nummer + "]";
	}

}
	