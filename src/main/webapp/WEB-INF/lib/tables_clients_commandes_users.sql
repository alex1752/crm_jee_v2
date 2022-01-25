/*Création de la table clients*/
CREATE TABLE clients
    (id SERIAL,
    nom varchar(50) NOT NULL,
    prenom varchar(50) NOT NULL,
    entreprise varchar(200),
    email varchar(200) NOT NULL UNIQUE,
    telephone varchar(200),
    actif BOOLEAN NOT NULL DEFAULT True,
    listeCommandes INTEGER[],
    notes TEXT,
    CONSTRAINT pk_clients PRIMARY KEY (id),
    CONSTRAINT clientEmailValide CHECK (email ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'));


/*Création de la table commandes*/
CREATE TABLE commandes
    (id SERIAL,
    label varchar(200),
    tjmHT DECIMAL(10,2) NOT NULL,
    dureeJours DECIMAL NOT NULL,
    TVA DECIMAL(10,2) NOT NULL,
    statut varchar(200) NOT NULL,
    typeCommande varchar(200) NOT NULL,
    notes TEXT,
    idClient INTEGER NOT NULL,
    CONSTRAINT pk_commandes PRIMARY KEY (id),
    CONSTRAINT dureeNonNulle CHECK (dureeJours>0),
    CONSTRAINT TVAValide CHECK (TVA>0 AND TVA<100),
    CONSTRAINT fk_commandes_clients FOREIGN KEY(idClient) REFERENCES clients(id));


/*Création de la table utilisateurs*/
CREATE TABLE utilisateurs
    (id SERIAL,
    login varchar(200) NOT NULL,
    motDePasse varchar(200) NOT NULL,
    email varchar(200) UNIQUE,
    CONSTRAINT pk_utilisateurs PRIMARY KEY (id),
    CONSTRAINT utilisateurEmailValide CHECK (email ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'));
    


