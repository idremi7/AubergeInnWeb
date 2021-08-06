package AubergeInnServlet;

import AubergeInn.GestionAubergeInn;
import AubergeInn.IFT287Exception;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "Reservation", value = "/Reservation")
public class Reservation extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(request.getParameter("allerReserver") != null){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/reserver.jsp");
            dispatcher.forward(request, response);
        }else if(request.getParameter("reserver") != null){
            traiterInscrire(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }

    private void traiterInscrire(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Servlet Reservation : POST - reserver");
        try
        {
            // lecture des paramètres du formulaire de ajouterChambre.jsp
            String client = request.getParameter("client");
            String chambre = request.getParameter("chambre");
            String dateDebutStr = request.getParameter("dateDebut");
            String dateFinStr = request.getParameter("dateFin");

            if (client == null || client.equals(""))
                throw new IFT287Exception("Vous devez entrer un client!");
            if (chambre == null || chambre.equals(""))
                throw new IFT287Exception("Vous devez entrer une chambre!");
            if (dateDebutStr == null || dateDebutStr.equals(""))
                throw new IFT287Exception("Vous devez entrer une dateDebut!");
            if (dateFinStr == null || dateFinStr.equals(""))
                throw new IFT287Exception("Vous devez entrer une dateFin!");

            request.setAttribute("client", client);
            request.setAttribute("chambre", chambre);
            request.setAttribute("dateDebut", dateDebutStr);
            request.setAttribute("dateFin", dateFinStr);

            int clientInt = AubergeHelper.ConvertirInt(client, "idClient");
            int chambreInt = AubergeHelper.ConvertirInt(chambre, "idChambre");
            Date dateDebut = AubergeHelper.ConvertirDate(dateDebutStr, "Date de début");
            Date dateFin = AubergeHelper.ConvertirDate(dateFinStr, "Date de fin");

            GestionAubergeInn aubergeUpdate = (GestionAubergeInn) request.getSession().getAttribute("aubergeUpdate");
            synchronized (aubergeUpdate)
            {
                aubergeUpdate.getGestionReservation().reserver(clientInt, chambreInt, dateDebut, dateFin);
            }

            System.out.println("Servlet Reservation : POST dispatch vers accueil.jsp");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.getMessage());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/reserver.jsp");
            dispatcher.forward(request, response);
            // pour déboggage seulement : afficher tout le contenu de l'exception
            e.printStackTrace();
        }
    }
}
