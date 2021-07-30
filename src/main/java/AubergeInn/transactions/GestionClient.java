package AubergeInn.transactions;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.TableClients;
import AubergeInn.tables.TableReserveChambre;
import AubergeInn.tuples.TupleClient;

import java.sql.SQLException;
import java.util.List;

public class GestionClient
{
    private TableClients client;
    private TableReserveChambre reservation;
    private Connexion cx;

    /**
     * Creation d'une instance
     */
    public GestionClient(TableClients client, TableReserveChambre reservation) throws IFT287Exception
    {
        this.cx = client.getConnexion();
        if (client.getConnexion() != reservation.getConnexion())
            throw new IFT287Exception("Les instances de client et de chambre n'utilisent pas la même connexion au serveur");
        this.client = client;
        this.reservation = reservation;
    }

    public boolean existe(String utilisateur)
            throws SQLException
    {
        return this.client.existe(utilisateur);
    }

    public boolean informationsConnexionValide(String utilisateur, String motDePasseSHA)
            throws SQLException, IFT287Exception
    {
        try
        {
            // Vérifie si le membre existe déja
            if (!client.existe(utilisateur))
                throw new IFT287Exception("Aucun utilisateur n'existe avec ce nom.");


            TupleClient user = client.getClient(utilisateur);
            if(!user.getMotDePasseSHA().equals(motDePasseSHA))
                throw new IFT287Exception("Mauvais mot de passe.");

            // Commit
            cx.commit();

            return true;
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }

    public boolean utilisateurEstAdministrateur(String utilisateur)
            throws SQLException, IFT287Exception
    {
        try
        {
            TupleClient c = client.getClient(utilisateur);
            if(c == null)
                throw new IFT287Exception("L'utilisateur n'existe pas");

            cx.commit();
            return c.getNiveauAcces() == 0;
        }
        catch(Exception e)
        {
            cx.rollback();
            throw e;
        }
    }

    /**
     * Ajout d'un nouveau client dans la base de données. S'il existe déjà, une
     * exception est levée.
     */
    public void ajouterClient(String utilisateur, String motDePasse, int acces, String nom, String prenom, int age)
            throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Vérifie si le livre existe déja
            if (client.existe(utilisateur))
                throw new IFT287Exception("Client existe déjà: " + utilisateur);

            // Ajout du livre dans la table des livres
            client.ajouter(utilisateur, motDePasse, acces, nom, prenom, age);

            // Commit
            cx.commit();
        } catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }

    /**
     * Supprimer un client.
     */
    public void supprimerClient(int idClient) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Validation
            TupleClient tupleClient = client.getClient(idClient);
            if (tupleClient == null)
                throw new IFT287Exception("Client inexistant: " + idClient);

            if (reservation.getReservationClient(idClient) != null)
                throw new IFT287Exception("Client #" + idClient + " a une ou plusieurs réservations ");

            // Suppression du client.
            int nb = client.supprimer(idClient);
            if (nb == 0)
                throw new IFT287Exception("Client " + idClient + " inexistant");

            // Commit
            cx.commit();
        } catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }

    /**
     * Trouve toutes les informations sur un clients de la BD
     */
    public TupleClient getClient(int idClient) throws SQLException
    {

        try
        {
            TupleClient unClient = client.getClient(idClient);
            cx.commit();
            return unClient;
        } catch (Exception e)
        {
            cx.rollback();
            throw e;
        }

    }

    public List<TupleClient> getListeClients(boolean avecAdmin) throws SQLException, IFT287Exception
    {
        try
        {
            List<TupleClient> clients = client.getListeClients(avecAdmin);
            cx.commit();
            return clients;
        }
        catch(SQLException e)
        {
            cx.rollback();
            throw e;
        }
    }
}
