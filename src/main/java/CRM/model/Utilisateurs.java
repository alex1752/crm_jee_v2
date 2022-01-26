package CRM.model;

public class Utilisateurs {

	//Attributs

	private long id;
	private String login;
	private String motDePasse;
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


}
