package AubergeInnServlet;

import AubergeInn.GestionAubergeInn;
import AubergeInn.IFT287Exception;
import AubergeInn.Securite;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Servlet qui gère la connexion d'un utilisateur au système de gestion de
 * AubergeInn
 *
 * <pre>
 * Vincent Ducharme
 * Université de Sherbrooke
 * Version 1.0 - 11 novembre 2018
 * IFT287 - Exploitation de BD relationnelles et OO
 *
 * Frédéric Bergeron
 * Université de Sherbrooke
 * Version 2.0 - 14 novembre 2019
 * IFT287 - Exploitation de BD relationnelles et OO
 * </pre>
 */

public class Accueil extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Servlet Accueil : POST");
        try
        {
            if (!AubergeHelper.peutProcederLogin(getServletContext(), request, response))
            {
                System.out.println("Servlet Accueil : POST ne peut pas procèder.");
                // Le dispatch sera fait par BiblioHelper.peutProceder
                return;
            }

            HttpSession session = request.getSession();

            // Si c'est la première fois qu'on essaie de se logguer, ou
            // d'inscrire quelqu'un
            if (!AubergeHelper.gestionnairesCrees(session))
            {
                System.out.println("Servlet Accueil : POST Création des gestionnaires");
                AubergeHelper.creerGestionnaire(getServletContext(), session);
            }

            if (request.getParameter("connecter") != null)
            {
                System.out.println("Servlet Accueil : POST - Connecter");
                try
                {
                    // lecture des paramètres du formulaire login.jsp
                    String userId = request.getParameter("userId");
                    String motDePasse = request.getParameter("motDePasse");


                    if (userId == null || userId.equals(""))
                        throw new IFT287Exception("Le nom d'utilisateur ne peut pas être nul!");
                    if (motDePasse == null || motDePasse.equals(""))
                        throw new IFT287Exception("Le mot de passe ne peut pas être nul!");

                    byte[] sha = Securite.getSHA(motDePasse);
                    String motDePasseSHA = Securite.toHexString(sha);

                    request.setAttribute("userId", userId);
                    request.setAttribute("motDePasseSHA", motDePasseSHA);

                    if (AubergeHelper.getAubergeInterro(session).getGestionClient().informationsConnexionValide(userId,
                            motDePasseSHA))
                    {
                        session.setAttribute("userID", userId);
                        if(AubergeHelper.getAubergeInterro(session).getGestionClient().utilisateurEstAdministrateur(userId))
                            session.setAttribute("admin", true);
                        session.setAttribute("etat", AubergeInnConstantes.CONNECTE);

                        System.out.println("Servlet Accueil : POST dispatch vers accueil.jsp");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
                        dispatcher.forward(request, response);
                    }
                    else
                    {
                        throw new IFT287Exception("Les informations de connexion sont erronées.");
                    }
                }
                catch (Exception e)
                {
                    List<String> listeMessageErreur = new LinkedList<String>();
                    listeMessageErreur.add(e.getMessage());
                    request.setAttribute("listeMessageErreur", listeMessageErreur);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
                    dispatcher.forward(request, response);
                    // pour déboggage seulement : afficher tout le contenu de l'exception
                    e.printStackTrace();
                }
            }
            else if (request.getParameter("inscrire") != null)
            {
                System.out.println("Servlet Accueil : POST - Inscrire");
                try
                {
                    // lecture des paramètres du formulaire de creerCompte.jsp
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

                    String accesS = request.getParameter("acces");
                    int acces = 1;
                    if (accesS != null)
                        acces = AubergeHelper.ConvertirInt(accesS, "Le niveau d'accés");

                    GestionAubergeInn aubergeUpdate = AubergeHelper.getAubergeUpdate(session);
                    synchronized (aubergeUpdate)
                    {
                        aubergeUpdate.getGestionClient().ajouterClient(userId, motDePasseSHA, acces, nom, prenom, ageInt);
                    }

                    // S'il y a déjà un userID dans la session, c'est parce
                    // qu'on est admin et qu'on inscrit un nouveau client
                    if (session.getAttribute("userID") == null)
                    {
                        session.setAttribute("userID", userId);
                        if(acces == 0)
                            session.setAttribute("admin", acces == 0);
                        session.setAttribute("etat", AubergeInnConstantes.CONNECTE);

                        System.out.println("Servlet Accueil : POST dispatch vers accueil.jsp");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
                        dispatcher.forward(request, response);
                    }
                    else if (request.getParameter("gestionClient") != null)
                    {
                        // Vers gestionCLient?
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listeClient.jsp");
                        dispatcher.forward(request, response);
                    }
                }
                catch (Exception e)
                {
                    List<String> listeMessageErreur = new LinkedList<String>();
                    listeMessageErreur.add(e.getMessage());
                    request.setAttribute("listeMessageErreur", listeMessageErreur);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/creerCompte.jsp");
                    dispatcher.forward(request, response);
                    // pour déboggage seulement : afficher tout le contenu de l'exception
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.getMessage());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
            dispatcher.forward(request, response);
            // pour déboggage seulement : afficher tout le contenu de l'exception
            e.printStackTrace();
        }
    }

    // Dans les formulaires, on utilise la méthode POST
    // donc, si le servlet est appelé avec la méthode GET
    // c'est que l'adresse a été demandé par l'utilisateur.
    // On procède si la connexion est actives seulement, sinon
    // on retourne au login
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Servlet Accueil : GET");
        if (AubergeHelper.peutProceder(getServletContext(), request, response))
        {
            System.out.println("Servlet Accueil : GET dispatch vers accueil.jsp");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
            dispatcher.forward(request, response);
        }
    }

} // class
