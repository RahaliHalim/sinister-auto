package com.gaconnecte.auxilium.domain.enumeration;

/**
 * The EtatDevis enumeration.
 */
public enum EtatDevis {
   Accepte,                /* Annulé par l'Expert   */
   Valide,                 /* Validation par Expert  */
   ValideGestionnaire,     /* Validation par Gest.Tech */
   Accord ,                 /*   Accord  par Gest.Tech */
   GenereAccord,           /* Generer Accord  par reparateur */
   En_cours,               /* Crée par Gest Acceptation  */
   Soumis,                 /* Valider par reparateur      */
   Refuse,                 /*                       */
   Facture,                /* Facturation par reparateur */
   ValideFacturation,      /* Valider Facture par Gest.Tech */
   Refuse_Apres_Facture,          /*  */
   Generation_bon_sortie_accepte,  /* Facturation par reparateur */
   Signature_Accord, /* Jointure de l'accord signé par l'assuré au niveau réparateur */
   Signature_BS, /* Jointure du bon de sortie par réparateur */
   Demontage,
   Expertise,
   AccordCmp,
   RDV_REPARATION
}
