<%@ page import="java.util.*,java.text.*,AubergeInnServlet.*,AubergeInn.*"
		 contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="AubergeInn.tuples.TupleChambre" %>
<%@ page import="AubergeInn.tuples.TupleCommodite" %>
<!DOCTYPE html>
<html>
<head>
	<title>IFT287 - Système de gestion de d'AubergeInn</title>
	<meta name="author" content="Rémi Létourneau">
	<meta name="author" content="Pierre-Daniel Godfrey">
	<meta name="description"
		  content="Page de gestion des chambre">
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
<body>
<jsp:include page="/WEB-INF/navigation.jsp" />
<div class="container">
	<h1 class="text-center">Gestionnaire des Chambres</h1>
	<%
		if (session.getAttribute("admin") != null)
		{
	%>
	<h3 class="text-center">Afficher une chambre</h3>
	<div class="col-8 offset-2">
		<table class="table">
			<thead class="aubergineTheme">
			<%-- titre des colonnes --%>
			<tr>
				<th scope="col"># Chambre</th>
				<th scope="col">Nom</th>
				<th scope="col">Type</th>
				<th scope="col">Prix de base</th>
			</tr>
			</thead>
			<tbody>
			<%
				String idChambre = (String) session.getAttribute("idChambre");
				if(idChambre == null){
					idChambre = (String) request.getAttribute("idChambre");
					if(idChambre == null){
						idChambre = "";
					}
				}
				TupleChambre chambre = AubergeHelper.getAubergeInterro(session).getGestionChambre().getChambre(Integer.parseInt(idChambre));
			%>
			<tr>
				<td class="topValign"><%=chambre.getIdChambre()%></td>
				<td class="topValign"><%=chambre.getNom()%></td>
				<td class="topValign"><%=chambre.getType()%></td>
				<td class="topValign"><%=chambre.getPrixBase()%></td>
			<tr>
			<tr>
				<td></td>
				<td colspan="2">
					<%
						GestionAubergeInn a = AubergeHelper.getAubergeInterro(session);
						List<TupleCommodite> commodites = a.getGestionChambre().ListerCommodites(chambre.getIdChambre());
						if (commodites.size() == 0)
						{
					%>
					 	Aucune Commoditée
					<%
					}
					else
					{
					%>
					<table class="table">
						<thead class="aubergineTheme">
						<tr>
							<th scope="col"># commodité</th>
							<th scope="col">Description</th>
							<th scope="col">Prix</th>
						</tr>
						</thead>
						<tbody>
						<%
							for (TupleCommodite c : commodites)
							{
						%>
						<tr>
							<th scope="row"><%=c.getIdCommodite()%></th>
							<td><%=c.getDescription()%></td>
							<td><%=c.getPrix()%></td>
						</tr>
						<%
							} // end for chaque commodite
						%>
						</tbody>
					</table>
					<%
							} // end else commodite
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
