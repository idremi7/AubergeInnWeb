<%@ page import="java.util.*,java.text.*,AubergeInnServlet.*,AubergeInn.*"
		 contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="AubergeInn.tuples.TupleClient" %>
<%@ page import="AubergeInn.tuples.TupleReserveChambre" %>
<!DOCTYPE html>
<html>
<head>
	<title>IFT287 - Système de gestion de d'AubergeInn</title>
	<meta name="author" content="Rémi Létourneau">
	<meta name="author" content="Pierre-Daniel Godfrey">
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
	<link rel="stylesheet" href="css/main.css">

</head>
<body>
<jsp:include page="/WEB-INF/navigation.jsp" />
<div class="container">
	<h1 class="text-center">Gestionnaire des Clients</h1>
	<%
		if (session.getAttribute("admin") != null)
		{
	%>
	<h3 class="text-center">Afficher un client</h3>
	<div class="col-8 offset-2">
		<table class="table">
			<thead class="aubergineTheme">
			<%-- titre des colonnes --%>
			<tr>
				<th scope="col">Utilisateur</th>
				<th scope="col">Nom</th>
				<th scope="col">Prénom</th>
				<th scope="col">Age</th>
			</tr>
			</thead>
			<tbody>
			<%
				String idClient = (String) session.getAttribute("idClient");
				if(idClient == null){
					idClient = (String) request.getAttribute("idClient");
					if(idClient == null){
						idClient = "";
					}
				}
				TupleClient client = AubergeHelper.getAubergeInterro(session).getGestionClient().getClient(Integer.parseInt(idClient));
			%>
			<tr>
				<td class="topValign"><%= (client.getUtilisateur())%></td>
				<td class="topValign"><%=client.getNom()%></td>
				<td class="topValign"><%=client.getPrenom()%></td>
				<td class="topValign"><%=client.getAge()%></td>
			<tr>
			<tr>
				<td></td>
				<td colspan="2">
					<%
						GestionAubergeInn a = AubergeHelper.getAubergeInterro(session);
						List<TupleReserveChambre> reservations = a.getGestionReservation().listerToutesReservationClient(client.getUtilisateur());
						if (reservations.size() == 0)
						{
					%>
					Aucune réservation
					<%
					}
					else
					{
					%>
					<table class="table">
						<thead class="aubergineTheme">
						<tr>
							<th scope="col"># réservation</th>
							<th scope="col">IdChambre</th>
							<th scope="col">Date de début</th>
							<th scope="col">Date de fin</th>
							<th scope="col">prix Total</th>
						</tr>
						</thead>
						<tbody>
						<%
							for (TupleReserveChambre r : reservations)
							{
						%>
						<tr>
							<th scope="row"><%=r.getIdReservation()%></th>
							<td><%=r.getIdChambre()%></td>
							<td><%=r.getDateDebut().toString()%></td>
							<td><%=r.getDateFin().toString()%></td>
							<td><%=r.getPrixTotal()%></td>
						</tr>
						<%
							} // end for chaque reservation
						%>
						</tbody>
					</table>
					<%
							} // end else reservation
					%>
				</td>
			</tr>
			</tbody>
		</table>
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
