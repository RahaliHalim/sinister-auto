-- View: public.view_sinister_pec_monitoring

-- DROP VIEW public.view_sinister_pec_monitoring;

CREATE OR REPLACE VIEW public.view_sinister_pec_monitoring AS 
 SELECT pec.id,
    pec.reference AS reference_ga,
    pec.company_reference AS reference_cmp,
    partner.id AS partner_id,
    partner.company_name AS partner_name,
    agency.id AS agency_id,
    agency.code AS agency_code,
    agency.name AS agency_name,
    assure.full_name AS insured_name,
    sin.vehicle_id,
    veh.immatriculation_vehicule AS immatriculation,
    brand.id AS brand_id,
    brand.label AS brand_label,
    model.id AS model_id,
    model.label AS model_label,
    usage.id AS usage_id,
    usage.label AS usage_label,
    rep.raison_sociale AS reparator_rs,
    rep.is_cng AS cng,
    exp.raison_sociale AS expert_rs,
    pack.label AS pack_name,
    gov_ag.id AS agency_governorate_id,
    gov_ag.label AS agency_governorate_label,
    sin.incident_date,
    pec.declaration_date AS sending_date,
    pec.mode_id AS managment_mode_id,
    rmg.libelle AS managment_mode_label,
    pec.pos_ga_id AS ga_position_id,
    rpg.libelle AS ga_position_label,
    tiers.id AS tierse_partner_id,
    tierse_partner.company_name AS tierse_partner_name,
    pec.assigned_to_id,
    concat(assigned_to.first_name, ' ', assigned_to.last_name) AS assigned_to_name,
    pec.bareme_id,
    bareme.responsabilite_x AS bareme_rate
   FROM app_sinister_pec pec
     LEFT JOIN ref_mode_gestion rmg ON pec.mode_id = rmg.id
     LEFT JOIN ref_position_ga rpg ON pec.pos_ga_id = rpg.id
     LEFT JOIN ref_bareme bareme ON pec.bareme_id = bareme.id
     LEFT JOIN jhi_user assigned_to ON pec.assigned_to_id = assigned_to.id
     LEFT JOIN tiers ON pec.id = tiers.sinister_pec_id AND tiers.responsible = true
     LEFT JOIN ref_partner tierse_partner ON tiers.compagnie_id = tierse_partner.id
     JOIN app_sinister sin ON sin.id = pec.sinister_id
     LEFT JOIN vehicule_assure veh ON sin.vehicle_id = veh.id
     LEFT JOIN ref_vehicle_brand brand ON veh.marque_id = brand.id
     LEFT JOIN ref_vehicle_brand_model model ON veh.modele_id = model.id
     LEFT JOIN ref_vehicle_usage usage ON veh.usage_id = usage.id
     LEFT JOIN ref_partner partner ON sin.partner_id = partner.id
     LEFT JOIN contrat_assurance ctr ON sin.contract_id = ctr.id
     LEFT JOIN ref_pack pack ON ctr.pack_id = pack.id
     LEFT JOIN ref_agency agency ON ctr.agence_id = agency.id
     LEFT JOIN ref_governorate gov_ag ON agency.governorate_id = gov_ag.id
     LEFT JOIN assure ON ctr.assure_id = assure.id
     LEFT JOIN reparateur rep ON pec.reparateur_id = rep.id
     LEFT JOIN expert exp ON pec.expert_id = exp.id
  ORDER BY pec.id;

ALTER TABLE public.view_sinister_pec_monitoring
  OWNER TO postgres;
