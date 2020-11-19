<!DOCTYPE html>
<html lang="en"><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta charset="utf-8"/>
    <title>Example 1</title>
    <style>
    <#include "css/style.css">
    </style>
</head>
	<body>
		
		<table style="width: 100%">
			<tr><td>
				<table style="width: 100%">
		        	<tr>
		        		<td style="text-align: center">
		        			<img src="${statement.logo64}" width="250" height="150" />
		        		</td>
		        	</tr>
		        	<tr>
		        		<td style="text-align: center">
		        			<img src="${statement.slogon64}" width="350" height="25" />
		        		</td>
		        	</tr>
				</table>
			</td></tr>
			<tr><td>
				<table style="width: 100%">
		        	<tr>
		        		<td class="title1">
		        			<span>FACTURE <small>${statement.invoiceReference}</small></span>
		        		</td>
		        	</tr>
				</table>
			</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>
				<table style="width: 100%">
			        <tr><td class="supplier-title"><span>RAISON SOCIALE</span></td><td class="supplier-info"> ${statement.socialReason}</td></tr>
			        <tr><td class="supplier-title"><span>ADRESSE</span></td><td class="supplier-info"> ${statement.adress}</td></tr>
			        <tr><td class="supplier-title"><span>TELEPHONE</span></td><td class="supplier-info"> ${statement.tel}</td></tr>
			        <tr><td class="supplier-title"><span>M F</span></td><td class="supplier-info"> ${statement.taxRegistration}</td></tr>
				</table>
			</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>
				<table style="width: 100%">
			        <tr><td class="supplier-title"><span>CLIENT</span></td><td class="supplier-info"> GENERALE ASSISTANCE</td></tr>
			        <tr><td class="supplier-title"><span>ADRESSE</span></td><td class="supplier-info"> ${statement.adressGA}</td></tr>
			        <tr><td class="supplier-title"><span>TELEPHONE</span></td><td class="supplier-info"> ${statement.telGA}</td></tr>
			        <tr><td class="supplier-title"><span>M F</span></td><td class="supplier-info"> ${statement.taxRegistrationGA}</td></tr>
				</table>
			</td></tr>	
			<tr><td>&nbsp;</td></tr>			
			<#list statement.companies as company>			
			<tr><td>
		      <table style="width: 100%">
		        <thead>
		          <tr>
		            <th class="header-company1" colspan="2" style="width: 20%">COMPAGNIE</th>
		            <th class="header-company1" colspan="9" style="text-align: center;">${company.companyName}</th>
		          </tr>
		          <tr>
		            <th class="header-company" style="width: 8%">DATE D'INCIDENT</th>
		            <th class="header-company" style="width: 12%">REF. DOSSIER</th>
		            <th class="header-company" style="width: 10%">NOM & PRENOM</th>
		            <th class="header-company" style="width: 10%">N° CONTRAT</th>
		            <th class="header-company" style="width: 12%">IMMATRICULATION</th>
		            <th class="header-company" style="width: 10%">LIEU SURVENANCE</th>
		            <th class="header-company" style="width: 10%">DESTINATION</th>
		            <th class="header-company" style="width: 5%">KLM</th>
		            <th class="header-company" style="width: 10%">SERVICE</th>
		            <th class="header-company" style="width: 5%">N° TEL</th>
		            <th class="header-company" style="width: 8%">MONTANT TND</th>			
		          </tr>
		        </thead>
		        <tbody>
		        	<#list company.prestations as prestation>	
		          <tr>
		            <td class="table-data">${prestation.stringIncidentDate}</td>
		            <td class="table-data">${prestation.reference}</td>
		            <td class="table-data">${prestation.insuredName}</td>
		            <td class="table-data">${prestation.contractNumber}</td>
					<td class="table-data">${prestation.vehicleRegistration}</td>
					<td class="table-data">${prestation.incidentLocationLabel}</td>
					<td class="table-data">${prestation.destinationLocationLabel}</td>
					<td class="table-data" style="text-align: right;">${prestation.mileage}</td>
					<td class="table-data">${prestation.serviceTypeLabel}</td>
					<td class="table-data">${prestation.insuredFirstTel}</td>
		            <td class="table-data" style="text-align: right;">${prestation.priceHt}</td>
		          </tr>
		          </#list>
		          <tr>
		            <td colspan="10" class="supplier-title">TOTAL</td>
		            <td class="table-total tright">${company.totalHt}</td>
		          </tr>
		        </tbody>
		      </table>
			
			</td></tr>				
			</#list>
			<tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>
			<table style="width: 100%">
				<tbody>
		          <tr>
		            <td class="supplier-title tright" style="width: 90%">TOTAL GENERAL</td>
		            <td class="table-total tright">${statement.tht}</td>
		          </tr>
		          <tr>
		            <td class="supplier-title tright" style="width: 90%">TVA</td>
		            <td class="table-total tright">${statement.vat}</td>
		          </tr>
		          <tr>
		            <td class="supplier-title tright" style="width: 90%">TIMBRE FISCAL</td>
		            <td class="table-total tright">${statement.stamped}</td>
		          </tr>
		          <tr>
		            <td class="supplier-title tright" style="width: 90%">TOTAL TTC</td>
		            <td class="table-total tright">${statement.ttc}</td>
		          </tr>
				</tbody>
			</table>
			
			</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>
			<table style="width: 100%">
				<tbody>
		          <tr>
		            <td class="supplier-title" style="width: 30%;text-align: center;">Cachet et Signature</td>
		            <td>&nbsp;</td>
		          </tr>
				</tbody>
			</table>
			
			</td></tr>			
		</table>		
	</body>
</html>