<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
 <head>
        <title>Menu deroulant</title>
        <!--<link rel = "stylesheet" href = "css/bootstrap.css">-->
        <!-- Latest compiled and minified CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    </head>
    <body>

    <nav class="navbar navbar-expand-md navbar-light bg-light">
        <a class="navbar-brand" href="<c:url value="/"/>">Application CRM</a>
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="nav-link" href="<c:url value="/"/>">Accueil </a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Opérations sur les clients</a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="<c:url value="/AjouterClient"/>">Ajouter un client</a>
                    <a class="dropdown-item" href="<c:url value="/trouverClient"/>">Trouver un client</a>
                   

                    <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="<c:url value="/ListeClient"/>">Afficher la liste des clients</a>
                </div>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Opérations sur les commandes</a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="<c:url value="/AjouterCommande"/>">Ajouter une commande</a>
                    <a class="dropdown-item" href="#">Trouver une commande</a>
                    

                    <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="<c:url value="/ListeCommande"/>">Afficher la liste des commandes</a>
                </div>
            </li>
            
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Opérations sur les utilisateurs</a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="<c:url value="/AjouterUtilisateur"/>">Ajouter un utilisateur</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="<c:url value="/ListeUtilisateur"/>">Afficher la liste des utilisateurs</a>
                </div>
            </li>
            
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Paramètres</a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="#">Changer le mot de passe</a>
                </div>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="#">Déconnection</a>
            </li>
        </ul>
    </nav>


        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script> 
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

    </body>
</html>