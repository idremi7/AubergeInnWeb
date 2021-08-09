<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<div class="navbar-collapse collapse">
    	<ul class="nav navbar-nav">
			<li><a class="nav-item nav-link text-white" href="Accueil">Accueil</a></li>
	<%
		if (session.getAttribute("admin") != null)
		{
	%>
			<li><a class="nav-item nav-link text-white" href="GererClient">Gérer les clients</a></li>
	<%
		}
	%>
			<li><a class="nav-item nav-link text-white" href="ListeChambre">Gérer les chambres</a></li>
			<li><a class="nav-item nav-link text-white" href="ListeCommodite">Gérer les commoditées</a></li>
		</ul>
		</div>
		<div class="navbar-collapse collapse justify-content-end">
		<ul class="nav navbar-nav navbar-right">
			<li><a class="nav-item nav-link text-white" href="Logout">Déconnexion</a></li>
		</ul>
	</div>
</nav>
