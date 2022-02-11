package CRM.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

@Entity
@Table(name= "entreprise")
public class Entreprise {

	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column( nullable=false, length=50  )
	private String nom;
	
	@Column(length=50)
	private String telephone; 
	
	@Column( nullable=false, unique=true, length=200)
	private String email; 
	
	@Column(length=200)
	private String domaine;
	
	@Column(length=50)
	private String type;
	
	@OneToOne(mappedBy= "entreprise", fetch = FetchType.LAZY, cascade= {CascadeType.MERGE})
	private Clients client;
	
	@PreRemove
    private void preRemove() {
        if(this.client != null) {
            this.client.setEntreprise(null);
        }
    }
	
	public Entreprise() {
		
	}

	public Entreprise(String nom, String telephone, String email, String domaine, String type) {
		this.nom = nom;
		this.telephone = telephone;
		this.email = email;
		this.domaine = domaine;
		this.type = type;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDomaine() {
		return domaine;
	}

	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Clients getClient() {
		return client;
	}

	public void setClient(Clients client) {
		this.client = client;
	}
	
	
}
