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
            	<i class="icon-settings oi oi-pencil"></i>
             	<span class="caption-subject bold uppercase"><spring:message code="insarticolo.form.titolo.label"/></span>
            </div>
		 </div>
		<div class="portlet-body form">
				<form:form  method="POST" modelAttribute="trasm" enctype="multipart/form-data">
				<div class="form-body">
					
					<div class="form-group">
						<label for="fileTerminale"><spring:message code="insarticolo.form.immagine.label"/></label>
						<form:input id="fileTerminale" path="fileTerminale" type="file" class="form:input-large" />  
					</div>
					
				</div>
				
				<hr class="line-form">
				
				<div class="form-actions">
					<input type="submit" id="btnAdd" class="btn btn-primary form-buttons" value = <spring:message code="insarticolo.form.btnAdd.label"/> />
					<input type="submit" id="btnAbort" class="btn btn-default form-buttons" value = <spring:message code="insarticolo.form.btnAbort.label"/> />
				</div>
			
				</form:form>
			</div>
	</div>
</section>