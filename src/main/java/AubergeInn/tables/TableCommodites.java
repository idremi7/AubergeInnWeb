package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleChambre;
import AubergeInn.tuples.TupleCommodite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableCommodites
{
    private final PreparedStatement stmtExiste;
    private final PreparedStatement stmtCommodite;
    private final PreparedStatement stmtInsert;
    private final PreparedStatement stmtInsert2;
    private final PreparedStatement stmtUpdate;
    private final PreparedStatement stmtDelete;

    private final Connexion cx;

    public TableCommodites(Connexion cx) throws SQLException
    {
        this.cx = cx;
        this.stmtExiste = cx.getConnection()
                .prepareStatement("select idcommodite, description, prix from commodite where idcommodite = ?");
        this.stmtInsert = cx.getConnection()
                .prepareStatement("insert into commodite (idcommodite, description, prix) values (?,?,?)");
        this.stmtInsert2 = cx.getConnection()
                .prepareStatement("insert into commodite (description, prix) values (?,?)");
        this.stmtUpdate = cx.getConnection()
                .prepareStatement("update commodite set description = ?, prix = ? where idcommodite = ?");
        this.stmtDelete = cx.getConnection().prepareStatement("delete from commodite where idcommodite = ?");

        this.stmtCommodite = cx.getConnection().prepareStatement("select idcommodite, description, prix from commodite");
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /**
     * Vérifie si une commodité existe.
     */
    public boolean existe(int idCommodite) throws SQLException
    {
        stmtExiste.setInt(1, idCommodite);
        ResultSet rset = stmtExiste.executeQuery();
        boolean commoditeExiste = rset.next();
        rset.close();
        return commoditeExiste;
    }

    /**
     * Ajout d'une nouvelle Commodite dans la base de données.
     */
    public void ajouter(int idCommodite, String description, float prix) throws SQLException
    {
        /* Ajout d'une commodite-canard. */
        stmtInsert.setInt(1, idCommodite);
        stmtInsert.setString(2, description);
        stmtInsert.setFloat(3, prix);
        stmtInsert.executeUpdate();
    }

    /**
     * Ajout d'une nouvelle Commodite dans la base de données.
     */
    public void ajouter(String description, float prix) throws SQLException
    {
        /* Ajout d'une commodite. */
        stmtInsert2.setString(1, description);
        stmtInsert2.setFloat(2, prix);
        stmtInsert2.executeUpdate();
    }

    /**
     * Suppression  d'une commodite.
     */
    public int supprimer(int idCommodite) throws SQLException
    {
        /* Suppression d'une commodite. */
        stmtDelete.setInt(1, idCommodite);
        return stmtDelete.executeUpdate();
    }

    /**
     * Lecture d'une commodite.
     */
    public TupleCommodite getCommodite(int idCommodite) throws SQLException
    {
        stmtExiste.setInt(1, idCommodite);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next())
        {
            TupleCommodite tupleCommodite = new TupleCommodite();
            tupleCommodite.setIdCommodite(idCommodite);
            tupleCommodite.setDescription(rset.getString(2));
            tupleCommodite.setPrix(rset.getFloat(3));

            rset.close();
            return tupleCommodite;
        } else
            return null;
    }

    /**
     * Trouve toutes les chambres
     */
    public List<TupleCommodite> listeCommodite() throws SQLException
    {
        ResultSet rset = stmtCommodite.executeQuery();
        List<TupleCommodite> commoditeList = new ArrayList<>();
        while (rset.next())
        {
            TupleCommodite commodite = new TupleCommodite(
                    rset.getInt(1),      //id
                    rset.getString(2),   //Description
                    rset.getFloat(3));   //Prix

            commoditeList.add(commodite);
        }
        rset.close();
        return commoditeList;
    }
}
