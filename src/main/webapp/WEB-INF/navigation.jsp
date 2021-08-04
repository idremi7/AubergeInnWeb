<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<div class="navbar-collapse collapse">
    	<ul class="nav navbar-nav">
			<li><a class="nav-item nav-link" href="Accueil">Accueil</a></li>
	<%
		if (session.getAttribute("admin") != null)
		{
	%>
			<li><a class="nav-item nav-link" href="GererClient">Gérer les clients</a></li>
	<%
		}
	%>
			<li><a class="nav-item nav-link" href="ListeChambre">Gérer les chambres</a></li>
			<li><a class="nav-item nav-link" href="ListeReservation">Gérer les réservations</a></li>
		</ul>
		</div>
		<div class="navbar-collapse collapse justify-content-end">
		<ul class="nav navbar-nav navbar-right">
			<li><a class="nav-item nav-link" href="Logout">Déconnexion</a></li>
		</ul>
	</div>
</nav>
