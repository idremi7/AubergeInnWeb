package AubergeInnServlet;

import AubergeInn.GestionAubergeInn;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.SQLException;

/**
 * Classe pour gestion des sessions
 * <P>
 * Système de gestion de bibliothèque &copy; 2004 Marc Frappier, Université de
 * Sherbrooke
 */

public class AubergeInnSessionListener implements HttpSessionListener
{
    public void sessionCreated(HttpSessionEvent se)
    {
    }

    public void sessionDestroyed(HttpSessionEvent se)
    {
        System.out.println("Session détruite pour l'utilisateur " + se.getSession().getAttribute("userID"));

        GestionAubergeInn aubergeInterrogation = (GestionAubergeInn)se.getSession().getAttribute("aubergeInterrogation");
        if (aubergeInterrogation != null)
        {
            try
            {
                System.out.println("Fermeture de la connexion d'interrogation...");
                aubergeInterrogation.fermer();
            }
            catch (SQLException e)
            {
                System.out.println("Impossible de fermer la connexion");
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Aucun gestionnaire d'interrogation n'avait encore été créé.");
        }

        GestionAubergeInn aubergeUpdate = (GestionAubergeInn)se.getSession().getAttribute("aubergeUpdate");
        if (aubergeUpdate != null)
        {
            try
            {
                System.out.println("Fermeture de la connexion de mise à jour...");
                aubergeUpdate.fermer();
            }
            catch (SQLException e)
            {
                System.out.println("Impossible de fermer la connexion");
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Aucun gestionnaire de mise à jour n'avait encore été créé.");
        }
    }
}
