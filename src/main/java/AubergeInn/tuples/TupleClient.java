package AubergeInn.tuples;

public class TupleClient
{
    private int idClient;
    private String utilisateur;
    private String motDePasseSHA;
    private String nom;
    private String prenom;
    private int age;
    private int acces;

    public TupleClient()
    {
    }

    public TupleClient(String utilisateur, String motDePasse, int acces, String nom, String prenom, int age)
    {
        this.utilisateur = utilisateur;
        this.motDePasseSHA = motDePasse;
        this.acces = acces;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }

    public int getIdClient()
    {
        return idClient;
    }

    public void setIdClient(int idClient)
    {
        this.idClient = idClient;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getPrenom()
    {
        return prenom;
    }

    public void setPrenom(String prenom)
    {
        this.prenom = prenom;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    /**
     *
     * @return le nom d'utilisateur du client
     */
    public String getUtilisateur()
    {
        return utilisateur;
    }

    /**
     *
     * @param utilisateur le nouveau nom d'utilisateur du client
     */
    public void setUtilisateur(String utilisateur)
    {
        this.utilisateur = utilisateur;
    }

    /**
     * @return le SHA-256 du mot de passe du membre
     */
    public String getMotDePasseSHA()
    {
        return motDePasseSHA;
    }

    /**
     * @param motDePasseSHA Le SHA-256 du nouveau mot de passe
     */
    public void setMotDePasseSHA(String motDePasseSHA)
    {
        this.motDePasseSHA = motDePasseSHA;
    }

    /**
     * @return Le niveau d'accès du membre
     */
    public int getNiveauAcces()
    {
        return acces;
    }

    /**
     * @param niveau Le nouveau niveau d'accès du membre
     */
    public void setNiveauAcces(int niveau)
    {
        this.acces = niveau;
    }

    @Override
    public String toString()
    {
        return "TupleClient{" +
                "idClient=" + idClient +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                '}';
    }
}
