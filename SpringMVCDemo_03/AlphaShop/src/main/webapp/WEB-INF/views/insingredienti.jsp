<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="jumbotron jumbotron-billboard">
	<div class="img"></div>
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<h2>${Titolo}</h2>
				<p>${Titolo2}</p>
			</div>
		</div>
	</div>
</div>
<section class="container">
	<div class="portlet light bordered">
		 <div class="portlet-title">
		 	 <div class="caption font-red-sunglo">
            	<i class="icon-settings oi oi-info"></i>
             	<span class="caption-subject bold uppercase">Dati Articolo</span>
            </div>
		 </div>
		<div class="portlet-body form">
				<form:form  method="POST" modelAttribute="Ingredienti">
				<div class="form-body">
				
					<div class="form-group">
						<label for="info">Ingredienti:</label>
						<form:textarea id="info" path="info" rows="4" class="form-control" placeholder="Ingredienti"/>  
					</div>
					
				</div>
				
				<hr class="line-form">
				
				<div class="form-actions">
					<input type="submit" id="btnAdd" class="btn btn-primary form-buttons" value = Aggiungi />
					<input type="submit" id="btnAbort" class="btn btn-default form-buttons" value = Annulla />
				</div>
			
				</form:form>
			</div>
	</div>
</section>