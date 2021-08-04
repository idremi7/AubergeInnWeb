<%@ page import="java.util.*,java.text.*,AubergeInnServlet.*,AubergeInn.*"
		 contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="AubergeInn.tuples.TupleClient" %>
<!DOCTYPE html>
<html>
<head>
	<title>IFT287 - Système de gestion de d'AubergeInn</title>
	<meta name="author" content="Vincent Ducharme">
	<meta name="description"
		  content="Page de gestion des clients">

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
<div class="container">
	<jsp:include page="/WEB-INF/navigation.jsp" />
	<h1 class="text-center">Gestionnaire des Clients</h1>

	<%
		if (session.getAttribute("admin") != null)
		{
	%>
	<h3 class="text-center">Clients</h3>
	<div class="col-8 offset-2">
		<FORM ACTION="GererClient" METHOD="GET">
		<table class="table">
			<thead style="color:white; background-color: #800080">
			<%-- titre des colonnes --%>
			<tr>
				<th scope="col">Sélection<br></th>
				<th scope="col">Utilisateur</th>
				<th scope="col">Nom</th>
				<th scope="col">Prénom</th>
				<th scope="col">Age</th>
			</tr>
			</thead>
			<tbody>
			<%
				List<TupleClient> clients = AubergeHelper.getAubergeInterro(session).getGestionClient().getListeClients(false);
				for (TupleClient c : clients)
				{
			%>
			<tr>
				<td style="vertical-align: top;">
					<INPUT TYPE="RADIO" NAME="clientSelectionne" VALUE="<%= c.getIdClient() %>"><br>
				</td>
				<td style="vertical-align: top;"><%= (c.getUtilisateur())%></td>
				<td style="vertical-align: top;"><%=c.getNom()%></td>
				<td style="vertical-align: top;"><%=c.getPrenom()%></td>
				<td style="vertical-align: top;"><%=c.getAge()%></td>
			<tr>
			<%
				} // end for all clients
			%>
			</tbody>
		</table>
			<input class="btn btn-outline-primary" type="SUBMIT" NAME="afficher" VALUE="Afficher un client">
			<input class="btn btn-outline-primary" type="SUBMIT" NAME="ajouter" VALUE="Ajouter un client">
			<input class="btn btn-outline-danger" type="SUBMIT" NAME="supprimer" VALUE="Supprimer un client">
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
