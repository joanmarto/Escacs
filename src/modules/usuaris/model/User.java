package modules.usuaris.model;

/**
 * Classe que representa un usuari de l'aplicació.
 * 
 * @author Bernat Galmés Rubert
 */
public class User {
    private long id;
    private String Nom;
    private String mail;

    private long numAccessos;
    
    public User(long id, String Nom, String mail, long numAccessos) {
        this.id = id;
        this.Nom = Nom;
        this.mail = mail;
        this.numAccessos = numAccessos;
    }

    public long getId() {
        return id;
    }

    public String getNom() {
        return Nom;
    }

    public String getMail() {
        return mail;
    }

    public long getNumAccessos() {
        return numAccessos;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
