package CRM.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;


@Entity
@Table(name = "produit")
public class Produit {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column( nullable=false, length=50)
	private String nom;
	
	@Column(length=200)
	private String description;
	
	@Column( nullable=false)
	private double prix;
	
	@ManyToMany(mappedBy = "listProduits", fetch = FetchType.LAZY)
	private List<Commandes> listCommandes = new ArrayList<Commandes>();
	
	public Produit () {
		
	}
	
	@PreRemove
	private void preRemove() {
		for(Commandes c : this.listCommandes) {
			c.getListProduits().remove(this);
		}
	}

	public Produit(String nom, String description, double prix) {
		
		this.nom = nom;
		this.description = description;
		this.prix = prix;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public List<Commandes> getListCommandes() {
		return listCommandes;
	}

	public void setListCommandes(List<Commandes> listCommandes) {
		this.listCommandes = listCommandes;
	}
	
	public void addCommande(Commandes commande) {
		this.listCommandes.add(commande);
		commande.getListProduits().add(this);
	}
	
	public void removeCommande(Commandes commande) {
		this.listCommandes.remove(commande);
		commande.getListProduits().remove(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Produit) {
			return this.id == ((Produit) obj).id;
		}
		return false;
	}
	
}
