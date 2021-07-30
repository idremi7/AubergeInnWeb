package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleClient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableClients
{
    private final PreparedStatement stmtExiste;
    private final PreparedStatement stmtInsert;
    private final PreparedStatement stmtUpdate;
    private final PreparedStatement stmtDelete;
    private final PreparedStatement stmtListeTousClients;
    private final PreparedStatement stmtListeUtilisateur;
    private final PreparedStatement stmtExisteUtilisateur;

    private final Connexion cx;

    public TableClients(Connexion cx) throws SQLException
    {
        this.cx = cx;
        stmtListeUtilisateur = cx.getConnection().prepareStatement(
                "SELECT utilisateur, motDePasse, acces, nom, prenom, age FROM client WHERE acces = ?");
        stmtExisteUtilisateur = cx.getConnection().prepareStatement(
                "SELECT utilisateur, motDePasse, acces, nom, prenom, age FROM client WHERE utilisateur = ?");
        this.stmtExiste = cx.getConnection().prepareStatement("select * from client where idclient = ?");
        this.stmtInsert = cx.getConnection()
                .prepareStatement("insert into client (utilisateur, motdepasse, acces, nom, prenom, age) "
                        + "values (?,?,?,?,?,?)");
        this.stmtUpdate = cx.getConnection()
                .prepareStatement("update client set nom = ?, prenom = ?, age = ? " + "where idclient = ?");
        this.stmtDelete = cx.getConnection().prepareStatement("delete from client where idclient = ?");
        this.stmtListeTousClients = cx.getConnection().prepareStatement("select idclient, nom, prenom, age from client");
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /**
     * Vérifie si un client existe.
     */
    public boolean existe(int idClient) throws SQLException
    {
        stmtExiste.setInt(1, idClient);
        ResultSet rset = stmtExiste.executeQuery();
        boolean clientExiste = rset.next();
        rset.close();
        return clientExiste;
    }

    /**
     * Verifie si un client existe avec son nom d'utilisateur.
     */
    public boolean existe(String utilisateur) throws SQLException
    {
        stmtExisteUtilisateur.setString(1, utilisateur);
        ResultSet rset = stmtExisteUtilisateur.executeQuery();
        boolean clientExiste = rset.next();
        rset.close();
        return clientExiste;
    }

    /**
     * Lecture d'un Client.
     */
    public TupleClient getClient(int idClient) throws SQLException
    {
        stmtExiste.setInt(1, idClient);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next())
        {
            TupleClient tupleClient = new TupleClient();
            tupleClient.setIdClient(idClient);
            tupleClient.setNom(rset.getString(2));
            tupleClient.setPrenom(rset.getString(3));
            tupleClient.setAge(rset.getInt(4));

            rset.close();
            return tupleClient;
        } else
            return null;
    }

    /**
     * Lecture d'un client à partir du nom d'utilisateur.
     */
    public TupleClient getClient(String utilisateur) throws SQLException
    {
        stmtExisteUtilisateur.setString(1, utilisateur);
        ResultSet rset = stmtExisteUtilisateur.executeQuery();
        TupleClient tupleClient = null;
        if (rset.next())
        {
            String motDePasse = rset.getString(2);
            int acces = rset.getInt(3);
            String nom = rset.getString(4);
            String prenom = rset.getString(5);
            int age = rset.getInt(6);

            tupleClient = new TupleClient(utilisateur, motDePasse, acces, nom, prenom, age);
            rset.close();
        }
        return tupleClient;
    }

    /**
     * Ajout d'un nouveau Client dans la base de données.
     */
    public void ajouter(String utilisateur, String motDePasse, int acces, String nom, String prenom, int age) throws SQLException
    {
        /* Ajout d'un client-canard. */
        stmtInsert.setString(1, utilisateur);
        stmtInsert.setString(2, motDePasse);
        stmtInsert.setInt(3, acces);
        stmtInsert.setString(4, nom);
        stmtInsert.setString(5, prenom);
        stmtInsert.setInt(6, age);
        stmtInsert.executeUpdate();
    }

    /**
     * Suppression  d'un client.
     */
    public int supprimer(int idClient) throws SQLException
    {
        /* Suppression d'un client-canard. */
        stmtDelete.setInt(1, idClient);
        return stmtDelete.executeUpdate();
    }

    public List<TupleClient> getListeClients(boolean avecAdmin) throws SQLException
    {
        List<TupleClient> clients = new ArrayList<TupleClient>();

        stmtListeUtilisateur.setInt(1, 1);
        ResultSet rset = stmtListeUtilisateur.executeQuery();
        while (rset.next())
        {
            String motDePasseSHA = rset.getString(2);
            int acces = rset.getInt(3);
            String nom = rset.getString(4);
            String prenom = rset.getString(5);
            int age = rset.getInt(6);

            TupleClient tupleClient = new TupleClient(rset.getString(1), motDePasseSHA, acces, nom, prenom, age);
            clients.add(tupleClient);
        }
        if (avecAdmin)
        {
            stmtListeUtilisateur.setInt(1, 0);
            rset = stmtListeUtilisateur.executeQuery();
            while (rset.next())
            {
                String motDePasseSHA = rset.getString(2);
                int acces = rset.getInt(3);
                String nom = rset.getString(4);
                String prenom = rset.getString(5);
                int age = rset.getInt(6);

                TupleClient tupleClient = new TupleClient(rset.getString(1), motDePasseSHA, acces, nom, prenom, age);
                clients.add(tupleClient);
            }
        }
        rset.close();
        return clients;
    }
}
