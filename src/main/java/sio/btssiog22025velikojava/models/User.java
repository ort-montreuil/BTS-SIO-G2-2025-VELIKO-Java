package sio.btssiog22025velikojava.models;

public class User
{
    private String nom;
    private String prenom;
    private String email;
    private boolean statut;

    public User(String nom, String prenom, String email, boolean statut) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.statut = statut;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }
}