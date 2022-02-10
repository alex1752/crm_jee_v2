package CRM.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="modification")
public class Modification {

	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY )
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Utilisateurs utilisateur;
	
	private String historique;
	
	public Modification() {
	}
	
	public Modification(String historique) {
		this.historique=historique;
	}
	
	public Modification(Utilisateurs utilisateur,String historique) {
		this.historique=historique;
		this.utilisateur=utilisateur;
	}

	public Long getId() {
		return id;
	}


	public Utilisateurs getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateurs utilisateur) {
		this.utilisateur = utilisateur;
	}

	public String getHistorique() {
		return historique;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Modification) {
			return this.id == ((Modification) obj).id;
		}
		return false;
	}
	
}
