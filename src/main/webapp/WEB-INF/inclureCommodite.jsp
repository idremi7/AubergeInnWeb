<%@ page import="AubergeInn.tuples.TupleChambre" %>
<%@ page import="AubergeInnServlet.AubergeHelper" %>
<%@ page import="AubergeInn.GestionAubergeInn" %>
<%@ page import="AubergeInn.tuples.TupleCommodite" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Remi
  Date: 2021-08-02
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>IFT287 - Système de gestion de d'AubergeInn</title>
    <meta name="description"
          content="Page de gestion des chambre">

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
</head>
<body>
<jsp:include page="/WEB-INF/navigation.jsp" />
<div class="container">
    <h1 class="text-center">Gestionnaire des Chambres</h1>
    <%
        if (session.getAttribute("admin") != null)
        {
    %>
    <h3 class="text-center">Inclure une commoditée</h3>
    <div class="col-8 offset-2">
        <FORM ACTION="ListeChambre" METHOD="GET">
            <%
                String idChambre = (String) session.getAttribute("idChambre");
                if(idChambre == null){
                    idChambre = (String) request.getAttribute("idChambre");
                    if(idChambre == null){
                        idChambre = "";
                    }
                }
            %>
            <div class="form-group">
                <label for="idChambre">IdChambre :</label>
                <input type="number" id="idChambre" name="idChambre" readonly disabled value="<%= idChambre %>">
            </div>
            <div class="form-group">
                <label for="commodite">Choisir une commoditée :</label>
                <select id="commodite" name="idCommodite">
                    <option value="" selected > - </option>
                    <%
                        List<TupleCommodite> commodites = AubergeHelper.getAubergeInterro(session).getGestionCommodite().ListerCommodite();
                        for (TupleCommodite c : commodites)
                        {
                    %>
                    <option value="<%= (c.getIdCommodite())%>"><%= (c.getDescription())%></option>
                    <%
                        } // end for all chambres
                    %>
                </select>
            </div>
            <input class="btn btn-primary" type="SUBMIT" name="inclure" value="Inclure">
        </FORM>
    </div>
    <%
        } // end if admin
    %>
    <br>
    <%-- inclusion d'une autre page pour l'affichage des messages d'erreur--%>
    <jsp:include page="/WEB-INF/messageErreur.jsp" />
    <br>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script
        src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script
        src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>
</html>
