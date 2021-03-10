<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

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
		<div class="row">
		<div class = "col-md-6 col-sm-6">
			<h3 class="page-title">Risultati Movimenti Articoli</h3>
		</div>
		<div class="col-md-6 col-sm-6">
			<div id="rep" class="datafilter">
				<label>
					Pagine: 
					 <select name="numpage" aria-controls="sample_1" class="form-control input-sm input-xsmall input-inline">
						 <option value="10">10</option>
						 <option value="15">15</option>
						 <option value="20">20</option>
						 <option value="-1">Tutti</option>
					 </select>
				</label>
			</div>
		</div>
			<table id="movimenti" class="table table-striped table-bordered">
				<thead>
		            <tr>
		                <th>CodArt</th>
		                <th>Descrizione</th>
		                <th>Acquisto</th>
		                <th>Um</th>
		                <th>Acquistato</th>
		                <th>Venduto</th>
		                <th>Reso</th>
		                <th>Uscite</th>
		                <th>Scaduti</th>
		                <th>Magazzino</th>
		                <th>Inc. Scaduto</th>
		            </tr>
	        	</thead>
	        	<tfoot>
	        	</tfoot>
	        	<tbody>
	        		<c:forEach items="${Movimenti}" var="movimento">
						<tr>
							<td>${movimento.codArt}</td>
							<td>${movimento.desArt}</td>
							<td>
							<fmt:formatNumber value = "${movimento.acquisto}"  minFractionDigits = "2" type = "number"/>
							</td>
							<td>${movimento.um}</td>
							<td>
							<fmt:formatNumber value = "${movimento.acquistato}"  minFractionDigits = "2" type = "number"/>
							</td>
							<td>
							<fmt:formatNumber value = "${movimento.venduto}"  minFractionDigits = "2" type = "number"/>
							</td>
							<td>
							<fmt:formatNumber value = "${movimento.reso}"  minFractionDigits = "2" type = "number"/>
							</td>
							<td>
							<fmt:formatNumber value = "${movimento.uscite}"  minFractionDigits = "2" type = "number"/>
							</td>
							<td>
							<fmt:formatNumber value = "${movimento.scaduti}"  minFractionDigits = "2" type = "number"/>
							</td>
							<td>
							<fmt:formatNumber value = "${movimento.magazzino}"  minFractionDigits = "2" type = "number"/>
							</td>
							<td>
							<fmt:formatNumber value = "${movimento.incscaduti}"  minFractionDigits = "2" type = "PERCENT"/>
							</td>
							
						</tr>
					</c:forEach>
	        	</tbody>
			</table>
		</div>
	</section>

