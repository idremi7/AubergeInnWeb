package AubergeInn.tuples;

public class TupleClient
{
    private int idClient;
    private String utilisateur;
    private String motDePasse;
    private String nom;
    private String prenom;
    private int age;

    public TupleClient()
    {
    }

    public TupleClient(int idClient, String nom, String prenom, int age)
    {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }

    public TupleClient(String utilisateur, String motDePasse, String nom, String prenom, int age)
    {
        this.utilisateur = utilisateur;
        this.motDePasse = motDePasse;
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

    public String getUtilisateur()
    {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur)
    {
        this.utilisateur = utilisateur;
    }

    public String getMotDePasse()
    {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse)
    {
        this.motDePasse = motDePasse;
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
