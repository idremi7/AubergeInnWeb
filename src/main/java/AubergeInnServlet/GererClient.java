package AubergeInnServlet;

import AubergeInn.GestionAubergeInn;
import AubergeInn.IFT287Exception;
import AubergeInn.Securite;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "GererClient", value = "/GererClient")
public class GererClient extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            // verification de l'état de la session
            HttpSession session = request.getSession();
            Integer etat = (Integer) session.getAttribute("etat");

            if (etat == null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            }
            else if (request.getParameter("afficher") != null)
            {
                System.out.println("Servlet GererClient : GET - afficher");

                // lecture des paramètres du formulaire ListeClient.jsp
                String idClientParam = request.getParameter("clientSelectionne");
                request.setAttribute("idClient", idClientParam);

                // conversion du parametre idClient en entier
                int idClient = -1; // inialisation requise par compilateur Java

                session.setAttribute("idClient", null);
                session.setAttribute("etat", new Integer(AubergeInnConstantes.CONNECTE));

                try
                {
                    idClient = Integer.parseInt(idClientParam);
                    // enregistrer dans la session le paramètre idMembre
                    // cette valeur sera utilisée dans listePretMembre.jsp
                    session.setAttribute("idClient", idClientParam);
                }
                catch (NumberFormatException e)
                {
                    throw new IFT287Exception("Format de no Client " + idClientParam + " incorrect.");
                }

                // vérifier existence du membre
                GestionAubergeInn aubergeInterrogation = (GestionAubergeInn)session.getAttribute("aubergeInterrogation");
                if (!aubergeInterrogation.getGestionClient().existe(idClient))
                    throw new IFT287Exception("Client " + idClient + " inexistant.");

                // transfert de la requète à la page JSP pour affichage
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/afficherClient.jsp");
                dispatcher.forward(request, response);
                session.setAttribute("etat", new Integer(AubergeInnConstantes.CLIENT_SELECTIONNE));
            }
            else if (request.getParameter("ajouter") != null)
            {
                // transfert de la requète à la page JSP pour affichage
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ajouterClient.jsp");
                dispatcher.forward(request, response);
            }
            else if (request.getParameter("supprimer") != null)
            {
                traiterSupprimer(request, response, session);
            }
            else if (request.getParameter("inscrire") != null)
            {
                System.out.println("Servlet GererClient : POST - Inscrire");
                try
                {
                    // lecture des paramètres du formulaire de ajouterClient.jsp
                    String userId = request.getParameter("userId");
                    String motDePasse = request.getParameter("motDePasse");

                    String nom = request.getParameter("nom");
                    String prenom = request.getParameter("prenom");
                    String age = request.getParameter("age");

                    if (userId == null || userId.equals(""))
                        throw new IFT287Exception("Vous devez entrer un nom d'utilisateur!");
                    if (motDePasse == null || motDePasse.equals(""))
                        throw new IFT287Exception("Vous devez entrer un mot de passe!");
                    if (nom == null || nom.equals(""))
                        throw new IFT287Exception("Vous devez entrer un nom!");
                    if (prenom == null || prenom.equals(""))
                        throw new IFT287Exception("Vous devez entrer un prenom!");
                    if (age == null || age.equals(""))
                        throw new IFT287Exception("Vous devez entrer un age!");

                    byte[] sha = Securite.getSHA(motDePasse);
                    String motDePasseSHA = Securite.toHexString(sha);

                    request.setAttribute("userId", userId);
                    request.setAttribute("motDePasseSHA", motDePasseSHA);
                    request.setAttribute("nom", nom);
                    request.setAttribute("prenom", prenom);
                    request.setAttribute("age", age);

                    int ageInt = AubergeHelper.ConvertirInt(age, "L'âge");

                    GestionAubergeInn aubergeUpdate = AubergeHelper.getAubergeUpdate(session);
                    synchronized (aubergeUpdate)
                    {
                        aubergeUpdate.getGestionClient().ajouterClient(userId, motDePasseSHA, 1, nom, prenom, ageInt);
                    }

                    System.out.println("Servlet GererClient : POST dispatch vers listeClient.jsp");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listeClient.jsp");
                    dispatcher.forward(request, response);
                }
                catch (Exception e)
                {
                    List<String> listeMessageErreur = new LinkedList<String>();
                    listeMessageErreur.add(e.getMessage());
                    request.setAttribute("listeMessageErreur", listeMessageErreur);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ajouterClient.jsp");
                    dispatcher.forward(request, response);
                    // pour déboggage seulement : afficher tout le contenu de l'exception
                    e.printStackTrace();
                }
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listeClient.jsp");
                dispatcher.forward(request, response);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        }
        catch (IFT287Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listeClient.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void traiterSupprimer(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception
    {
        // lecture des paramètres du formulaire ListeClient.jsp
        String idClientParam = request.getParameter("clientSelectionne");
        request.setAttribute("idClient", idClientParam);

        // conversion du parametre idClient en entier
        int idClient = -1; // inialisation requise par compilateur Java

        try
        {
            idClient = Integer.parseInt(idClientParam);
            // enregistrer dans la session le paramètre idMembre
            // cette valeur sera utilisée dans listePretMembre.jsp
            session.setAttribute("idClient", idClientParam);
        }
        catch (NumberFormatException e)
        {
            throw new IFT287Exception("Format de no Client " + idClientParam + " incorrect.");
        }

        // vérifier existence du membre
        GestionAubergeInn aubergeInterrogation = (GestionAubergeInn) session.getAttribute("aubergeInterrogation");
        if (!aubergeInterrogation.getGestionClient().existe(idClient))
            throw new IFT287Exception("Client " + idClient + " inexistant.");

        GestionAubergeInn aubergeUpdate = AubergeHelper.getAubergeUpdate(session);
        synchronized (aubergeUpdate)
        {
            aubergeUpdate.getGestionClient().supprimerClient(idClient);
        }

        // transfert de la requète à la page JSP pour affichage
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listeClient.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
}
