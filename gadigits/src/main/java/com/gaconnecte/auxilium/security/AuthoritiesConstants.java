package com.gaconnecte.auxilium.security;

/**
 * Constants for Spring Security authorities.
 */

public final class AuthoritiesConstants {

	public static final String ADMIN = "ROLE_ADMIN";

	public static final String USER = "ROLE_USER";

	public static final String ANONYMOUS = "ROLE_ANONYMOUS";

	public static final String ROLE_GESTIONNAIRE = "ROLE_GESTIONNAIRE";

	public static final String ROLE_REPARATEUR = "ROLE_REPARATEUR";

	public static final String ROLE_EXPERT = "ROLE_EXPERT";

	public static final String ROLE_AGGENERAL = "ROLE_AGGENERAL";

	public static final String ROLE_AGCOMPAGNIE = "ROLE_AGCOMPAGNIE";

	public static final String ROLE_CHARGÉ_SINISTRE = "ROLE_CHARGÉ_SINISTRE";

	public static final String ROLE_RESPONSABLE_PEC = "ROLE_RESPONSABLE_PEC";

	public static final String ROLE_RESPONSABLE_CONTROLE_TECHNIQUE = "ROLE_RESPONSABLE_CONTROLE_TECHNIQUE";

	public static final String ROLE_DIRECTEUR_PRESTATION = "ROLE_DIRECTEUR_PRESTATION";

	private AuthoritiesConstants() {
	}
}
