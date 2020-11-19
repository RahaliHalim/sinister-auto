package com.gaconnecte.auxilium.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;


//@Service
//@Transactional
@Component
@PropertySource("classpath:config/application.yml")
public class KpiDao {

    //@PersistenceContext
    //EntityManager em;
    /*
    public List<String> getPec(){
        Query query = em.createNativeQuery("select p.nom from Personne p where p.id > 2");
        //List noms = query.getResultList();

        //Query query = session.createNativeQuery("select distinct user.firstname from User user");

        List<String> list = (List<String>) query.list();
    }

    */


    private static Connection connexion;

	@Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;
    
	@Value("${spring.datasource.password}")
    private String password;
    
	public KpiDao( ) {
		
	}
	
	public void init( ) {
		try {
		    connexion = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void finalize() {
		try {
			connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


    /*
	public Integer countDossier(Date debut, Date fin) throws SQLException {

		CallableStatement cst = null;
		cst = connexion.prepareCall("{ call count_dossiers(?,?,?) }");
		cst.registerOutParameter(3,Types.INTEGER);
		cst.setDate(1,(java.sql.Date) debut);
		cst.setDate(2,(java.sql.Date) fin);
		cst.execute();
		Integer result = cst.getInt(3);
		return result;
	}
	*/

	/*
	public Integer countBonSorties(Date debut, Date fin) throws SQLException {
		CallableStatement cst = connexion.prepareCall("{ call count_bons_sortie(?,?,?) }");
		cst.registerOutParameter(3,Types.INTEGER);
		cst.setDate(1,(java.sql.Date) debut);
		cst.setDate(2,(java.sql.Date) fin);
		cst.execute();
		Integer result = cst.getInt(3);
		return result;
	}
	public Double delaiImmobilisation(Date debut, Date fin) throws SQLException {
		CallableStatement cst = connexion.prepareCall("{ call delai_immobilisation(?,?,?) }");
		cst.registerOutParameter(3,Types.DOUBLE);
		cst.setDate(1,(java.sql.Date) debut);
		cst.setDate(2,(java.sql.Date) fin);
		cst.execute();
		double result = cst.getDouble(3);
		return result;
	}
	public String[] pieDossierEtat(Date debut,Date fin) throws SQLException
	{
		Statement state = connexion.createStatement(); 
		Date d =(Date)debut;
		Date f =(Date)fin;
		ResultSet result = state.executeQuery("select d.etat_de_dossier ,  count(d.id_dossier_sinistre_auto)  from dossier_sinistre_auto d \n" + 
				"\n" + 
				"where d.date_creation between '"+d+"' and '"+f+"' \n" + 
				"group by d.etat_de_dossier;\n" + 
				"");
		Map<String, String> m = new HashMap<String, String>();
		while (result.next()) {
			m.put(result.getString(1), result.getString(2));
		}
		String[] tableau = new String[100];
		int i=0;
		Iterator it = m.entrySet().iterator();
		while (it.hasNext() && (i<200)) {

			Map.Entry pair = (Map.Entry)it.next();
			tableau[i]=(String) pair.getKey();
			tableau[i+1]=(String) pair.getValue();
			i=i+2;
			it.remove(); 
		}
		return tableau;
	}

	
	
	public List<Integer> getNombre()  throws Exception {
		Statement state = connexion.createStatement();
		ResultSet result = state.executeQuery("select extract(year from date_creation), count(*) \n" + 
				"from dossier_sinistre_auto\n" + "Group by EXTRACT(YEAR FROM date_creation)");
		ResultSetMetaData resultMeta = result.getMetaData();
		List<Integer> list = new ArrayList<Integer>();
		while(result.next()){

			for (int i = 1; i <=resultMeta.getColumnCount(); i++){

				list.add(result.getInt(i));
			}
		}
		return list;
	}

	

	
	
	public List<Integer> getChiffreTotalGlissant()  throws Exception {
		Statement state = connexion.createStatement();
		ResultSet result = state.executeQuery("select extract(year from a.date_creation),extract(month from a.date_creation), sum(h.mnt_ttc) as montant \n" + 
				"from dossier_sinistre_auto a, piece_identifie_par_expert h, devis_main_d_oeuvre i,  devis j\n" + 
				"where h.devis = i.id_devis_main_ouvre and i.devis = j.id_devis and j.dossier_sinistre_auto = a.id_dossier_sinistre_auto \n" + 
				"and\n" + 
				"a.supprime is null \n" + 
				"Group by EXTRACT(YEAR FROM a.date_creation), EXTRACT(MONTH from a.date_creation) \n" + 
				"order by (EXTRACT(YEAR FROM a.date_creation),extract(month from a.date_creation)) DESC");
		ResultSetMetaData resultMeta = result.getMetaData();
		List<Integer> list = new ArrayList<Integer>();
		while(result.next()){
			for (int i = 1; i <=resultMeta.getColumnCount(); i++){
				list.add(result.getInt(i));
			}
		}
		return list;
	}
	
	
	public String[] chiffreAffaireZoneGeo(Date debut, Date fin) throws SQLException
	{
		Statement state = connexion.createStatement();
		Date d =debut;
		Date f =fin;
		ResultSet result = state.executeQuery("select d.libelle, sum(h.mnt_ttc) as \"Montant TTC\"\n" + 
				"from dossier_sinistre_auto a, zone_geographique d, piece_identifie_par_expert h, devis_main_d_oeuvre i,  devis j\n" + 
				"where h.devis = i.id_devis_main_ouvre and i.devis = j.id_devis and j.dossier_sinistre_auto = a.id_dossier_sinistre_auto and a.zone_geographique = d.id_zone_geographique and \n" + 
				"a.date_creation between '"+d+"'  and '"+f+"' and a.supprime is null\n" + 
				"group by d.libelle;");

		Map<String, String> m = new HashMap<String, String>();
		while (result.next()) {
			m.put(result.getString(1), result.getString(2));
		}
		String[] tableau = new String[100];
		int i=0;
		Iterator it = m.entrySet().iterator();
		while (it.hasNext()&& (i<200)) {
			Map.Entry pair = (Map.Entry)it.next();
			tableau[i]=(String) pair.getKey();
			tableau[i+1]=(String) pair.getValue();
			i=i+2;
			it.remove(); 
		}
		return tableau;
	}

	public String[] nombreDossierGouvernorat(Date debut, Date fin) throws SQLException
	{
	    Statement state = connexion.createStatement();
        Date d =(Date)debut;
        Date f = (Date)fin;
        ResultSet result = state.executeQuery("select d.libelle, count(c.id_dossier_sinistre_auto) as nombre "
        		+ "from dossier_sinistre_auto c, gouvernorat d where c.gouvernorat = d.id_gouvernorat "
        		+ "and c.supprime is null and c.date_creation between '"+d+"' and '"+f+"'"
        		+ "group by d.libelle order by nombre desc;");
        Map<String, String> m = new HashMap<String, String>();      
        while (result.next()) {
        	m.put(result.getString(1), result.getString(2));
        }      
        String[] tableau = new String[100];    
        int i=0;   
        Iterator it = m.entrySet().iterator();     
        while (it.hasNext()&& (i<200)) {
        	Map.Entry pair = (Map.Entry)it.next();
            tableau[i]=(String) pair.getKey();
            tableau[i+1]=(String) pair.getValue();
            i=i+2;          
            it.remove(); 
      }
      return tableau;
	}
	public String[] chiffreAffaireGouvernorat(Date debut, Date fin) throws SQLException
	{
	    Statement state = connexion.createStatement();
        Date d =(Date)debut;
        Date f = (Date)fin;
        ResultSet result = state.executeQuery("select p.libelle, sum(h.mnt_ttc) as \"Montant TTC\"\n" + 
        		"from dossier_sinistre_auto a,  gouvernorat p, piece_identifie_par_expert h, devis_main_d_oeuvre i,  devis j\n" + 
        		"where h.devis = i.id_devis_main_ouvre and i.devis = j.id_devis and j.dossier_sinistre_auto = a.id_dossier_sinistre_auto and a.gouvernorat = p.id_gouvernorat and \n" + 
        		"a.date_creation between '"+d+"' and '"+f+"' and a.supprime is null\n" + 
        		"group by p.libelle");
        Map<String, String> m = new HashMap<String, String>();      
        while (result.next()) {
        	m.put(result.getString(1), result.getString(2));
        }      
        String[] tableau = new String[100];    
        int i=0;   
        Iterator it = m.entrySet().iterator();     
        while (it.hasNext() && (i<200)) {
        	Map.Entry pair = (Map.Entry)it.next();
            tableau[i]=(String) pair.getKey();
            tableau[i+1]=(String) pair.getValue();
            i=i+2;          
            it.remove(); 
      }
      return tableau;
	}
	public String[] chiffreAffaireCompagnie(Date debut, Date fin) throws SQLException
	{
	    Statement state = connexion.createStatement();
        Date d =(Date)debut;
        Date f = (Date)fin;
        ResultSet result = state.executeQuery("select  b.nom_compagnie, sum(h.mnt_ttc) as \"Montant TTC\"\n" + 
        		"from dossier_sinistre_auto a, compagnie_assurance b, piece_identifie_par_expert h, devis_main_d_oeuvre i,  devis j\n" + 
        		"where h.devis = i.id_devis_main_ouvre and i.devis = j.id_devis and j.dossier_sinistre_auto = a.id_dossier_sinistre_auto and a.compagnie_assurance = b.id_compagnie_assurance and \n" + 
        		"a.date_creation between '"+d+"' and '"+f+"' and a.supprime is null\n" + 
        		"group by  b.nom_compagnie;");
        Map<String, String> m = new HashMap<String, String>();      
        while (result.next()) {
        	m.put(result.getString(1), result.getString(2));
        }      
        String[] tableau = new String[100];    
        int i=0;   
        Iterator it = m.entrySet().iterator();     
        while (it.hasNext() && (i<200)) {
        	Map.Entry pair = (Map.Entry)it.next();
            tableau[i]=(String) pair.getKey();
            tableau[i+1]=(String) pair.getValue();
            i=i+2;          
            it.remove(); 
      }
      return tableau;
	}
	
	
	
	
	
	public String[] nombreDossierPointChoc(@PathVariable("debut") Date debut, @PathVariable("fin") Date fin) throws SQLException
	{
	    Statement state = connexion.createStatement();   
        Date d =debut;
        Date f = fin;      
        String[] tableau = {"face_av","tiers_av_d","tiers_lat_d","lat_d","tiers_ar_d","tiers_lat_g",
        		"tiers_ar_g","lat_g","face_ar","divers_choc","pare_brise","triangle_av_d",
        		"triangle_ar_d","triangle_av_g","triangle_ar_g","vitre_av_d","vitre_ar_d","vitre_av_g",
        		"vitre_ar_g","lunette_ar","tiers_av_g"};      
        String[] nombre= new String[42];
        int j=0;
        for(int i=0;i<(tableau.length)*2;i=i+2) {
        	ResultSet result = state.executeQuery("select count(*) from point_choc a, point_choc_siniste b,"
        			+ " dossier_sinistre_auto c where a.id_point_choc = b.point_choc "
        			+ "and b.dossier = c.id_dossier_sinistre_auto and c.supprime is null "
        			+ " and c.date_creation between '"+d+"' and '"+f+"' and a."+ tableau[j] +" = TRUE;");

        	while (result.next()) {
        		nombre[i]=tableau[j];
        		nombre[i+1]=result.getString(1);
        	}
        	j++;
        }
        return nombre;
	}
	

	

	*/

	public List<Integer> getNombreDossier()  throws Exception {
		Statement state = connexion.createStatement();
		ResultSet result = state.executeQuery("select extract(year from date_creation),extract(month from date_creation), count(*)  \n" + 
				"				 		from prestation\n" + 
				"				 		Group by EXTRACT(YEAR FROM date_creation), EXTRACT(MONTH from date_creation) \n" + 
				"				 		order by (EXTRACT(YEAR FROM date_creation),extract(month from date_creation)) DESC");
		ResultSetMetaData resultMeta = result.getMetaData();
		List<Integer> list = new ArrayList<Integer>();
		while(result.next()){
			for (int i = 1; i <=resultMeta.getColumnCount(); i++){
				list.add(result.getInt(i));
			}
		}
		return list;
	}

	public String[] chiffreAffaireAgence() throws SQLException
	{
		Statement state = connexion.createStatement();
        //Date d =debut;
        //Date f = fin;
        ResultSet result = state.executeQuery("select p.raison_sociale, count(a.prestation_id) as nombre_affectation\n" + 
        		"from personne_morale p, affect_expert a, expert e\n" + 
        		"where e.personne_morale_id = p.id and e.id = a.expert_id \n" + 
        		"group by p.raison_sociale order by nombre_affectation desc;");
        int i=0;      
        String[] tableau = new String[100];     
        while ((result.next()) && (i<40)) {    	
    	tableau[i]=(result.getString(1));    	
    	tableau[i+1]=(result.getString(2));
               i=i+2;
    }
      return tableau;
		
	}

	public List<Integer> getAnnee()  throws Exception {
		

		Statement state = connexion.createStatement();
		ResultSet result = state.executeQuery("SELECT   distinct   date_part( 'year', date_creation ) as years FROM prestation order by years desc");
		ResultSetMetaData resultMeta = result.getMetaData();
		List<Integer> list = new ArrayList<Integer>();
		while(result.next()){
			for (int i = 1; i <=resultMeta.getColumnCount(); i++){
				list.add(result.getInt(i));
			}
		}
		return list;
    }
    
   
    public String[] nombreDpec(LocalDate debut, LocalDate fin) throws SQLException
	{
	    
      return null;   
	}


	public String[] nombreDossierAgence() throws SQLException
	{
	    Statement state = connexion.createStatement();
        //Date d =debut;
        //Date f = fin;
        ResultSet result = state.executeQuery("select e.nom, count(pec.id) as last_total, count(pec1.id) as old_total, count(pec.id)-count(pec1.id) as difference "
        		+ "from prestation_pec pec, prestation_pec pec1, prestation p, prestation p1, dossier o, agent_general d, ref_agence e "
        		+ "where pec.prestation_id = p.id and p.dossier_id = o.id and o.agent_general = d.id and d.agence_id = e.id and pec1.prestation_id = p1.id and p1.dossier_id = o.id and o.agent_general = d.id and d.agence_id = e.id "
        		+ "and p.date_creation between (NOW() - interval '1 year') and NOW() and p1.date_creation between (NOW() - interval '2 year') and (NOW() - interval '1 year') group by e.nom;");
        int i=0;      
        String[] tableau = new String[200];     
        while ((result.next()) && (i<200)) {    	
    	tableau[i]=(result.getString(1));    	
    	tableau[i+1]=(result.getString(2));
               i=i+2;
    }
      return tableau;   
	}

	public String[] nombreDpecCompagnie() throws SQLException
	{
	    Statement state = connexion.createStatement();
		Date d = new Date();
        //Date f = fin;
        ResultSet result = state.executeQuery("select rc.nom, (count(pec.id)*100)/count(pec2.id) as taux_acceptation, count(pec3.id) as current_Cumul, count(pec4.id) as last_Cumul, (count(pec3.id)-count(pec4.id)) as difference, 100 as objectif, (count(pec3.id)-100) as pourcentage_realisation "
        		+ "from prestation p, prestation p1, dossier o, prestation_pec pec, prestation_pec pec2, prestation_pec pec3, prestation_pec pec4, ref_compagnie rc "
        		+ "where pec.prestation_id = p.id and p.dossier_id = o.id and o.compagnie_assurance = rc.id and pec.decision = 'Accepted' and pec2.prestation_id = p.id and p.dossier_id = o.id and o.compagnie_assurance = rc.id and pec3.prestation_id=p.id and p.dossier_id=o.id and o.compagnie_assurance=rc.id and p.date_creation between (NOW() - interval '1 year') and NOW() and pec4.prestation_id=p1.id and p1.dossier_id=o.id and o.compagnie_assurance=rc.id and p1.date_creation between (NOW() - interval '2 year') and (NOW() - interval '1 year') "
        		+ "group by rc.nom;");
        int i=0;      
        String[] tableau = new String[200];     
        while ((result.next()) && (i<200)) {    	
    	tableau[i]=(result.getString(1));    	
    	tableau[i+1]=(result.getString(2));
               i=i+2;
    }
	  
      return tableau;  
	}

	public String[] pieDossierAssurance() throws SQLException
	{  
		Statement state = connexion.createStatement();
		//Date d =debut;
		//Date f =fin;
		ResultSet result = state.executeQuery("select nom, count(pec.id) from prestation_pec pec, prestation p, dossier d, ref_compagnie r\n" + 
				"where pec.prestation_id = p.id\n" + 
				"and p.dossier_id = d.id and d.compagnie_assurance=r.id \n" + 
				"group by r.nom;\n" + 
				"");    
		Map<String, String> m = new HashMap<String, String>();      
		while (result.next()) {
			m.put(result.getString(1), result.getString(2));   	  
		}      
		String[] tableau = new String[100];      
		int i=0;     
		Iterator it = m.entrySet().iterator();      
		while (it.hasNext() && (i<200)) {
			Map.Entry pair = (Map.Entry)it.next();
			tableau[i]=(String) pair.getKey();
			tableau[i+1]=(String) pair.getValue();
			i=i+2;         
			it.remove(); 
		}
		return tableau;
	}
    
	/*
	public String[] meilleurReparateurs() throws SQLException
	{
	    Statement state = connexion.createStatement();
        //Date d =debut;
        //Date f = fin;
        ResultSet result = state.executeQuery("select pm.raison_sociale, sum(j.total_ttc) as montant, montant/count(g.prestation_id)\n" + 
        		"from prestation_pec a, prestation p, personne_morale pm, reparateur f, affect_reparateur g, devis j\n" + 
        		"where j.reparateur_id = p.id and p.personne_morale_id = pm.id and j.reparateur_id= g.reparateur_id and j.dossier_sinistre_auto = a.id_dossier_sinistre_auto and g.reparateur = f.id_reparateur and g.dossier_sinistre_auto = a.id_dossier_sinistre_auto and \n" + 
        		"a.date_creation between '"+d+"' and '"+f+"' and a.supprime is null\n" + 
        		"group by  f.nom_raison_sociale order by montant desc;");
        int i=0;      
        String[] tableau = new String[100];     
        while ((result.next()) && (i<40)) {    	
    	tableau[i]=(result.getString(1));    	
    	tableau[i+1]=(result.getString(2));
               i=i+2;
    }
      return tableau;   
	}
	*/

	public String[] chiffreAffaireRemorqeur(@PathVariable("debut") Date debut, @PathVariable("fin") Date fin) throws SQLException
	{
	      
      return null;  
	}



    

}
