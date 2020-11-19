import { MenuItems } from "../entities/MenuItems/menu.model";

export class Global {
    public static textPattern = '^[0-9a-zA-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüýÿ\/]+( [0-9a-zA-ZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØŒŠþÙÚÛÜÝŸàáâãäåæçèéêëìíîïðñòóôõöøœšÞùúûüýÿ\/\s\(\)\-]+)*$';
}

export class MenuList {
    public static ListeOfMenu: MenuSquelette[] = [
        { id: 1, libelle: "libelle1" },
        { id: 2, libelle: "libelle2" },
        { id: 3, libelle: "libelle3" },
        { id: 4, libelle: "libelle4" },
        { id: 5, libelle: "libelle5" },
    ]
}

export class TypeAgence {
    public id: number;
    public libelle: String;
}

// export class Type{
//     public static ListeOfMenu : MenuItems[] = [
//         {id: 1, libelle: "Agent"},
//         {id: 2, libelle: "Courtier"},
//         {id: 3, libelle: "Bureau direct"}
//     ]
// }
export class Type {
    public id: number;
    public libelle: String;
}

export class Categorie {
    public id: number;
    public libelle: String;
}

export class MenuSquelette {
    public id: number;
    public libelle: String;
}

export class Cells {
    public static ASSISTANCE = 1;
    public static ACCEPTATION = 2;
    public static REPARATION = 3;
    public static TECHNIQUE = 4;
}

export class Authorities {
    public static connectServer = "wss://notif.gadigits.com:8443/my-ws/websocket";
    public static ADMIN = 'ROLE_ADMIN';
    public static GESTIONNAIRE = 'ROLE_GESTIONNAIRE';
    public static CCELLULE = 'ROLE_CCELLULE';
    public static RESPONSABLE = 'ROLE_RESPONSABLE';
    public static ASSURE = 'ROLE_ASSURE';
    public static REPARATEUR = 'ROLE_REPARATEUR';
    public static EXPERT = 'ROLE_EXPERT';
    public static AGGENERAL = 'ROLE_AGGENERAL';
    public static AGCOMPAGNIE = 'ROLE_AGCOMPAGNIE';
}

export class PrestationType {
    public static AVT = 'AVT';
    public static PEC = 'PEC';
}

export class ManagementModes {
    public static MODE_GESION_IDA_ID = 1;
    public static MODE_GESION_HIDA_ID = 2;
    public static MODE_GESION_Dommage_ID = 3;
    public static MODE_GESION_CONNEXE_ID = 4;
}

export class Features {
    public static FEATURE_PRESTATION_PEC_CREATE = 13;
    public static FEATURE_PRESTATION_AVT_CREATE = 6;
}

export class GaDatatable {
    public static defaultDtOptions = {
        pagingType: 'full_numbers',
        pageLength: 10,
        retrieve: true,
        // Declare the use of the extension in the dom parameter
        dom: '<"row"<"col-sm-6"l><"col-sm-6"f>>t<"row"<"col-sm-6"B><"col-sm-6"p>>',

        language: {
            processing: 'Traitement en cours...',
            search: 'Rechercher&nbsp;:',
            lengthMenu: 'Afficher _MENU_ &eacute;l&eacute;ments',
            info: '_START_ - _END_ / _TOTAL_',
            infoEmpty: 'La liste est vide',
            infoFiltered: '(filtr&eacute; de _MAX_ &eacute;l&eacute;ments au total)',
            infoPostFix: '',
            loadingRecords: 'Chargement en cours...',
            zeroRecords: '...',
            emptyTable: '...',
            paginate: {
                first: '<i class="fa fa-angle-double-left" style="font-size:16px"></i>',
                previous: '<i class="fa fa-angle-left" style="font-size:16px"></i>',
                next: '<i class="fa fa-angle-right" style="font-size:16px"></i>',
                last: '<i class="fa fa-angle-double-right" style="font-size:16px"></i>'
            },            
            aria: {
                sortAscending: ': activer pour trier la colonne par ordre croissant',
                sortDescending: ': activer pour trier la colonne par ordre décroissant'
            }
        },
        // Declare the use of the extension in the dom parameter
        //dom: 'Bfrtip',
        // Configure the buttons
        buttons: [
            {
                extend: 'print',
                text: '<span class="btn btn-default btn-sm"><i class="fa fa-print"></i><b> Imprimer </b></span> '
            },
            {
                extend: 'csvHtml5',
                text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-text-o"></i><b>   Csv   </b></span>',
                fieldSeparator: ';'
            },
            {
                extend: 'excelHtml5',
                text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-excel-o"></i><b>  Excel  </b></span>'
            },
            {
                extend: 'pdfHtml5',
                text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-pdf-o"></i><b>   Pdf   </b></span>',
                orientation: 'landscape',
                pageSize: 'LEGAL' 
            }
        ]
    };
    public static onlyPagingDtOptions = {
        pagingType: 'full_numbers',
        pageLength: 3,
        retrieve: true,
        // Declare the use of the extension in the dom parameter
        dom: '<"row">t<"row"<"col-sm-12"p>>',

        language: {
            processing: 'Traitement en cours...',
            search: 'Rechercher&nbsp;:',
            lengthMenu: 'Afficher _MENU_ &eacute;l&eacute;ments',
            info: '_START_ - _END_ / _TOTAL_',
            infoEmpty: 'La liste est vide',
            infoFiltered: '(filtr&eacute; de _MAX_ &eacute;l&eacute;ments au total)',
            infoPostFix: '',
            loadingRecords: 'Chargement en cours...',
            zeroRecords: 'Aucun &eacute;l&eacute;ment &agrave; afficher',
            emptyTable: 'Aucune donnée disponible dans le tableau',
            paginate: {
                first: '<i class="fa fa-angle-double-left" style="font-size:16px"></i>',
                previous: '<i class="fa fa-angle-left" style="font-size:16px"></i>',
                next: '<i class="fa fa-angle-right" style="font-size:16px"></i>',
                last: '<i class="fa fa-angle-double-right" style="font-size:16px"></i>'
            },            
            aria: {
                sortAscending: ': activer pour trier la colonne par ordre croissant',
                sortDescending: ': activer pour trier la colonne par ordre décroissant'
            }
        },
        // Declare the use of the extension in the dom parameter
        //dom: 'Bfrtip',
        // Configure the buttons
        buttons: [
            {
                extend: 'print',
                text: '<span class="btn btn-default btn-sm"><i class="fa fa-print"></i><b> Imprimer </b></span> '
            },
            {
                extend: 'csvHtml5',
                text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-text-o"></i><b>   Csv   </b></span>',
                fieldSeparator: ';'
            },
            {
                extend: 'excelHtml5',
                text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-excel-o"></i><b>  Excel  </b></span>'
            },
            {
                extend: 'pdfHtml5',
                text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-pdf-o"></i><b>   Pdf   </b></span>',
                orientation: 'landscape',
                pageSize: 'A0', customize: function (doc) { doc.defaultStyle.fontSize = 1; doc.styles.tableHeader.fontSize = 1; }
            }
        ]
    };

}

export class QuoteStatus {
    public static QUOTATION_STATUS_NULL = 0;
    public static QUOTATION_STATUS_IN_PROGRESS = 1;
    public static QUOTATION_STATUS_TO_CHEKED = 2;
    public static QUOTATION_STATUS_TO_OPINION_EXPERT = 3;
    public static QUOTATION_STATUS_VALID_GA = 4;
    public static QUOTATION_STATUS_EXPERTISE_TERRAIN = 5;
    public static QUOTATION_STATUS_ACCORD_VALIDATED_BY_GA = 6;
    public static QUOTATION_STATUS_ACCORD_SIGNED_BY_ASSURE = 8;
    public static QUOTATION_STATUS_ACCORD_GENERATED_BY_REPAIR = 9;
    public static DEVIS_CONFERME_PAR_LE_REPARATEUR = 10;
    public static QUOTATION_STATUS_IN_RECTIF = 11;
    public static DEVIS_NON_CONFIRME_PAR_LE_REPARATEUR = 14;
    public static BS_STATUS_VALIDATED_BY_GA = 12;
    public static BS_STATUS_GENERATED_BY_REPAIR = 13;
    public static DEMONTAGE = 15;
    public static APEC_SIGNATURE = 16;  
}

export class TypePiecesDevis {
    public static MAIN_OEUVRE = 1;
    public static INGREDIENT_FOURNITURE = 2;
    public static PIECES_FOURNITURE = 3;

}
export class PrestationPecReparation {
    public static CIRCONSTANCE_CONFORM_POUR_DEMONTAGE = " CIRCONSTANCE CONFORME OK POUR DEMONTAGE";
    public static EXPERT_TERAIN = "EXPERTISE TERAIN";
    public static RECONSTITUTION_DEVIS = "RECONSTITUTION";
    public static ACCORD_POUR_REPARATION_MODIFICATION = " ACCORD POUR REPARATION AVEC MODIFICATION";
    public static DEVIS_NON_CONFIRME_PAR_REPARATEUR = "DEVIS NON CONFIRME PAR LE REPARATEUR";
    public static REPARATION_DEMONTAGE = "DEMONTAGE";
    public static REPARATION_VERIFICATION_PEC = "VERIFICATION PEC";
    public static REPARATION_VERIFICATION_DEVIS = "VERIFICATION DEVIS";
}

export class TypeService {
    public static ASSISTANCE_REMPLISSAGE_CONSTAT = 1;
    public static REMORQUAGE = 2;
    public static DEPANNAGE = 3;
    public static DEPLACEMENT = 4;
    public static GRUTAGE = 5;
    public static EXTRACTION = 6;
    public static ASSISTANCE_MEDICAL = 7;
    public static AMBULANCE = 8;
    public static CONSULTATION_TELEPHONIQUE_MEDECIN = 9;
    public static FRAIS_TRANSPORT = 10;
    public static PEC_FRAIS_REPARATION = 11;
    public static VEHICULE_REMPLACEMENT = 12;
    public static ASSISTANCE_JURIDIQUE = 13;
    public static ASSISTANCE_VENTE_ACHAT = 14;

}

export class PrestationPecStep {
    public static REQUEST_DENIED = 0;
    public static REQUEST_OPENING = 1;
    public static REQUEST_OPEN_SEND = 2;
    public static BEING_PROCESSED  = 3;
    public static EXPERTISE_TERAIN = 5;
    public static RECONSTITUTION = 6;
    public static ACCORD_POUR_REPARATION_AVEC_MODIFICATION = 7;
    public static MISSIONNER_EXPERT = 8;
    public static CANCELED = 9;
    public static REFUSED = 10;
    public static ASSIGNMENT_REPAIRER = 11;
    public static RESERVES_LIFTED = 12;
    public static CHANGE_STATUS = 13;
    public static CONFIRMATION_OF_REFUSAL = 14;
    public static ATTENTE_SAISIE_DVIS = 15;
    public static APPROUVER_APEC = 100;
    public static VALIDATION_APEC = 106;
    public static CONFIRMATION_QUOTE = 17;
    public static ADVICE_EXPERT_EXPERTISE_TERRAIN = 18; 
    public static NOTICE_EXPERT_RECONSTITUTION = 19;
    public static EXPERT_ADVICE = 20;
    public static CIRCUMSTANCE_CONFORMS_OK_FOR_DISASSEMBLY = 21;
    public static RECTIFICATION_QUOTE = 23;
    public static UPDATE_QUOTE = 24;
    public static REVUE_VERIFICATION_DEVIS = 34;
    public static RECEPTION_VEHICLE = 25;
    public static Verification= 26;
    public static CONFIRMATION_MODIFICATION_PRIX= 27;
    public static MODIFICATION_PRIX_valid= 28;
    public static APPROVE= 30;
    public static CANCEL_SINISTER_PEC = 31;
    public static CONFIRMATION_CANCELLATION = 22;
    public static REFUS_SINISTER_PEC = 32;
    public static CONFIRMATION_REFUS = 33;
    public static GENERATION_BON_SORTIE = 37;
    public static VERIFICATION_PRINTED_ORIGINALS = 35;
    public static SIGNATURE_BON_SORTIE = 36;
    public static RECTIFICATION_QUOTE_ADD = 16;
    public static INSTANCE_REPARATION = 110;
    public static SINISTER_PEC_CLOTURE = 40;


}

export class SettlementMode {
    public static VIREMENT = 1;
    public static TRAITE = 4;
}

export class StatusSinister {
    public static INPROGRESS = 1;
    public static CANCELED = 2;
    public static CLOSED = 3;
    public static REOPENED = 4;
    public static NOTELIGIBLE = 5;
    public static REFUSED = 6;


}

export class GaDatatablePA {
    public static defaultDtOptions = {
        pagingType: 'full_numbers',
        pageLength: 10,
        retrieve: true,
        // Declare the use of the extension in the dom parameter
        dom: '<"row"<"col-sm-6"l><"col-sm-6"f>>t<"row"<"col-sm-6"B><"col-sm-6"p>>',

        language: {
            processing: 'Traitement en cours...',
            search: 'Rechercher&nbsp;:',
            lengthMenu: 'Afficher _MENU_ &eacute;l&eacute;ments',
            info: '_START_ - _END_ / _TOTAL_',
            infoEmpty: 'La liste est vide',
            infoFiltered: '(filtr&eacute; de _MAX_ &eacute;l&eacute;ments au total)',
            infoPostFix: '',
            loadingRecords: 'Chargement en cours...',
            zeroRecords: '...',
            emptyTable: '...',
            paginate: {
                first: '<i class="fa fa-angle-double-left" style="font-size:16px"></i>',
                previous: '<i class="fa fa-angle-left" style="font-size:16px"></i>',
                next: '<i class="fa fa-angle-right" style="font-size:16px"></i>',
                last: '<i class="fa fa-angle-double-right" style="font-size:16px"></i>'
            },            
            aria: {
                sortAscending: ': activer pour trier la colonne par ordre croissant',
                sortDescending: ': activer pour trier la colonne par ordre décroissant'
            }
        },
        // Declare the use of the extension in the dom parameter
        //dom: 'Bfrtip',
        // Configure the buttons
        buttons: [
            /*{
                extend: 'print',
                text: '<span class="btn btn-default btn-sm"><i class="fa fa-print"></i><b> Imprimer </b></span> '
            },
            {
                extend: 'csvHtml5',
                text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-text-o"></i><b>   Csv   </b></span>',
                fieldSeparator: ';'
            },
            {
                extend: 'excelHtml5',
                text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-excel-o"></i><b>  Excel  </b></span>'
            },
            {
                extend: 'pdfHtml5',
                text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-pdf-o"></i><b>   Pdf   </b></span>',
                orientation: 'landscape',
                pageSize: 'LEGAL' 
            }*/
        ]
    };
    public static onlyPagingDtOptions = {
        pagingType: 'full_numbers',
        pageLength: 3,
        retrieve: true,
        // Declare the use of the extension in the dom parameter
        dom: '<"row">t<"row"<"col-sm-12"p>>',

        language: {
            processing: 'Traitement en cours...',
            search: 'Rechercher&nbsp;:',
            lengthMenu: 'Afficher _MENU_ &eacute;l&eacute;ments',
            info: '_START_ - _END_ / _TOTAL_',
            infoEmpty: 'La liste est vide',
            infoFiltered: '(filtr&eacute; de _MAX_ &eacute;l&eacute;ments au total)',
            infoPostFix: '',
            loadingRecords: 'Chargement en cours...',
            zeroRecords: 'Aucun &eacute;l&eacute;ment &agrave; afficher',
            emptyTable: 'Aucune donnée disponible dans le tableau',
            paginate: {
                first: '<i class="fa fa-angle-double-left" style="font-size:16px"></i>',
                previous: '<i class="fa fa-angle-left" style="font-size:16px"></i>',
                next: '<i class="fa fa-angle-right" style="font-size:16px"></i>',
                last: '<i class="fa fa-angle-double-right" style="font-size:16px"></i>'
            },            
            aria: {
                sortAscending: ': activer pour trier la colonne par ordre croissant',
                sortDescending: ': activer pour trier la colonne par ordre décroissant'
            }
        },
        // Declare the use of the extension in the dom parameter
        //dom: 'Bfrtip',
        // Configure the buttons
        buttons: [
            {
                extend: 'print',
                text: '<span class="btn btn-default btn-sm"><i class="fa fa-print"></i><b> Imprimer </b></span> '
            },
            {
                extend: 'csvHtml5',
                text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-text-o"></i><b>   Csv   </b></span>',
                fieldSeparator: ';'
            },
            {
                extend: 'excelHtml5',
                text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-excel-o"></i><b>  Excel  </b></span>'
            },
            {
                extend: 'pdfHtml5',
                text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-pdf-o"></i><b>   Pdf   </b></span>',
                orientation: 'landscape',
                pageSize: 'A0', customize: function (doc) { doc.defaultStyle.fontSize = 1; doc.styles.tableHeader.fontSize = 1; }
            }
        ]
    };

}

// Code of Rules
export class CalculationRulesConst {
    public static PARTRESPTVA = 'PRRESPTVA';
    public static DEPASSPLAFONDTVA = 'DEPASSPLAFTVA';
    public static REGPROPTVA = 'REGPROPTVA';
    public static VETUSTETVA = 'VETSTVA';
    public static TVA = 'TVA';
    public static DTMB = 'DTMB';
    // Engagement GA pour IDA,HIDA,CONNEXE
    public static ENGIHCTVA = 'ENGIHCTVA';
    public static ENGIHCNTVA = 'ENGIHCNTVA';
    // Engagement GA avec test VA par rapport à  VR
    public static ENGTVAINF = 'ENGTVAINF';
    public static ENGTVAEQU = 'ENGTVAEQU';
    public static ENGNTVAINF = 'ENGNTVAINF';
    public static ENGNTVAEQU = 'ENGNTVAEQU';
    // Engagement GA avec test Total devis par rapport à VA
    public static ENGTDEVISINF = 'ENGTDEVISINF';
    public static ENGTDEVISSUP = 'ENGTDEVISSUP';
    public static ENGNDEVISINF = 'ENGNDEVISINF';
    public static ENGNDEVISSUP = 'ENGNDEVISSUP';

    /**
     * parametres non assujettis à la TVA
     */
    public static PARTRESPNONTVA = 'PRRESPNONTVA';
    public static DEPASSPLAFONDNONTVA = 'DEPASSPLAFNONTVA';
    public static REGPROPNONTVA = 'REGPROPNONTVA';
    public static VETUSTENONTVA = 'VETSNONTVA';
    public static ENGAGANONTVA = 'ENGGANONTVA';

    /**
     * autre
     */
    public static REGLESFRAISHIDA = 'REGFRAISHIDA';
    public static REGLESAVANCEFACTURE = 'REGADVFACT';
    // Franchise
    public static REGLESFRANCHISE = 'REGFRANCH';

}

export class DecisionPec {

    public static REFUSED = 'REFUSED';
    public static ACCEPTED = 'ACCEPTED';
    public static CANCELED = 'CANCELED';
    public static ACC_WITH_RESRV = 'ACC_WITH_RESRV';
    public static ACC_WITH_CHANGE_STATUS = 'ACC_WITH_CHANGE_STATUS';
    public static WITHOUT_DECISION = 'WITHOUT_DECISION';

}

export class ApprovPec {

    public static APPROVE = 'APPROVE';
    public static APPROUVE_WITH_MODIFICATION = 'APPROVE_WITH_MODIFICATION';
}

export class Motifs {

    public static DECISION_PEC = 'DECISION_PEC';
}
export class EtatMotifs {

    public static ACCEPT_WITH_RESERVE = 'ACCEPT_WITH_RESERVE';
    public static CANCELED = 'CANCELED';
    public static ACC_WITH_CHANGE_STATUS = 'ACC_WITH_CHANGE_STATUS';
    public static REFUSED = 'REFUSED';
}

export class EtatMotifsId {

    public static ACCEPT_WITH_RESERVE = 1;
    public static CANCELED = 2;
    public static ACC_WITH_CHANGE_STATUS = 3;
    public static REFUSED = 4;
}


export class TypeInterventionQuotation {

    public static INTERVENTION_MO = 1;
    public static INTERVENTION_PEINTURE = 2;
    public static INTERVENTION_FOURNITURE = 3;
}

export class EtatAccord {

    public static ACC_A_APPROUV = 0;
    public static ACC_FAVORABLE = 1;
    public static ACC_DEFAVORABLE = 2;
    public static ACC_VALIDE_PART_ASSURE = 3;

    public static ACC_FAVORABLE_AVEC_MODIF = 4;
    public static ACC_FAVORABLE_AVEC_RESERV = 5;
    public static ACC_A_VALIDER = 6;
    public static ACC_A_VALIDER_PART_ASSURE = 7;
    public static ACC_NON_VALIDER = 8;
    public static ACC_SIGNATURE_ACCORD = 9;
    public static ACC_IMPRIMER = 9;
    public static ACC_WITH_GENERIC_PIECE = 11;
    public static ACC_INSTANCE_REPARATION = 10;
    public static MODIFICATION_PRIX = 12 ;
    public static BON_SORTIE = 13 ;
    public static ACC_REFUSED = 14;


}
  

export class DecisionAccord {

    public static ACC_A_APPROUV = 0;
    public static ACC_FAVORABLE = 1;
    public static ACC_DEFAVORABLE = 2;
    public static ACC_FAVORABLE_AVEC_MODIF = 4;
    public static ACC_FAVORABLE_AVEC_RESERV = 5;
}
export class DecisionAssure {

    public static ACC_ACCEPTED = 1;
    public static ACC_ACCEPTED_WITH_GENERIQ_PIECE = 2;
    public static ACC_REFUSED = 3;
    public static ACC_INSTANCE_CONFIRMATION = 4;

}
export class ObservationAccord {

    public static ACC_CHANGEMENT_TVA = 1;
    public static ACC_CHANGEMENT_RP = 2;

}
export class NotificationAccord {

    public static NOTIF_ACCORD_FAVORABLE = 'NOTIF_ACCORD_FAVORABLE';
    public static NOTIF_ACCORD_FAVORABLE_WITH_MODIF = 'NOTIF_ACCORD_FAVORABLE_WITH_MODIF';
    public static NOTIF_ACCORD_FAVORABLE_WITH_RESERV = 'NOTIF_ACCORD_FAVORABLE_WITH_RESERV';
    public static NOTIF_ACCORD_DEFAVORABLE = 'NOTIF_ACCORD_DEFAVORABLE';
    public static NOTIF_ACCORD_VALIDE = 'NOTIF_ACCORD_VALIDE';
    public static NOTIF_NON_ACCORD_VALIDE = 'NOTIF_NON_ACCORD_VALIDE';

    public static NOTIF_ASSURE_ACCEPTED = 'NOTIF_ASSURE_ACCEPTED';
    public static NOTIF_ASSURE_REFUSED = 'NOTIF_ASSURE_REFUSED';
    public static NOTIF_ASSURE_ACCEPTED_WITH_P_GEN = 'NOTIF_ASSURE_ACCEPTED_WITH_P_GEN';
    public static NOTIF_ASSURE_INSTANCE_CONFIRM = 'NOTIF_ASSURE_INSTANCE_CONFIRM';
}


