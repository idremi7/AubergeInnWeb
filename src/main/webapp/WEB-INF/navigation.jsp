<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<div class="navbar-collapse collapse">
    	<ul class="nav navbar-nav">
			<li><a class="nav-item nav-link text-white" href="Accueil">Accueil</a></li>
	<%
		if (session.getAttribute("admin") != null)
		{
	%>
			<li><a class="nav-item nav-link text-white" href="GererClient">G�rer les clients</a></li>
	<%
		}
	%>
			<li><a class="nav-item nav-link text-white" href="ListeChambre">G�rer les chambres</a></li>
			<li><a class="nav-item nav-link text-white" href="ListeCommodite">G�rer les commodit�es</a></li>
		</ul>
		</div>
		<div class="navbar-collapse collapse justify-content-end">
		<ul class="nav navbar-nav navbar-right">
			<li><a class="nav-item nav-link text-white" href="Logout">D�connexion</a></li>
		</ul>
	</div>
</nav>
