package CRM.model;

public class Clients {

	// Attributs

	private Long id;
	private String nom;
	private String prenom;
	private String entreprise;
	private String email;
	private String telephone;
	private Boolean actif;
	private String notes;

	// Constructeurs

	public Clients() {
	}

	public Clients(String nom, String prenom, String entreprise, String email, String telephone, Boolean actif,
			String notes) {

		this.id = id;
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

	public String getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(String entreprise) {
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

	@Override
	public String toString() {
		return "Clients [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", entreprise=" + entreprise + ", email="
				+ email + ", telephone=" + telephone + ", actif=" + actif + ", notes=" + notes + "]";
	}

	public void getBla(String[] columns) {
		for (String column : columns) {
			if (column.equals("nom")) {
				System.out.print(getNom());
			} else if (column.equals("prenom")) {
				System.out.print(getPrenom());
			} else if (column.equals("entreprise")) {
				System.out.print(getEntreprise());
			} else if (column.equals("email")) {
				System.out.print(getEmail());
			} else if (column.equals("telephone")) {
				System.out.print(getTelephone());
			} else if (column.equals("actif")) {
				System.out.print(isActif());
			} else if (column.equals("notes")) {
				System.out.print(getNotes());
			}
			System.out.print(" ".repeat(5));
		}
		System.out.println("");
	}
}