<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<div class="navbar-collapse collapse">
    	<ul class="nav navbar-nav">
			<li><a class="nav-item nav-link" href="Accueil">Accueil</a></li>
	<%
		if (session.getAttribute("admin") != null)
		{
	%>
			<li><a class="nav-item nav-link" href="GererClient">G�rer les clients</a></li>
	<%
		}
	%>
			<li><a class="nav-item nav-link" href="ListeChambre">G�rer les chambres</a></li>
			<li><a class="nav-item nav-link" href="ListeReservation">G�rer les r�servations</a></li>
		</ul>
		</div>
		<div class="navbar-collapse collapse justify-content-end">
		<ul class="nav navbar-nav navbar-right">
			<li><a class="nav-item nav-link" href="Logout">D�connexion</a></li>
		</ul>
	</div>
</nav>
