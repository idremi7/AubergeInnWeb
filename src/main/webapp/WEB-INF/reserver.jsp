<%@ page import="java.util.*,java.text.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="AubergeInnServlet.AubergeHelper" %>
<%@ page import="AubergeInn.tuples.TupleClient" %>
<%@ page import="AubergeInn.tuples.TupleChambre" %>
<!DOCTYPE html>
<html>
	<head>
		<title>IFT287 - Système de gestion de bibliothèque</title>
		<meta name="author" content="Rémi Létourneau">
		<meta name="author" content="Pierre-Daniel Godfrey">
		<meta name="description" content="Page de création de compte du système de gestion AubergeInn.">
		
		<!-- Required meta tags -->
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

		<link rel="icon" type="image/ico" href="favicon.ico" />
	    <!-- Bootstrap CSS -->
	    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
		<!-- Our CSS -->
		<link rel="stylesheet" href="css/main.css">
	</head>
	<body>
	<jsp:include page="/WEB-INF/navigation.jsp" />
		<div class="container">
			<h1 class="text-center">Réservation d'une chambre</h1>
			<div class="col-md-4 offset-md-4">
			<form action="Reservation" method="POST">
			    <div class="form-group">
					<label for="client">Client:</label>
					<select class="form-control" id="client" name="client">
						<%
							List<TupleClient> client = AubergeHelper.getAubergeInterro(session).getGestionClient().getListeClients(false);
							for (TupleClient cl : client)
							{
						%>
						<option value="<%= (cl.getIdClient())%>"><%= (cl.getPrenom())%> <%= (cl.getNom())%></option>
						<%
							} //fin pour
						%>
					</select>
				</div>
			    <div class="form-group">
					<label for="chambre">Type de chambre:</label>
					<select class="form-control" id="chambre" name="chambre">
							<%
							List<TupleChambre> chambre = AubergeHelper.getAubergeInterro(session).getGestionChambre().ListerChambres();
							for (TupleChambre ch : chambre)
							{
						%>
						<option value="<%= (ch.getIdChambre())%>"><%= (ch.getType())%> <%= (ch.getPrixBase())%></option>
							<%
							} //fin pour
						%>
					</select>
				</div>
			    <div class="form-group">
					<label for="dateDebut">Date de début:</label>
					<input class="form-control" type="date" id="dateDebut" name="dateDebut">
				</div>
			    <div class="form-group">
					<label for="dateFin">Date de fin:</label>
					<input class="form-control" type="date" id="dateFin" name="dateFin">
			    </div>
				<input class="btn btn-primary" type="SUBMIT" name="reserver" value="Réserver">
			</form>
			</div>
		</div>
		<br>
		<%-- inclusion d'une autre page pour l'affichage des messages d'erreur--%>
		<jsp:include page="/WEB-INF/messageErreur.jsp" />
		<br>
		
		<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	</body>
</html>
