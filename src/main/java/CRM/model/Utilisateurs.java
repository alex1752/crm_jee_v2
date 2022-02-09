package CRM.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="utilisateurs")
public class Utilisateurs {

	//Attributs

	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY )
	private long id;
	
	@Column( nullable=false, length=200 )
	private String login;
	
	@Column( nullable=false, length=200 )
	private String motDePasse;
	
	@Column( nullable=false, unique=true , length=200)
	private String email;

	// Constructeur

	public Utilisateurs (){

	}

	public Utilisateurs ( String login, String motDePasse, String email) {

		this.login = login;
		this.motDePasse = motDePasse;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Utilisateurs [id=" + id + ", login=" + login + ", motDePasse=" + motDePasse + ", email=" + email + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Utilisateurs) {
			return this.id == ((Utilisateurs) obj).id && this.email == ((Utilisateurs) obj).email;
		}
		return false;
	}

}
