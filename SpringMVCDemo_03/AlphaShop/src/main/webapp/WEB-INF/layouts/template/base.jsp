<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> 
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
 

<!doctype html>
<html lang="it">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link href="<c:url value="/static/css/bootstrap.min.css" />" rel="stylesheet">
    
    <!-- open-iconic -->
    <link href="<c:url value="/static/css/open-iconic-bootstrap.css" />" rel="stylesheet">
    
    <!-- Altri CSS -->
    <link href="<c:url value="/static/css/main.css" />" rel="stylesheet">
    
    <title><tiles:insertAttribute name="title" /></title>
  </head>
  <body>
  <!-- Navigation Bar --> 
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
    	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
    		<span class="navbar-toggler-icon"></span>
  		</button>
  		<div class="collapse navbar-collapse" id="navbarTogglerDemo01">
  		
  			 <a class="navbar-brand" href="<spring:url value="/webstore/lastart"/>">Alpha Shop</a>
  			     <ul class="nav mr-auto mt-2 mt-lg-0">
      				<li class="nav-item active">
        				<a class="nav-link" href="#">
        					<span class="oi oi-home" title="home" aria-hidden="true"></span>
        					Home 
        					<span class="sr-only">(current)</span>
        				</a>
      				</li>
      				<li class="nav-item">
        				<a class="nav-link" href="<spring:url value="/articoli/" /> ">
        					<span class="oi oi-box" title="box" aria-hidden="true"></span>
        					<spring:message code="base.navbar.menu1"/> 
        				</a>
      				</li>
      				<li class="nav-item">
        				<a class="nav-link" href="#">
        					<span class="oi oi-bullhorn" title="bullhorn" aria-hidden="true"></span>
        					<spring:message code="base.navbar.menu2"/>  
        				</a>
      				</li>
      				<li class="nav-item">
        				<a class="nav-link" href="#">
        					<span class="oi oi-credit-card" title="credit-card" aria-hidden="true"></span>
        					<spring:message code="base.navbar.menu3"/>   
        				</a>
      				</li>
      				<li class="nav-item">
        				<a class="nav-link" href="#">
        					<span class="oi oi-cart" title="cart" aria-hidden="true"></span>
        					<span class="badge"></span>
        					<spring:message code="base.navbar.menu4"/>    
        				</a>
      				</li>
      				<li class="nav-item">
        				<a class="nav-link" href="<spring:url value="/clienti/" /> ">
        					<span class="oi oi-people" title="clienti" aria-hidden="true"></span>
        					<spring:message code="base.navbar.menu5"/>   
        				</a>
      				</li>
    			</ul>
    			
    			<!-- Search Box -->
    			<c:choose>
    				<c:when test = "${IsClienti}">
    					<form:form class="form-inline my-2 my-lg-0" id="search" role="search" method="GET" action="/AlphaShop/clienti/search">
    						<input type="text" onClick="this.select();"  class="form-control mr-sm-2" name="filter" value="${filter}" placeholder="Cerca Clienti">
    						<button class="btn btn-outline-success my-2 my-sm-0" type="submit"><spring:message code="base.search.button"/></button>
    					</form:form>
    				</c:when>
    				<c:when test = "${IsArticoli}">
    					<form:form class="form-inline my-2 my-lg-0" id="search" role="search" method="GET" action="/AlphaShop/articoli/search">
		      				<input type="text" onClick="this.select();"  class="form-control mr-sm-2" name="filter" value="${filter}" placeholder="Cerca Articoli">
		      				<button class="btn btn-outline-success my-2 my-sm-0" type="submit"><spring:message code="base.search.button"/></button>
		    			</form:form>
    				</c:when>
    			</c:choose>
    			
    			<!-- dropdown user menu -->
    			<div class="dropdown">
    			
    				<button class="btn btn-default dropdown-toggle thumbnail"
    					type="button"
    					id="dropdownMenu1"
    					data-toggle="dropdown"
    					aria-haspopup="true" 
    					aria-expanded="true">
    					<img class="img-circle" src="<c:url value="/static/images/offline_user.png" />">
    					<span class="caret"></span>
    				</button>
    				
    				<div class="dropdown-menu" aria-labelledby="dropdownMenu1">
    				
    					<c:choose>
    						<c:when test="${User != null}">
    							<a class="dropdown-item disabled" href="#"><spring:message code="base.dropdwn.menu1"/></a>
    						</c:when>
    						<c:otherwise>
    							<a class="dropdown-item" href="<spring:url value="login/form" /> "><spring:message code="base.dropdwn.menu1"/></a>
    						</c:otherwise>
    					</c:choose>
    					<a class="dropdown-item" href="#"><spring:message code="base.dropdwn.menu2"/></a>
    					<div class="dropdown-divider"></div>
    					
    					<c:choose>
    						<c:when test = "${User != null}">
    							<form id="myHiddenFormId" action="/AlphaShop/login/form?logout " method="post" style="display: none">
									<input type="hidden" name="logout" value="${User}">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								</form>
								<a class="dropdown-item" href="" onclick="$('#myHiddenFormId').submit(); return false;" title="Logout"><spring:message code="base.dropdwn.menu3"/> ${User}</a>
    						</c:when>
    						<c:otherwise>
    							<a class="dropdown-item disabled" href="#"><spring:message code="base.dropdwn.menu3"/></a>
    						</c:otherwise>
    					</c:choose>
    					
    					
    				</div>
    			</div>
  		</div>
    </nav> 
    
     <tiles:insertAttribute name="content" /> 
     
     <tiles:insertAttribute name="footer" />  
      
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script type="text/javascript" src="<c:url value="/static/js/jquery-3.2.1.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/static/js/bootstrap.min.js" />"></script>
     <script type="text/javascript" src="<c:url value="/static/js/main.js" />"></script>
  </body>
</html>