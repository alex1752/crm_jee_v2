package CRM.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table (name="clients")
public class Clients {

	// Attributs
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY )
	private Long id;
	
	@Column( nullable=false, length=50  )
	private String nom;
	
	@Column( nullable=false, length=50 )
	private String prenom;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Entreprise entreprise;
	
	@Column( nullable=false, unique=true, length=200)
	private String email;
	
	@Column(length=200 )
	private String telephone;
	
	@Column(columnDefinition="BOOLEAN DEFAULT true")
	private Boolean actif;
	
	@Column(length=2000 )
	private String notes;
	
	@OneToMany(mappedBy="client", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Commandes> commandes = new ArrayList<Commandes>();

	
	// Constructeurs

	public Clients() {
	}

	public Clients(String nom, String prenom, Entreprise entreprise, String email, String telephone, Boolean actif,
			String notes) {
		this.nom = nom;
		this.prenom = prenom;
		this.entreprise = entreprise;
		this.email = email;
		this.telephone = telephone;
		this.actif = actif;
		this.notes = notes;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	
	public Entreprise getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	
	public List<Commandes> getCommandes() {
		return commandes;
	}

	public void setCommandes(List<Commandes> commandes) {
		this.commandes = commandes;
	}

	@Override
	public String toString() {
		return "Clients [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", entreprise=" + entreprise + ", email="
				+ email + ", telephone=" + telephone + ", actif=" + actif + ", notes=" + notes + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Clients) {
			return this.id == ((Clients) obj).id;
		}
		return false;
	}
	

}