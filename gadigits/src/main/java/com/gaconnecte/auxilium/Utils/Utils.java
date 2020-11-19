package com.gaconnecte.auxilium.Utils;

import com.gaconnecte.auxilium.domain.Assure;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * Created by Yamen BANNOUR on 9/27/2017.
 */
public class Utils {

	public static final Integer ASSISTANCE = 10;
	public static final Integer START_DAY = 6;
	public static final Integer END_DAY = 22;

	public static String getCurrentYear() {
		LocalDateTime currentTime = LocalDateTime.now();
		return String.valueOf(currentTime.getYear());
	}

	public static String getIpAdress(HttpServletRequest request) {
		String remoteAddr = "";
		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}
		return remoteAddr;
	}

	public static final String CREATEDOSSIER = "Creer Dossier";
	public static final String UPDATEDOSSIER = "Update Dossier";
	public static final String DELETEDOSSIER = "Supprimer Dossier";

	public static final String CREATEPRESTATION = "Creer Prestation";
	public static final String UPDATEPRESTATION = "Update Prestation";
	public static final String DELETEPRESTATION = "Supprimer Prestation";

	public static final String CREATESERVICERMQ = "Creer service Remorquage";
	public static final String UPDATESERVICERMQ = "Update service Remorquage";
	public static final String DELETESERVICERMQ = "Supprimer service Remorquage";
	public static final String CANCELSRVICERMQ = "Annuler service Remorquage";
	public static final String CLOTURERSRVICERMQ = "Cloturer service Remorquage";
	public static final String REOUVRIRSRVICERMQ = "Reouvrir service Remorquage";

	public static final String LOGOUT = "Logout";
	public static final String LOGIN = "Login";
	public static final String REGISTER = "Register";
	public static final String ACTIVATE = "Activate";
	public static final String CHANGEPWD = "ChangePWD";
	public static final String RESETPASSWORD = "ResetPWD";

	public static Long CIRCONSTANCE_CONFORME_OK_POUR_DEMONTAGE = 1L;
	public static Long EXPERTISE_TERAIN = 2L;
	public static Long RECONSTITUTION = 3L;
	public static Long ACCORD_POUR_REPARATION_AVEC_MODIFICATION = 40L;

	public static String CIRCONSTANCE_CONFORM_POUR_DEMONTAGE = " CIRCONSTANCE CONFORME OK POUR DEMONTAGE";
	public static String EXPERT_TERAIN = "EXPERTISE TERAIN";
	public static String RECONSTITUTION_DEVIS = "RECONSTITUTION";
	public static String ACCORD_POUR_REPARATION_MODIFICATION = " ACCORD POUR REPARATION AVEC MODIFICATION";
	public static String DEVIS_NON_CONFIRME_PAR_REPARATEUR = "DEVIS NON CONFIRME PAR LE REPARATEUR";
	public static String REPARATION_DEMONTAGE = "DEMONTAGE";
	public static String REPARATION_VERIFICATION_PEC = "VERIFICATION PEC";
	public static String REPARATION_VERIFICATION_DEVIS = "VERIFICATION DEVIS";

	public static Long CHOC_NON_LEGER = 0L;
	public static Long CHOC_LEGER = 1L;
	// Quotation Constants

	public static Long DEVIS_NON_CONFIRME_PAR__LE_REPARATEUR = 14L;

	// Quotation Constants
	public static final Long QUOTATION_STATUS_NULL = 0L;
	public static final Long QUOTATION_STATUS_IN_PROGRESS = 1L;
	public static final Long QUOTATION_STATUS_ACCORD_VALIDATED_BY_GA = 6L;
	public static final Long QUOTATION_STATUS_ACCORD_GENERATED_BY_REPAIR = 9L;
	public static final Long QUOTATION_STATUS_ACCORD_SIGNED_BY_ASSURE = 8L;

	public static final Long BS_STATUS_VALIDATED_BY_GA = 12L;
	public static final Long BS_STATUS_GENERATED_BY_REPAIR = 13L;

	public static final Long DEMONTAGE = 15L;

	public static final Long QUOTATION_STATUS_VALID_GA = 4L;
	public static final Long QUOTATION_STATUS_EXPERTISE_TERRAIN = 5L;

	public String getInsuredName(Assure insured) {
		return insured.getCompany() ? insured.getRaisonSociale() : insured.getPrenom() + " " + insured.getNom();
	}

}
