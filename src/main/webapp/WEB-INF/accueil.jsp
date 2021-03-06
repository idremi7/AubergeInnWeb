<%@ page import="java.util.*,java.text.*,AubergeInnServlet.*,AubergeInn.*"
		 contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="AubergeInn.tuples.TupleChambre" %>
<%@ page import="AubergeInn.tuples.TupleReserveChambre" %>
<!DOCTYPE html>
<html>
<head>
	<title>IFT287 - Système de gestion de d'AubergeInn</title>
	<meta name="author" content="Rémi Létourneau">
	<meta name="author" content="Pierre-Daniel Godfrey">
	<meta name="description"
		  content="Page d'accueil du système de gestion d'AubergeInn.">

	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport"
		  content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<link rel="icon" type="image/ico" href="favicon.ico" />
	<!-- Bootstrap CSS -->
	<link rel="stylesheet"
		  href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
		  integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
		  crossorigin="anonymous">

	<link rel="stylesheet" href="css/main.css">
</head>
<body class="bg">
<jsp:include page="/WEB-INF/navigation.jsp" />
<div class="container">
	<h1 class="text-center">Système de gestion d'AubergeInn.</h1>
	<%
		if (session.getAttribute("admin") != null)
		{
	%>
	<div>
		<div class="row">
			<div class="col-6">
				<h3 class="text-center">Chambre libres</h3>
				<table class="table">
					<thead class="aubergineTheme">
					<tr>
						<th scope="col"># de chambre</th>
						<th scope="col">Nom</th>
						<th scope="col">Type</th>
						<th scope="col">Prix de location</th>
					</tr>
					</thead>
					<tbody>
					<%
						List<TupleChambre> chambres = AubergeHelper.getAubergeInterro(session).getGestionChambre().ListerChambresLibres();
						for (TupleChambre ch : chambres)
						{
					%>
					<tr>
						<td><%=ch.getIdChambre()%></td>
						<td><%=ch.getNom()%></td>
						<td><%=ch.getType()%></td>
						<td><%=ch.getPrixBase()%></td>
					</tr>
					<%
						}
					%>
					</tbody>
				</table>
				<FORM action="Reservation" METHOD="get">
					<input class="btn btn-primary" type="SUBMIT" name="allerReserver" value="Réserver">
				</FORM>
			</div>
			<div class="col-6">
				<img src="https://s-media-cache-ak0.pinimg.com/originals/29/04/16/290416516a8a633375a8738510e34df2.jpg" alt="notre ami">
			</div>
		</div>
	</div>
	<%
	} // end if admin
	else
	{
		GestionAubergeInn a = AubergeHelper.getAubergeInterro(session);
		List<TupleReserveChambre> reservations = a.getGestionReservation().listerToutesReservationClient((String)session.getAttribute("userID"));
	%>
	<h3 class="text-center">Mes Réservations</h3>
	<div class="col-8 offset-2">
		<table class="table">
			<thead class="thead-dark">
			<tr>
				<th scope="col">#</th>
				<th scope="col">IdChambre</th>
				<th scope="col">Date de début</th>
				<th scope="col">Date de fin</th>
				<th scope="col">Prix de location</th>
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
				<td><%= r.getPrixTotal() %></td>
			</tr>
			<%
				}
			%>
			</tbody>
		</table>
	</div>
	<%
		} //end else
	%>
	//Petit musique d'aubergine
	<audio controls autoplay>
		//Video de Michael Franks : https://www.youtube.com/watch?v=peuy_DcJhp8&ab_channel=MCSong
		<source src="audio/Eggplant.mp3" type="audio/mpeg">
	</audio>
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
