<%@ page import="java.util.*,java.text.*,AubergeInnServlet.*,AubergeInn.*"
		 contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="AubergeInn.tuples.TupleClient" %>
<%@ page import="AubergeInn.tuples.TupleReserveChambre" %>
<!DOCTYPE html>
<html>
<head>
	<title>IFT287 - Système de gestion de d'AubergeInn</title>
	<meta name="author" content="Vincent Ducharme">
	<meta name="description"
		  content="Page d'accueil du système de gestion d'AubergeInn.">

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
	<h1 class="text-center">Système de gestion d'AubergeInn.</h1>

	<%
		if (session.getAttribute("admin") != null)
		{
	%>
	<h3 class="text-center">Réservation</h3>
	<div class="col-8 offset-2">
		<table class="table">
			<thead class="thead-dark">
			<tr>
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
				<td><%=c.getUtilisateur()%></td>
				<td><%=c.getNom()%></td>
				<td><%=c.getPrenom()%></td>
				<td><%=c.getAge()%></td>
			<tr>
			<tr>
				<td></td>
				<td colspan="2">
					<%
						GestionAubergeInn a = AubergeHelper.getAubergeInterro(session);
						List<TupleReserveChambre> reservations = a.getGestionReservation().listerToutesReservationClient(c.getUtilisateur());
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
						<thead class="thead-dark">
						<tr>
							<th scope="col">#</th>
							<th scope="col">id client</th>
							<th scope="col">id chambre</th>
							<th scope="col">Date de début</th>
							<th scope="col">Date de fin</th>
						</tr>
						</thead>
						<tbody>
						<%
							for (TupleReserveChambre r : reservations)
							{
						%>
						<tr>
							<th scope="row"><%=r.getIdReservation()%></th>
							<td><%=r.getIdClient()%></td>
							<td><%=r.getIdChambre()%></td>
							<td><%=r.getDateDebut().toString()%></td>
							<td><%=r.getDateFin().toString()%></td>
						</tr>
						<%
							} // end for chaque reservation
						%>
						</tbody>
					</table>
					<%
							} // end else reservation
						} // end for all clients
					%>
				</td>
			</tr>
			</tbody>
		</table>
	</div>
	<%
	} // end if admin
	else
	{
		GestionAubergeInn g = AubergeHelper.getAubergeInterro(session);
		List<TupleReserveChambre> reservations = g.getGestionReservation().listerToutesReservationClient((String)session.getAttribute("userID"));
	%>
	<h3 class="text-center">Mes Reservation</h3>
	<div class="col-8 offset-2">
		<table class="table">
			<thead class="thead-dark">
			<tr>
				<th scope="col">#</th>
				<th scope="col">idChambre</th>
				<th scope="col">dateDebut</th>
				<th scope="col">dateFin</th>
			</tr>
			</thead>
			<tbody>
			<%
				for(TupleReserveChambre r : reservations)
				{
			%>
			<tr>
				<td><%= r.getIdReservation() %></td>
				<td><%= r.getIdChambre() %></td>
				<td><%= r.getDateDebut().toString() %></td>
				<td><%= r.getDateFin().toString() %></td>
			</tr>
			<%
				}
			%>
			</tbody>
		</table>
	</div>
	<%

		}
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
