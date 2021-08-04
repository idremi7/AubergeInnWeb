<%@ page import="java.util.*,java.text.*,AubergeInnServlet.*,AubergeInn.*"
		 contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="AubergeInn.tuples.TupleClient" %>
<%@ page import="AubergeInn.tuples.TupleReserveChambre" %>
<%@ page import="AubergeInn.tuples.TupleChambre" %>
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
	<h3 class="text-center">Chambre libres</h3>
	<div class="col-8 offset-2">
		<table class="table">
			<thead class="thead-dark">
			<tr>
				<th scope="col"># de chambre</th>
				<th scope="col">Nom</th>
				<th scope="col">Type</th>
				<th scope="col">Prix de base</th>
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
