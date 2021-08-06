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

@WebServlet(name = "ListeChambre", value = "/ListeChambre")
public class ListeChambre extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Integer etat = (Integer) request.getSession().getAttribute("etat");

        if (etat == null)
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }
        else if (request.getParameter("afficher") != null)
            traiterAfficherChambre(request, response);
        else if (request.getParameter("ajouter") != null)
            traiterAjouter(request, response);
        else if (request.getParameter("inscrire") != null)
            traiterInscrire(request, response);
        else if (request.getParameter("inclure") != null){

            System.out.println("Servlet ListeChambre : POST - inclure");
            try
            {
                // lecture des paramètres du formulaire de listeChambre.jsp
                String idCommodite = request.getParameter("idCommodite");
                String idChambreParam = request.getParameter("chambreSelectionne");

                if (idCommodite == null || idCommodite.equals(""))
                    throw new IFT287Exception("Vous devez Sélectionner une commoditée!");
                if (idChambreParam == null || idChambreParam.equals(""))
                    throw new IFT287Exception("Vous devez sélectionner une chambre!");

                request.setAttribute("idCommodite", idCommodite);
                request.setAttribute("idChambre", idChambreParam);

                int idCommoditeInt = AubergeHelper.ConvertirInt(idCommodite, "L'id de la commodite");
                int idChambre = AubergeHelper.ConvertirInt(idChambreParam, "L'id de la chambre");

                GestionAubergeInn aubergeUpdate = (GestionAubergeInn) request.getSession().getAttribute("aubergeUpdate");
                synchronized (aubergeUpdate)
                {
                    aubergeUpdate.getGestionCommodite().InclureCommodite(idChambre, idCommoditeInt);
                }

                System.out.println("Servlet ListeChambre : POST dispatch vers listeChambre.jsp");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listeChambre.jsp");
                dispatcher.forward(request, response);
            } catch (Exception e)
            {
                List<String> listeMessageErreur = new LinkedList<String>();
                listeMessageErreur.add(e.getMessage());
                request.setAttribute("listeMessageErreur", listeMessageErreur);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listeChambre.jsp");
                dispatcher.forward(request, response);
                // pour déboggage seulement : afficher tout le contenu de l'exception
                e.printStackTrace();
            }
        }
        else if (request.getParameter("enlever") != null){

            System.out.println("Servlet ListeChambre : POST - enlever");
            try
            {
                // lecture des paramètres du formulaire de listeChambre.jsp
                String idCommodite = request.getParameter("idCommodite");
                String idChambreParam = request.getParameter("chambreSelectionne");

                if (idCommodite == null || idCommodite.equals(""))
                    throw new IFT287Exception("Vous devez sélectionner une commoditée!");
                if (idChambreParam == null || idChambreParam.equals(""))
                    throw new IFT287Exception("Vous devez sélectionner une chambre!");

                request.setAttribute("idCommodite", idCommodite);
                request.setAttribute("idChambre", idChambreParam);

                int idCommoditeInt = AubergeHelper.ConvertirInt(idCommodite, "L'id de la commodite");
                int idChambre = AubergeHelper.ConvertirInt(idChambreParam, "L'id de la chambre");

                GestionAubergeInn aubergeUpdate = (GestionAubergeInn) request.getSession().getAttribute("aubergeUpdate");
                synchronized (aubergeUpdate)
                {
                    aubergeUpdate.getGestionCommodite().enleverCommodite(idChambre, idCommoditeInt);
                }

                System.out.println("Servlet ListeChambre : POST dispatch vers listeChambre.jsp");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listeChambre.jsp");
                dispatcher.forward(request, response);
            } catch (Exception e)
            {
                List<String> listeMessageErreur = new LinkedList<String>();
                listeMessageErreur.add(e.getMessage());
                request.setAttribute("listeMessageErreur", listeMessageErreur);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listeChambre.jsp");
                dispatcher.forward(request, response);
                // pour déboggage seulement : afficher tout le contenu de l'exception
                e.printStackTrace();
            }
        }
        else if(request.getParameter("supprimer") != null)
            traiterSupprimer(request, response);
        else
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listeChambre.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void traiterInscrire(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Servlet ListeChambre : POST - Inscrire");
        try
        {
            // lecture des paramètres du formulaire de ajouterChambre.jsp
            String nom = request.getParameter("nom");
            String type = request.getParameter("type");
            String prixBase = request.getParameter("prixBase");

            if (nom == null || nom.equals(""))
                throw new IFT287Exception("Vous devez entrer un nom de chambre!");
            if (type == null || type.equals(""))
                throw new IFT287Exception("Vous devez entrer un type!");
            if (nom == null || nom.equals(""))
                throw new IFT287Exception("Vous devez entrer un nom!");
            if (prixBase == null || prixBase.equals(""))
                throw new IFT287Exception("Vous devez entrer un prixBase!");

            request.setAttribute("nom", nom);
            request.setAttribute("type", type);
            request.setAttribute("prixBase", prixBase);

            float prixBaseFloat = AubergeHelper.ConvertirFloat(prixBase, "Le prix de base");

            GestionAubergeInn aubergeUpdate = (GestionAubergeInn) request.getSession().getAttribute("aubergeUpdate");
            synchronized (aubergeUpdate)
            {
                aubergeUpdate.getGestionChambre().ajouterChambre(nom, type, prixBaseFloat);
            }

            System.out.println("Servlet ListeChambre : POST dispatch vers listeChambre.jsp");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listeChambre.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e)
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

    private void traiterAjouter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // transfert de la requète à la page JSP pour affichage
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ajouterChambre.jsp");
        dispatcher.forward(request, response);
    }

    private void traiterAfficherChambre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        System.out.println("Servlet ListeChambre : GET - afficher");
        try
        {
            // lecture des paramètres du formulaire listeChambre.jsp
            String idChambreParam = request.getParameter("chambreSelectionne");
            request.setAttribute("idChambre", idChambreParam);

            session.setAttribute("etat", new Integer(AubergeInnConstantes.CONNECTE));

            if (idChambreParam == null || idChambreParam.equals(""))
                throw new IFT287Exception("Vous devez sélectionner une chambre!");

            // transfert de la requète à la page JSP pour affichage
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/afficherChambre.jsp");
            dispatcher.forward(request, response);
        }
        catch (Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.getMessage());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listeChambre.jsp");
            dispatcher.forward(request, response);
            // pour déboggage seulement : afficher tout le contenu de l'exception
            e.printStackTrace();
        }
    }

    private void traiterSupprimer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Servlet ListeChambre : GET - supprimer");
        try
        {
            // lecture des paramètres du formulaire listeChambre.jsp
            String idChambreParam = request.getParameter("chambreSelectionne");
            request.setAttribute("idChambre", idChambreParam);
            int idChambre = -1;

            if (idChambreParam == null || idChambreParam.equals(""))
                throw new IFT287Exception("Vous devez sélectionner une chambre!");

            try
            {
                idChambre = Integer.parseInt(idChambreParam);
                // enregistrer dans la session le paramètre idMembre
                // cette valeur sera utilisée dans listePretMembre.jsp
                request.getSession().setAttribute("idChambre", idChambreParam);
            } catch (NumberFormatException e)
            {
                throw new IFT287Exception("Format de no Chambre " + idChambreParam + " incorrect.");
            }

            GestionAubergeInn aubergeUpdate = (GestionAubergeInn) request.getSession().getAttribute("aubergeUpdate");
            synchronized (aubergeUpdate)
            {
                aubergeUpdate.getGestionChambre().supprimerChambre(idChambre);
            }

            // transfert de la requète à la page JSP pour affichage
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listeChambre.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.getMessage());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listeChambre.jsp");
            dispatcher.forward(request, response);
            // pour déboggage seulement : afficher tout le contenu de l'exception
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
}
