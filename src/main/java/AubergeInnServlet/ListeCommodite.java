package AubergeInnServlet;

import AubergeInn.GestionAubergeInn;
import AubergeInn.IFT287Exception;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "ListeCommodite", value = "/ListeCommodite")
public class ListeCommodite extends HttpServlet
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
        else if (request.getParameter("ajouter") != null)
            traiterAjouter(request, response);
        else if (request.getParameter("inscrire") != null)
            traiterInscrire(request, response);
        else
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listeCommodite.jsp");
            dispatcher.forward(request, response);
        }

    }

    private void traiterAjouter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Servlet ListeCommodite : GET - Ajouter");
        // transfert de la requète à la page JSP pour affichage
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ajouterCommodite.jsp");
        dispatcher.forward(request, response);
    }

    private void traiterInscrire(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            // lecture des paramètres du formulaire de ajouterCommodite.jsp
            String description = request.getParameter("description");
            String prix = request.getParameter("prix");

            if (description == null || description.equals(""))
                throw new IFT287Exception("Vous devez entrer une description!");
            if (prix == null || prix.equals(""))
                throw new IFT287Exception("Vous devez entrer un prix!");


            request.setAttribute("description", description);
            request.setAttribute("prix", prix);

            float prixFloat = AubergeHelper.ConvertirFloat(prix, "Le prix");

            GestionAubergeInn aubergeUpdate = (GestionAubergeInn) request.getSession().getAttribute("aubergeUpdate");
            synchronized (aubergeUpdate)
            {
                aubergeUpdate.getGestionCommodite().ajouterCommodite(description, prixFloat);
            }

            System.out.println("Servlet ListeCommodite : POST dispatch vers listeCommodite.jsp");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listeCommodite.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.getMessage());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ajouterCommodite.jsp");
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
