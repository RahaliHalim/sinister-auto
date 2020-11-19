package com.gaconnecte.auxilium.service.impl;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.drools.FactDevis;
import com.gaconnecte.auxilium.repository.DetailsPiecesRepository;
import com.gaconnecte.auxilium.repository.QuotationRepository;
import com.gaconnecte.auxilium.repository.SinisterRepository;
import com.gaconnecte.auxilium.repository.AssureRepository;
import com.gaconnecte.auxilium.service.DroolsService;
import com.gaconnecte.auxilium.service.prm.PartnerRulesService;
import com.gaconnecte.auxilium.service.prm.dto.PartnerRulesDTO;
import com.gaconnecte.auxilium.service.dto.ComplementaryQuotationDTO;
import com.gaconnecte.auxilium.service.dto.SinisterPecDTO;
import com.gaconnecte.auxilium.web.rest.ReportResource;
import com.gaconnecte.auxilium.domain.Quotation;
import com.gaconnecte.auxilium.domain.VehiculeAssure;
import com.gaconnecte.auxilium.domain.app.Sinister;
import com.gaconnecte.auxilium.domain.Assure;
import com.gaconnecte.auxilium.service.QuotationService;
import com.gaconnecte.auxilium.service.SinisterPecService;

@Service
@Transactional
public class DroolsServiceImpl implements DroolsService {

	private final Logger log = LoggerFactory.getLogger(ReportResource.class);

	
	@Autowired
	private KieBase kieBase;

	
	@Autowired
	private DetailsPiecesRepository detailPieceRepository;
	@Autowired
	private PartnerRulesService partnerRulesService;
	@Autowired
	private QuotationRepository quotationRepository;
	@Autowired
	private SinisterRepository sinisterRepository;
	@Autowired
	private AssureRepository assureRepository;
	@Autowired
	private QuotationService quotationService;

	@Autowired
	private SinisterPecService sinisterPecService;

	/**
	 * Affectation data to Drools
	 */

	public FactDevis getDataFromDevis(SinisterPecDTO accordPriseChargeDTO, Long id) {

		FactDevis factDevis = new FactDevis();

		if (accordPriseChargeDTO != null) {

			Float ttcMo = 0f;
			Float ttcPr = 0f;
			Float ttcPf = 0f;
			Float ttcPi = 0f;
			Float thtMo = 0f;
			Float thtPr = 0f;
			Float thtPf = 0f;
			Float thtPi = 0f;
			Float droitTimbre = 0f;
			Double sommeDevis = 0D;
			Double sommeDevisTotale = 0D;
			Float ttcComplemnetary = 0F;
			Double diffCapSommComp = 0D;

			PartnerRulesDTO partnerRulesDTO = partnerRulesService.findPartnerRulesByPartnerAndMode(
					accordPriseChargeDTO.getPartnerId(), accordPriseChargeDTO.getModeId());
			Quotation quotation = quotationRepository.findOne(id);

			Long typeDevis = quotationService.getQuotationType(id);

			if (typeDevis == 2) {
				if (quotationRepository.findTtcComplementary(accordPriseChargeDTO.getId(), id) == null) {
					ttcComplemnetary = 0F;
				} else {
					ttcComplemnetary = quotationRepository.findTtcComplementary(accordPriseChargeDTO.getId(), id);
				}
			}

			SinisterPecDTO sinisterPecDTO = sinisterPecService.findOne(accordPriseChargeDTO.getId());
			if (sinisterPecDTO.getRemainingCapital() != null) {

				for (ComplementaryQuotationDTO comp : sinisterPecDTO.getListComplementaryQuotation()) {
					if (!comp.getStatusId().equals(4L)) {
						sommeDevis = sommeDevis + comp.getTtcAmount() - comp.getStampDuty();
					}
				}
				Quotation quotationInitiale = quotationRepository.findOne(sinisterPecDTO.getPrimaryQuotationId());
				sommeDevis = sommeDevis + quotationInitiale.getTtcAmount() - quotationInitiale.getStampDuty();
				if (typeDevis == 2) {
					sommeDevisTotale = sommeDevis + quotation.getTtcAmount() - quotation.getStampDuty();
				}
				diffCapSommComp = sinisterPecDTO.getRemainingCapital().doubleValue() - sommeDevis.doubleValue();
				if (diffCapSommComp < 0) {
					diffCapSommComp = 0D;
				}
			}

			if (accordPriseChargeDTO.getPartnerId() != null) {
				factDevis.setCompagnieId(accordPriseChargeDTO.getPartnerId());
			}
			factDevis.setModeId(accordPriseChargeDTO.getModeId());
			if (accordPriseChargeDTO.getResponsabiliteX() == null) {
				factDevis.setResponsabilite(0F);
			} else {
				factDevis.setResponsabilite(
						Float.parseFloat(accordPriseChargeDTO.getResponsabiliteX().toString()) / 100);
			}
			if (accordPriseChargeDTO.getValeurNeuf() == null) {
				factDevis.setValeurNeuf(0D);
			} else {
				factDevis.setValeurNeuf(accordPriseChargeDTO.getValeurNeuf());
			}
			if (accordPriseChargeDTO.getMarketValue() == null) {
				factDevis.setValeurVulnerable(0D);
			} else {
				factDevis.setValeurVulnerable(accordPriseChargeDTO.getMarketValue());
			}
			if (accordPriseChargeDTO.getPartnerId() != null) {
				factDevis.setCompagnieId(accordPriseChargeDTO.getPartnerId());
			}
			if (accordPriseChargeDTO.getFranchise() == null) {
				factDevis.setFranchise(0F);
			} else {
				factDevis.setFranchise(Float.parseFloat(accordPriseChargeDTO.getFranchise().toString()));
			}

			if (accordPriseChargeDTO.getSinisterId() != null) {

				Sinister sinister = sinisterRepository.findOne(accordPriseChargeDTO.getSinisterId());


				//Assure assure = assureRepository.findOne(sinister.getVehicle().getInsured().getId());
				
				VehiculeAssure vehiculeAssure = sinister.getVehicle();

				if (vehiculeAssure.getAssujettieTVA() != null) {
					if (vehiculeAssure.getAssujettieTVA().equals(false)) {
						factDevis.setIsAssujettie(false);
					}
					if (vehiculeAssure.getAssujettieTVA().equals(true)) {
						factDevis.setIsAssujettie(true);
					}
				} else {
					factDevis.setIsAssujettie(false);
				}

				if (vehiculeAssure.getAssujettieTVA() != null) {
					// vetusté if not assujetti tva
					if (vehiculeAssure.getAssujettieTVA().equals(false)) {
						if (partnerRulesDTO.getVetuste() != null) {
							if (partnerRulesDTO.getVetuste()) {
								if (detailPieceRepository.sumVetusteHtByDevis(id) == null) {
									factDevis.setVetuste(0F);
								} else {
									factDevis.setVetuste(detailPieceRepository.sumVetusteHtByDevis(id) * 1.19F);
								}
							} else {
								factDevis.setVetuste(0F);
							}
						}
					}
					// vetusté if assujetti tva
					if (vehiculeAssure.getAssujettieTVA().equals(true)) {
						if (partnerRulesDTO.getVetuste() != null) {
							if (partnerRulesDTO.getVetuste()) {
								if (detailPieceRepository.sumVetusteHtByDevis(id) == null) {
									factDevis.setVetuste(0F);
								} else {
									factDevis.setVetuste(detailPieceRepository.sumVetusteHtByDevis(id));
								}
							} else {
								factDevis.setVetuste(0F);
							}
						}
					}
				} else {
					factDevis.setVetuste(0F);
				}
			}

			if (accordPriseChargeDTO.getInsuredCapital() == null) {
				factDevis.setCapitalAssuree(0D);
			} else {
				factDevis.setCapitalAssuree(Double.parseDouble(accordPriseChargeDTO.getInsuredCapital().toString()));
			}
			if (typeDevis == 1) {
				if (accordPriseChargeDTO.getRemainingCapital() == null) {
					factDevis.setCapitalRestant(0D);
				} else {
					factDevis.setCapitalRestant(
							Double.parseDouble(accordPriseChargeDTO.getRemainingCapital().toString()));

				}
			}
			if (typeDevis == 2) {
				if (accordPriseChargeDTO.getRemainingCapital() == null) {
					factDevis.setCapitalRestant(0D);
				} else {
					factDevis.setCapitalRestant(
							Double.parseDouble(accordPriseChargeDTO.getCapitalRestantAfterComp().toString()));

				}
			}
			if (accordPriseChargeDTO.getValeurNeuf() == null) {
				factDevis.setValeurNeuf(0D);
			} else {
				factDevis.setValeurNeuf(accordPriseChargeDTO.getValeurNeuf());
			}
			if (accordPriseChargeDTO.getMarketValue() == null) {
				factDevis.setValeurVulnerable(0D);
			} else {
				factDevis.setValeurVulnerable(accordPriseChargeDTO.getMarketValue());
			}
			if (quotationRepository.findStampDutyByQuotation(id) == null) {

				factDevis.setDroitTimbre(0F);
				droitTimbre = 0F;
			} else if (typeDevis == 1) {

				factDevis.setDroitTimbre(quotationRepository.findStampDutyByQuotation(id));
				droitTimbre = quotationRepository.findStampDutyByQuotation(id);
			} else if (typeDevis == 2) {
				droitTimbre = 0F;
				factDevis.setDroitTimbre(0F);
			}
			// ttc pieces MO
			if (detailPieceRepository.sumTtcPrByDevis(id, 1L, true) == null) {
				ttcMo = 0f;
			} else {
				ttcMo = detailPieceRepository.sumTtcPrByDevis(id, 1L, true);
			}
			// ttc pieces ingredient
			if (detailPieceRepository.sumTtcPrByDevis(id, 2L, false) == null) {
				ttcPi = 0f;
			} else {
				ttcPi = detailPieceRepository.sumTtcPrByDevis(id, 2L, false);
			}
			// ttc pieces fourniture
			if (detailPieceRepository.sumTtcPrByDevis(id, 3L, false) == null) {
				ttcPf = 0f;
			} else {
				ttcPf = detailPieceRepository.sumTtcPrByDevis(id, 3L, false);
			}
			// ttc pieces de priéce
			if (detailPieceRepository.sumTtcPrByDevis(id, 1L, false) == null) {
				ttcPr = 0f;
			} else {
				ttcPr = detailPieceRepository.sumTtcPrByDevis(id, 1L, false);
			}
			// ht piece MO
			if (detailPieceRepository.sumThtPrByDevis(id, 1L, true) == null) {
				thtMo = 0f;
			} else {
				thtMo = detailPieceRepository.sumThtPrByDevis(id, 1L, true);
			}
			// ht piece fourniture
			if (detailPieceRepository.sumThtPrByDevis(id, 3L, false) == null) {
				thtPf = 0f;
			} else {
				thtPf = detailPieceRepository.sumThtPrByDevis(id, 3L, false);
			}
			// ht piece ingredient
			if (detailPieceRepository.sumThtPrByDevis(id, 2L, false) == null) {
				thtPi = 0f;
			} else {
				thtPi = detailPieceRepository.sumThtPrByDevis(id, 2L, false);
			}
			// ht piece de rechange
			if (detailPieceRepository.sumThtPrByDevis(id, 1L, false) == null) {
				thtPr = 0f;
			} else {
				thtPr = detailPieceRepository.sumThtPrByDevis(id, 1L, false);
			}

			Float Tht = thtMo + thtPf + thtPi + thtPr;
			Float Ttc = ttcMo + ttcPf + ttcPi + ttcPr;

			factDevis.setTotHt(Double.valueOf(Tht.toString()));
			factDevis.setTotalTtc(Double.valueOf(Ttc.toString()) + Double.valueOf(droitTimbre.toString()));
			factDevis.setTva(Double.valueOf(Ttc.toString()) - Double.valueOf(Tht.toString()));

			/************************************************
			 * FRAIS && AVANCE SUR FACTURE
			 ***************************************************/
			// MODE HIDA < 5000 OR HIDA > 5000
			// When Frais dossier
			if ((accordPriseChargeDTO.getModeId() == 3) || (accordPriseChargeDTO.getModeId() == 4)) {
				if (accordPriseChargeDTO.getModeId() == 3) { // HIDA < 5000
					if (typeDevis == 1) {
						if (partnerRulesDTO.getHidaServiceLowerId() != null
								&& partnerRulesDTO.getHidaServiceLowerId() == 6) { // HIDA TTC < 5000 : modified by
																					// Issam & Fathi 2000 ==> 5000
							factDevis.setFraisDossierInput(
									Float.parseFloat(partnerRulesDTO.getHidaServiceChargesLower().toString()));
						}
					}
					if (typeDevis == 2) {
						factDevis.setFraisDossierInput(0F);
					}
				}
				if (accordPriseChargeDTO.getModeId() == 4) { // HIDA > 5000
					if (partnerRulesDTO.getHidaServiceHigherId() != null
							&& partnerRulesDTO.getHidaServiceHigherId() == 8) { // HIDA TTC > 5000
						
						factDevis.setFraisDossierInput(
								((Float.parseFloat(partnerRulesDTO.getHidaServiceChargesHigher().toString())
										* Float.parseFloat(Ttc.toString()) / 100) * 1.19F)
										+ Float.parseFloat(droitTimbre.toString()));
					}
				}
				// When not Frais dossier
				if (partnerRulesDTO.getHidaServiceLowerId() == null && partnerRulesDTO.getHidaServiceBetweenId() == null
						&& partnerRulesDTO.getHidaServiceHigherId() == null) {
					factDevis.setFraisDossierInput(0F);
				}
				// When avance Facture
				if (partnerRulesDTO.getAdvanceOnInvoice() == true) {
					if (partnerRulesDTO.getAdvanceInvoiceHigher() == null) {
						factDevis.setAvance(0F);
					} else {
						factDevis.setAvance((Ttc + droitTimbre)
								* (Float.parseFloat(partnerRulesDTO.getAdvanceInvoiceHigher().toString()) / 100));
					}
				} else {
					factDevis.setAvance(0F);
				}
			}
			/************************************************
			 * END FRAIS && AVANCE SUR FACTURE
			 ***************************************************/

			/*************************************************
			 * FRANCHISE
			 ************************************************************************/

			if (partnerRulesDTO.getInsuranceDeductible() != null) {
				if (partnerRulesDTO.getInsuranceDeductible()) {
					if (partnerRulesDTO.getInsuranceDeductibleRuleId() == 4) { // F % VA
						if (typeDevis == 1) {// devis principale
							if (accordPriseChargeDTO.getModeId() == 7) { // Bris de glace
								if (accordPriseChargeDTO.getFranchiseTypeBgCapital() != null) {
									if (accordPriseChargeDTO.getFranchiseTypeBgCapital().equals("Pourcentage")) {

										factDevis.setFranchiseInput(Float
												.parseFloat(accordPriseChargeDTO.getBgCapitalFranchise().toString())
												* Float.parseFloat(accordPriseChargeDTO.getBgCapital().toString())
												/ 100);
									} else if (accordPriseChargeDTO.getFranchiseTypeBgCapital().equals("Montant")) {
										factDevis.setFranchiseInput(Float
												.parseFloat(accordPriseChargeDTO.getBgCapitalFranchise().toString()));
									}
								}

							} else if (accordPriseChargeDTO.getModeId() == 5) { // Dommage collision

								if (accordPriseChargeDTO.getFranchiseTypeDcCapital() != null) {
									if (accordPriseChargeDTO.getFranchiseTypeDcCapital().equals("Pourcentage")) {
										factDevis.setFranchiseInput(Float
												.parseFloat(accordPriseChargeDTO.getDcCapitalFranchise().toString())
												* Float.parseFloat(factDevis.getCapitalAssuree().toString()) / 100);
									} else if (accordPriseChargeDTO.getFranchiseTypeDcCapital().equals("Montant")) {
										factDevis.setFranchiseInput(Float
												.parseFloat(accordPriseChargeDTO.getDcCapitalFranchise().toString()));
									}
								}
							} else if (accordPriseChargeDTO.getModeId() == 6) { // Dommage vehicule
								if (accordPriseChargeDTO.getFranchiseTypeNewValue() != null) {
									if (accordPriseChargeDTO.getFranchiseTypeNewValue().equals("Pourcentage")) {
										
										factDevis.setFranchiseInput(Float.parseFloat(
												accordPriseChargeDTO.getNewValueVehicleFarnchise().toString())
												* Float.parseFloat(accordPriseChargeDTO.getValeurNeuf().toString())
												/ 100);
									} else if (accordPriseChargeDTO.getFranchiseTypeNewValue().equals("Montant")) {
										factDevis.setFranchiseInput(Float.parseFloat(
												accordPriseChargeDTO.getNewValueVehicleFarnchise().toString()));
									}
								}
							} else if (accordPriseChargeDTO.getModeId() == 8 || accordPriseChargeDTO.getModeId() == 9) { // Vol
																															// ou
																															// Incendie
								if (accordPriseChargeDTO.getFranchiseTypeMarketValue() != null) {
									if (accordPriseChargeDTO.getFranchiseTypeMarketValue().equals("Pourcentage")) {

										factDevis.setFranchiseInput(Float
												.parseFloat(accordPriseChargeDTO.getMarketValueFranchise().toString())
												* Float.parseFloat(accordPriseChargeDTO.getMarketValue().toString())
												/ 100);
									} else if (accordPriseChargeDTO.getFranchiseTypeMarketValue().equals("Montant")) {
										factDevis.setFranchiseInput(Float
												.parseFloat(accordPriseChargeDTO.getMarketValueFranchise().toString()));
									}
								}
							} else if (accordPriseChargeDTO.getModeId() == 10) { // Tierce
								if (accordPriseChargeDTO.getFranchiseTypeNewValue() != null) {
									if (accordPriseChargeDTO.getFranchiseTypeNewValue().equals("Pourcentage")) {
										
										factDevis.setFranchiseInput(Float.parseFloat(
												accordPriseChargeDTO.getNewValueVehicleFarnchise().toString())
												* Float.parseFloat(accordPriseChargeDTO.getValeurNeuf().toString())
												/ 100);
									} else if (accordPriseChargeDTO.getFranchiseTypeNewValue().equals("Montant")) {
										factDevis.setFranchiseInput(Float.parseFloat(
												accordPriseChargeDTO.getNewValueVehicleFarnchise().toString()));
									}
								}
							}
						}
						if (typeDevis == 2) {// devis complementaire
							factDevis.setFranchiseInput(0F);
						}
					}

					// END F % VA

					if (partnerRulesDTO.getInsuranceDeductibleRuleId() == 5) { // F % TR
						if (typeDevis == 1) {// devis principale
							if (accordPriseChargeDTO.getPartnerId() != 7L
									&& accordPriseChargeDTO.getPartnerId() != 4L) {
								if (partnerRulesDTO.getInsuranceDeductiblePercentage() != null
										&& partnerRulesDTO.getInsuranceDeductiblePercentage() >= 0) {
									factDevis.setFranchiseInput(Float
											.parseFloat(partnerRulesDTO.getInsuranceDeductiblePercentage().toString())
											* (Ttc) / 100);
								} else if ((partnerRulesDTO.getInsuranceDeductiblePercentage() == null
										|| partnerRulesDTO.getInsuranceDeductiblePercentage() == 0)
										&& (partnerRulesDTO.getInsuranceDeductibleFixed() != null
												|| partnerRulesDTO.getInsuranceDeductibleFixed() >= 0)) {
									factDevis.setFranchiseInput(
											Float.parseFloat(partnerRulesDTO.getInsuranceDeductibleFixed().toString()));
								}
							}

							if (accordPriseChargeDTO.getPartnerId() == 7L
									|| accordPriseChargeDTO.getPartnerId() == 4L) {
										
								if (accordPriseChargeDTO.getModeId().equals(5L)) {
									if (accordPriseChargeDTO.getFranchiseTypeDcCapital().equals("Pourcentage")) {
										factDevis.setFranchiseInput(Float
												.parseFloat(accordPriseChargeDTO.getDcCapitalFranchise().toString())
												* (Ttc) / 100);
									} else if (accordPriseChargeDTO.getFranchiseTypeDcCapital().equals("Montant")
											&& accordPriseChargeDTO.getDcCapitalFranchise() >= 0) {
										factDevis.setFranchiseInput(Float
												.parseFloat(accordPriseChargeDTO.getDcCapitalFranchise().toString()));
									}
								}

								if (accordPriseChargeDTO.getModeId().equals(7L)) {
									if (accordPriseChargeDTO.getFranchiseTypeBgCapital().equals("Pourcentage")) {
										factDevis.setFranchiseInput(Float
												.parseFloat(accordPriseChargeDTO.getBgCapitalFranchise().toString())
												* (Ttc) / 100);
									} else if (accordPriseChargeDTO.getFranchiseTypeBgCapital().equals("Montant")
											&& accordPriseChargeDTO.getBgCapitalFranchise() >= 0) {
										factDevis.setFranchiseInput(Float
												.parseFloat(accordPriseChargeDTO.getBgCapitalFranchise().toString()));
									}
								}

							}
						}
						if (typeDevis == 2) {// devis complementaire

							if (Ttc < Float.parseFloat(factDevis.getCapitalRestant().toString())) {

								if (accordPriseChargeDTO.getPartnerId() != 7L
										&& accordPriseChargeDTO.getPartnerId() != 4L) {
									if (partnerRulesDTO.getInsuranceDeductiblePercentage() != null
											&& partnerRulesDTO.getInsuranceDeductiblePercentage() >= 0) {
										factDevis.setFranchiseInput(Float.parseFloat(
												partnerRulesDTO.getInsuranceDeductiblePercentage().toString()) * (Ttc)
												/ 100);
									} else if ((partnerRulesDTO.getInsuranceDeductiblePercentage() == null
											|| partnerRulesDTO.getInsuranceDeductiblePercentage() == 0)
											&& (partnerRulesDTO.getInsuranceDeductibleFixed() != null
													|| partnerRulesDTO.getInsuranceDeductibleFixed() >= 0)) {
										factDevis.setFranchiseInput(Float
												.parseFloat(partnerRulesDTO.getInsuranceDeductibleFixed().toString()));
									}
								}

								if (accordPriseChargeDTO.getPartnerId() == 7L
										|| accordPriseChargeDTO.getPartnerId() == 4L) {

									if (accordPriseChargeDTO.getModeId().equals(5L)) {
										if (accordPriseChargeDTO.getFranchiseTypeDcCapital().equals("Pourcentage")) {
											factDevis.setFranchiseInput(Float
													.parseFloat(accordPriseChargeDTO.getDcCapitalFranchise().toString())
													* (Ttc) / 100);
										} else if (accordPriseChargeDTO.getFranchiseTypeDcCapital().equals("Montant")
												&& accordPriseChargeDTO.getDcCapitalFranchise() >= 0) {
											factDevis.setFranchiseInput(0F/*
																			 * Float .parseFloat(accordPriseChargeDTO.
																			 * getDcCapitalFranchise().toString())
																			 */);
										}
									}

									if (accordPriseChargeDTO.getModeId().equals(7L)) {
										if (accordPriseChargeDTO.getFranchiseTypeBgCapital().equals("Pourcentage")) {
											factDevis.setFranchiseInput(Float
													.parseFloat(accordPriseChargeDTO.getBgCapitalFranchise().toString())
													* (Ttc) / 100);
										} else if (accordPriseChargeDTO.getFranchiseTypeBgCapital().equals("Montant")
												&& accordPriseChargeDTO.getBgCapitalFranchise() >= 0) {
											factDevis.setFranchiseInput(0F/*
																			 * Float .parseFloat(accordPriseChargeDTO.
																			 * getDcCapitalFranchise().toString())
																			 */);
										}
									}

								}
							} else if (Float.parseFloat(factDevis.getCapitalRestant().toString()) >= 0) {
								Float restantc = Float.parseFloat(factDevis.getCapitalRestant().toString());

								if (accordPriseChargeDTO.getPartnerId() != 7L
										&& accordPriseChargeDTO.getPartnerId() != 4L) {
									if (partnerRulesDTO.getInsuranceDeductiblePercentage() != null
											&& partnerRulesDTO.getInsuranceDeductiblePercentage() >= 0) {
										factDevis.setFranchiseInput(Float.parseFloat(
												partnerRulesDTO.getInsuranceDeductiblePercentage().toString())
												* (restantc) / 100);
									} else if ((partnerRulesDTO.getInsuranceDeductiblePercentage() == null
											|| partnerRulesDTO.getInsuranceDeductiblePercentage() == 0)
											&& (partnerRulesDTO.getInsuranceDeductibleFixed() != null
													|| partnerRulesDTO.getInsuranceDeductibleFixed() >= 0)) {
										factDevis.setFranchiseInput(Float
												.parseFloat(partnerRulesDTO.getInsuranceDeductibleFixed().toString()));
									}
								}

								if (accordPriseChargeDTO.getPartnerId() == 7L
										|| accordPriseChargeDTO.getPartnerId() == 4L) {

									if (accordPriseChargeDTO.getModeId().equals(5L)) {
										if (accordPriseChargeDTO.getFranchiseTypeDcCapital().equals("Pourcentage")) {
											factDevis.setFranchiseInput(Float
													.parseFloat(accordPriseChargeDTO.getDcCapitalFranchise().toString())
													* (Ttc) / 100);
										} else if (accordPriseChargeDTO.getFranchiseTypeDcCapital().equals("Montant")
												&& accordPriseChargeDTO.getDcCapitalFranchise() >= 0) {
											factDevis.setFranchiseInput(0F/*
																			 * Float .parseFloat(accordPriseChargeDTO.
																			 * getDcCapitalFranchise().toString())
																			 */);
										}
									}

									if (accordPriseChargeDTO.getModeId().equals(7L)) {
										if (accordPriseChargeDTO.getFranchiseTypeBgCapital().equals("Pourcentage")) {
											factDevis.setFranchiseInput(Float
													.parseFloat(accordPriseChargeDTO.getBgCapitalFranchise().toString())
													* (Ttc) / 100);
										} else if (accordPriseChargeDTO.getFranchiseTypeBgCapital().equals("Montant")
												&& accordPriseChargeDTO.getBgCapitalFranchise() >= 0) {
											factDevis.setFranchiseInput(0F/*
																			 * Float .parseFloat(accordPriseChargeDTO.
																			 * getDcCapitalFranchise().toString())
																			 */);
										}
									}

								}
							} else {
								factDevis.setFranchiseInput(0F);
							}
						}
					}

					// END F % TR

					if (partnerRulesDTO.getInsuranceDeductibleRuleId() == 11) { // F%*VA si TR >=VA ; F%*TR si TR<VA
																				// avec min XDTTC
						if (partnerRulesDTO.getInsuranceDeductiblePercentage() != null
								&& partnerRulesDTO.getInsuranceDeductiblePercentage() >= 0) {

							if (accordPriseChargeDTO.getModeId() == 5) {
								if ((Ttc + droitTimbre) >= Float.parseFloat(factDevis.getCapitalRestant().toString())) {
									if (typeDevis == 1) {// devis principale
										Float franchise = Float.parseFloat(
												partnerRulesDTO.getInsuranceDeductiblePercentage().toString())
												* Float.parseFloat(factDevis.getCapitalRestant().toString()) / 100;

										if (franchise < Float
												.parseFloat(partnerRulesDTO.getInsuranceDeductibleMin().toString())) {
											factDevis.setFranchiseInput(Float.parseFloat(
													partnerRulesDTO.getInsuranceDeductibleMin().toString()));

										} else {
											factDevis.setFranchiseInput(franchise);
										}
									}
									if (typeDevis == 2) { // devis complementaire
										Float franchise = Float.parseFloat(
												partnerRulesDTO.getInsuranceDeductiblePercentage().toString())
												* Float.parseFloat(diffCapSommComp.toString()) / 100;
										factDevis.setFranchiseInput(franchise);
										factDevis.setCapitalRestant(factDevis.getCapitalRestant() + franchise);
									}
								}
								if ((Ttc + droitTimbre) < Float.parseFloat(factDevis.getCapitalRestant().toString())) {
									if (typeDevis == 1) {// devis principale
										Float franchise = Float.parseFloat(
												partnerRulesDTO.getInsuranceDeductiblePercentage().toString()) * (Ttc)
												/ 100;

										if (franchise < Float
												.parseFloat(partnerRulesDTO.getInsuranceDeductibleMin().toString())) {
											factDevis.setFranchiseInput(Float.parseFloat(
													partnerRulesDTO.getInsuranceDeductibleMin().toString()));
											accordPriseChargeDTO.setUseMinFranchise(true);
										} else {
											factDevis.setFranchiseInput(franchise);
										}
									}
									if (typeDevis == 2) { // devis complementaire
										/*
										 * if (partnerRulesDTO.getInsuranceDeductiblePercentage() != null &&
										 * partnerRulesDTO.getInsuranceDeductiblePercentage() >= 0) {
										 */
										Boolean test = true;

										Float franchise = Float.parseFloat(
												partnerRulesDTO.getInsuranceDeductiblePercentage().toString()) * (Ttc)
												/ 100;
										if (test.equals(accordPriseChargeDTO.getUseMinFranchise())) {
											if (franchise < Float.parseFloat(
													partnerRulesDTO.getInsuranceDeductibleMin().toString())) {
												factDevis.setFranchiseInput(0F);
											} else {
												Float franchiseMin = Float.parseFloat(
														partnerRulesDTO.getInsuranceDeductiblePercentage().toString())
														* (sommeDevisTotale.floatValue()) / 100;
												factDevis.setFranchiseInput(franchiseMin
														- accordPriseChargeDTO.getTotaleFranchise().floatValue());
											}
										} else {
											factDevis.setFranchiseInput(franchise);
										}

										// }
										// }
										/*
										 * else if (Float.parseFloat(factDevis.getCapitalRestant().toString()) >= 0) {
										 * Float restantc = Float.parseFloat(factDevis.getCapitalRestant().toString());
										 * Float franchise = Float.parseFloat(
										 * partnerRulesDTO.getInsuranceDeductiblePercentage().toString()) (restantc) /
										 * 100;
										 * 
										 * 
										 * if (franchise < Float.parseFloat(
										 * partnerRulesDTO.getInsuranceDeductibleMin().toString())) {
										 * factDevis.setFranchiseInput(0F); } else {
										 * 
										 * factDevis.setFranchiseInput(franchise); } } else {
										 * factDevis.setFranchiseInput(0F); }
										 */
									}
								}
							}
							if (accordPriseChargeDTO.getModeId() == 7 && accordPriseChargeDTO.getPartnerId() == 5) {
								if ((Ttc + droitTimbre) >= Float.parseFloat(factDevis.getCapitalRestant().toString())) {
									if (typeDevis == 1) {// devis principale
										Float franchise = Float.parseFloat(
												partnerRulesDTO.getInsuranceDeductiblePercentage().toString())
												* Float.parseFloat(factDevis.getCapitalRestant().toString()) / 100;

										if (franchise < Float
												.parseFloat(partnerRulesDTO.getInsuranceDeductibleMin().toString())) {
											factDevis.setFranchiseInput(Float.parseFloat(
													partnerRulesDTO.getInsuranceDeductibleMin().toString()));
										} else {
											factDevis.setFranchiseInput(franchise);
										}
									}
									if (typeDevis == 2) {// devis complementaire
										Float franchise = Float.parseFloat(
												partnerRulesDTO.getInsuranceDeductiblePercentage().toString())
												* Float.parseFloat(diffCapSommComp.toString()) / 100;
										factDevis.setFranchiseInput(franchise);
										factDevis.setCapitalRestant(factDevis.getCapitalRestant() + franchise);
									}
								}
								if ((Ttc + droitTimbre) < Float.parseFloat(factDevis.getCapitalRestant().toString())) {
									if (typeDevis == 1) {// devis principale
										Float franchise = Float.parseFloat(
												partnerRulesDTO.getInsuranceDeductiblePercentage().toString()) * (Ttc)
												/ 100;

										if (franchise < Float
												.parseFloat(partnerRulesDTO.getInsuranceDeductibleMin().toString())) {
											factDevis.setFranchiseInput(Float.parseFloat(
													partnerRulesDTO.getInsuranceDeductibleMin().toString()));
											accordPriseChargeDTO.setUseMinFranchise(true);
										} else {
											factDevis.setFranchiseInput(franchise);
										}
									}
									if (typeDevis == 2) {// devis complementaire
										Boolean test = true;

										Float franchise = Float.parseFloat(
												partnerRulesDTO.getInsuranceDeductiblePercentage().toString()) * (Ttc)
												/ 100;

										if (test.equals(accordPriseChargeDTO.getUseMinFranchise())) {
											if (franchise < Float.parseFloat(
													partnerRulesDTO.getInsuranceDeductibleMin().toString())) {
												factDevis.setFranchiseInput(0F);
											} else {
												Float franchiseMin = Float.parseFloat(
														partnerRulesDTO.getInsuranceDeductiblePercentage().toString())
														* (sommeDevisTotale.floatValue()) / 100;
												factDevis.setFranchiseInput(franchiseMin
														- accordPriseChargeDTO.getTotaleFranchise().floatValue());
											}
										} else {
											factDevis.setFranchiseInput(franchise);
										}

									}
								}
							}
						}

					}
					// END F%*VA si TR >=VA ; F%*TR si TR<VA avec min XDTTC

					// Un Montant fixe= XDT
					if (partnerRulesDTO.getInsuranceDeductibleRuleId() == 12) {
						if (typeDevis == 1) {// devis principale
							factDevis.setFranchiseInput(
									Float.parseFloat(partnerRulesDTO.getInsuranceDeductibleFixed().toString()));
						}
						if (typeDevis == 2) {// devis complementaire
							factDevis.setFranchiseInput(0F);
						}
					}
					if (partnerRulesDTO.getInsuranceDeductibleRuleId() == 14) { // F%*VA si TR >=VA ; F%*TR si TR<VA ou
																				// montant fixe = XDTTC

						if (typeDevis == 1 || (typeDevis == 2
								&& (Ttc < Float.parseFloat(factDevis.getCapitalRestant().toString())))) {// devis
																											// principale
							if (accordPriseChargeDTO.getModeId() == 7 && accordPriseChargeDTO.getPartnerId() != 5) {// Bris
																													// de
																													// glace
								if (partnerRulesDTO.getInsuranceDeductiblePercentage() != null
										&& partnerRulesDTO.getInsuranceDeductiblePercentage() >= 0) {
									if ((Ttc + droitTimbre) >= Float
											.parseFloat(factDevis.getCapitalRestant().toString())) {
										Float franchise = Float.parseFloat(
												partnerRulesDTO.getInsuranceDeductiblePercentage().toString())
												* Float.parseFloat(factDevis.getCapitalRestant().toString()) / 100;
										factDevis.setFranchiseInput(franchise);
									}
									if ((Ttc + droitTimbre) < Float
											.parseFloat(factDevis.getCapitalRestant().toString())) {
										Float franchise = Float.parseFloat(
												partnerRulesDTO.getInsuranceDeductiblePercentage().toString()) * (Ttc)
												/ 100;
										factDevis.setFranchiseInput(franchise);
									}
								} else if ((partnerRulesDTO.getInsuranceDeductiblePercentage() == null
										|| partnerRulesDTO.getInsuranceDeductiblePercentage() == 0)
										&& (partnerRulesDTO.getInsuranceDeductibleFixed() != null
												|| partnerRulesDTO.getInsuranceDeductibleFixed() >= 0)) {
									factDevis.setFranchiseInput(
											Float.parseFloat(partnerRulesDTO.getInsuranceDeductibleFixed().toString()));
								}
							}
							if (accordPriseChargeDTO.getModeId() == 11) {// Tierce collision
								if (accordPriseChargeDTO.getFranchiseTypeDcCapital().equals("Pourcentage")) {
									if ((Ttc + droitTimbre) >= Float
											.parseFloat(factDevis.getCapitalRestant().toString())) {
										Float franchise = Float
												.parseFloat(accordPriseChargeDTO.getDcCapitalFranchise().toString())
												* Float.parseFloat(factDevis.getCapitalRestant().toString()) / 100;
										factDevis.setFranchiseInput(franchise);
									}
									if ((Ttc + droitTimbre) < Float
											.parseFloat(factDevis.getCapitalRestant().toString())) {
										Float franchise = Float
												.parseFloat(accordPriseChargeDTO.getDcCapitalFranchise().toString())
												* (Ttc) / 100;
										factDevis.setFranchiseInput(franchise);
									}
								} else if (accordPriseChargeDTO.getFranchiseTypeDcCapital().equals("Montant")
										&& accordPriseChargeDTO.getDcCapitalFranchise() >= 0) {
									factDevis.setFranchiseInput(
											Float.parseFloat(accordPriseChargeDTO.getDcCapitalFranchise().toString()));
									if (typeDevis == 2) {
										factDevis.setFranchiseInput(0F);
									}
								}
							}
						}
						if (typeDevis == 2) {
							if (Ttc > Float.parseFloat(factDevis.getCapitalRestant().toString())) {// devis
																									// complementaire

								if (Float.parseFloat(factDevis.getCapitalRestant().toString()) > 0) {
									Float restantc = Float.parseFloat(factDevis.getCapitalRestant().toString());

									if (accordPriseChargeDTO.getModeId() == 7
											&& accordPriseChargeDTO.getPartnerId() != 5) {// Bris de glace
										if (partnerRulesDTO.getInsuranceDeductiblePercentage() != null
												&& partnerRulesDTO.getInsuranceDeductiblePercentage() >= 0) {
											/*
											 * if ((restantc + droitTimbre) >= Float
											 * .parseFloat(factDevis.getCapitalRestant().toString())) {
											 */
											Float franchise = Float.parseFloat(
													partnerRulesDTO.getInsuranceDeductiblePercentage().toString())
													* Float.parseFloat(diffCapSommComp.toString()) / 100;
											factDevis.setFranchiseInput(franchise);
											factDevis.setCapitalRestant(factDevis.getCapitalRestant() + franchise);
											// }
											/*
											 * if ((restantc + droitTimbre) < Float
											 * .parseFloat(factDevis.getCapitalRestant().toString())) { Float franchise
											 * = Float.parseFloat(
											 * partnerRulesDTO.getInsuranceDeductiblePercentage().toString()) (restantc)
											 * / 100; factDevis.setFranchiseInput(franchise); }
											 */
										} else if ((partnerRulesDTO.getInsuranceDeductiblePercentage() == null
												|| partnerRulesDTO.getInsuranceDeductiblePercentage() == 0)
												&& (partnerRulesDTO.getInsuranceDeductibleFixed() != null
														|| partnerRulesDTO.getInsuranceDeductibleFixed() >= 0)) {
											factDevis.setFranchiseInput(Float.parseFloat(
													partnerRulesDTO.getInsuranceDeductibleFixed().toString()));
											factDevis.setCapitalRestant(
													factDevis.getCapitalRestant() + factDevis.getFranchise());
										}
									}
									if (accordPriseChargeDTO.getModeId() == 11) {// Tierce collision
										if (accordPriseChargeDTO.getFranchiseTypeDcCapital().equals("Pourcentage")) {
											/*
											 * if ((restantc + droitTimbre) >= Float
											 * .parseFloat(factDevis.getCapitalRestant().toString())) {
											 */
											Float franchise = Float
													.parseFloat(accordPriseChargeDTO.getDcCapitalFranchise().toString())
													* Float.parseFloat(diffCapSommComp.toString()) / 100;
											factDevis.setFranchiseInput(franchise);
											factDevis.setCapitalRestant(factDevis.getCapitalRestant() + franchise);
											// }
											/*
											 * if ((restantc + droitTimbre) < Float
											 * .parseFloat(factDevis.getCapitalRestant().toString())) { Float franchise
											 * = Float.parseFloat(
											 * accordPriseChargeDTO.getDcCapitalFranchise().toString()) (restantc) /
											 * 100; factDevis.setFranchiseInput(franchise); }
											 */
										} else if (accordPriseChargeDTO.getFranchiseTypeDcCapital().equals("Montant")
												&& accordPriseChargeDTO.getDcCapitalFranchise() >= 0) {
											factDevis.setFranchiseInput(0F/*
																			 * Float.parseFloat( accordPriseChargeDTO.
																			 * getDcCapitalFranchise().toString())
																			 */);
										}
									}

								} else {
									factDevis.setFranchiseInput(0F);
								}
							}
						}
					}
				} else {
					factDevis.setFranchiseInput(0F);
				}
			}

			/***************************** END FRANCHISE ******************/

			/***************************** DEPASSEMENT PLAFOND ******************/
			// MODE DOMMAGE COLLISION
			if (accordPriseChargeDTO.getModeId() == 5) {

				if (typeDevis == 1
						|| (typeDevis == 2 && Float.parseFloat(factDevis.getCapitalRestant().toString()) >= 0)) {
					factDevis.setDepassplafondInput(Float.valueOf(factDevis.getTotHt().toString())
							- Float.valueOf(factDevis.getCapitalRestant().toString())
							- Float.valueOf(factDevis.getVetuste().toString()));

					factDevis.setDepassplafondInputNonAssujettie(
							Ttc - Float.valueOf(factDevis.getCapitalRestant().toString())
									- Float.valueOf(factDevis.getVetuste().toString()));
				}

				if (typeDevis == 2) {
					
					if (Float.parseFloat(factDevis.getCapitalRestant().toString()) < 0) {
						Float depassPlacond = Float.parseFloat(factDevis.getCapitalRestant().toString()) - Ttc;
						factDevis.setDepassplafondInput(-depassPlacond);
						factDevis.setDepassplafondInputNonAssujettie(-depassPlacond);
					}
				}
			}

			// MODE TIERCE COLLISION
			if (accordPriseChargeDTO.getModeId() == 11) {

				if (typeDevis == 1
						|| (typeDevis == 2 && Float.parseFloat(factDevis.getCapitalRestant().toString()) >= 0)) {
					factDevis.setDepassplafondInput(Float.valueOf(factDevis.getTotHt().toString())
							- Float.valueOf(factDevis.getCapitalRestant().toString())
							- Float.valueOf(factDevis.getVetuste().toString()));

					factDevis.setDepassplafondInputNonAssujettie(
							Ttc - Float.valueOf(factDevis.getCapitalRestant().toString())
									- Float.valueOf(factDevis.getVetuste().toString()));

				}

				if (typeDevis == 2) {
					if (Float.parseFloat(factDevis.getCapitalRestant().toString()) < 0) {

						Float depassPlacond = Float.parseFloat(factDevis.getCapitalRestant().toString()) - Ttc;

						factDevis.setDepassplafondInput(-depassPlacond);
						factDevis.setDepassplafondInputNonAssujettie(-depassPlacond);
					}
				}
			}
			// MODE BRIS DE GLACE
			if (accordPriseChargeDTO.getModeId() == 7) {

				if (typeDevis == 1
						|| (typeDevis == 2 && Float.parseFloat(factDevis.getCapitalRestant().toString()) >= 0)) {
					factDevis.setDepassplafondInput(Float.valueOf(factDevis.getTotHt().toString())
							- Float.valueOf(factDevis.getCapitalRestant().toString()));
					factDevis.setDepassplafondInputNonAssujettie(
							Ttc - Float.valueOf(factDevis.getCapitalRestant().toString()));
				}

				if (typeDevis == 2) {
					if (Float.parseFloat(factDevis.getCapitalRestant().toString()) < 0) {

						Float depassPlacond = Float.parseFloat(factDevis.getCapitalRestant().toString()) - Ttc;

						factDevis.setDepassplafondInput(-depassPlacond);
						factDevis.setDepassplafondInputNonAssujettie(-depassPlacond);
					}
				}
			}
			/***************************** END DEPASSEMENT PLAFOND ******************/

			/***************************** REGLE PROPORTIONNE ******************/

			// MODE DOMMAGE VEHICLE OR TIERCE
			if (accordPriseChargeDTO.getModeId() == 6 || accordPriseChargeDTO.getModeId() == 10) { // à changer
																									// récupération de
																									// devis
				// RP DOMMAGE VEHICLE/TIERCE
				if (accordPriseChargeDTO.getValeurNeuf() != null && quotation.getPriceNewVehicle() != null
						&& quotation.getPriceNewVehicle() != 0D) {
					Float ins = Float.parseFloat(accordPriseChargeDTO.getValeurNeuf().toString());
					Float vn = Float.parseFloat(quotation.getPriceNewVehicle().toString());

					float rpdv = ins / vn;
					factDevis.setRpDV(new Float(rpdv));
					factDevis.setValeurNeufDOMV(quotation.getPriceNewVehicle());
				} else {
					factDevis.setRpDV(0F);
					factDevis.setValeurNeufDOMV(0D);
				}

				// REGLE PROPORTIONNEL DOMMAGE VEHICLE/TIERCE
				if (Float.parseFloat(quotation.getPriceNewVehicle().toString()) >= Float
						.parseFloat(accordPriseChargeDTO.getValeurNeuf().toString())) {
					factDevis.setRegleproporAssujettie((Float.valueOf(factDevis.getTotHt().toString())
							- Float.valueOf(factDevis.getVetuste().toString())) * (1F - factDevis.getRpDV()));

					factDevis.setRegleproporNonAssujettie(
							(Ttc - Float.valueOf(factDevis.getVetuste().toString())) * (1F - factDevis.getRpDV()));
				} else {
					factDevis.setRegleproporAssujettie(0F);
					factDevis.setRpDV(1F);
					factDevis.setRegleproporNonAssujettie(0F);
				}

			}

			// MODE VOL/INCENDIE
			if (accordPriseChargeDTO.getModeId() == 8 || accordPriseChargeDTO.getModeId() == 9) {
				// RP VOL/INCENDIE
				if (accordPriseChargeDTO.getMarketValue() != null && quotation.getMarketValue() != null
						&& quotation.getMarketValue() != 0D) {
					Float ins = Float.parseFloat(accordPriseChargeDTO.getMarketValue().toString());
					Float vn = Float.parseFloat(quotation.getMarketValue().toString());
					float rpdv = ins / vn;
					factDevis.setRpVI(new Float(rpdv));
					factDevis.setMarketValueVOLIN(quotation.getMarketValue());
				} else {
					factDevis.setRpVI(0F);
					factDevis.setMarketValueVOLIN(0D);
				}
				// REGLE PROPORTIONNEL VOL/INCENDIE
				/*
				 * factDevis.setRegleproporAssujettie((Float.valueOf(factDevis.getTotHt().
				 * toString()) - Float.valueOf(factDevis.getVetuste().toString())) * (1F -
				 * factDevis.getRpVI()));
				 * 
				 * factDevis.setRegleproporNonAssujettie( (Ttc -
				 * Float.valueOf(factDevis.getVetuste().toString())) * (1F -
				 * factDevis.getRpVI()));
				 */

				if (Float.parseFloat(quotation.getMarketValue().toString()) >= Float
						.parseFloat(accordPriseChargeDTO.getMarketValue().toString())) {
					factDevis.setRegleproporAssujettie((Float.valueOf(factDevis.getTotHt().toString())
							- Float.valueOf(factDevis.getVetuste().toString())) * (1F - factDevis.getRpVI()));

					factDevis.setRegleproporNonAssujettie(
							(Ttc - Float.valueOf(factDevis.getVetuste().toString())) * (1F - factDevis.getRpVI()));
				} else {
					factDevis.setRegleproporAssujettie(0F);
					factDevis.setRpVI(1F);
					factDevis.setRegleproporNonAssujettie(0F);
				}
			}

			// REGLE PROPORTIONNEL LA CARTE DOMMAGE COLLISION

			if (accordPriseChargeDTO.getPartnerId() == 3L && accordPriseChargeDTO.getModeId() == 5) {

				factDevis.setRegleproporAssujettieCarte(Float.valueOf(factDevis.getTotHt().toString())
						* Float.valueOf(accordPriseChargeDTO.getValeurAssure().toString())
						/ Float.valueOf(quotation.getPriceNewVehicle().toString()));
				factDevis.setRegleproporNonAssujettieCarte(
						Ttc * Float.valueOf(accordPriseChargeDTO.getValeurAssure().toString())
								/ Float.valueOf(quotation.getPriceNewVehicle().toString()));
				factDevis.setValeurNeufCarte(Float.valueOf(accordPriseChargeDTO.getValeurAssure().toString()));
				factDevis.setValeurNeufDOMV(quotation.getPriceNewVehicle());

				Float ins = Float.parseFloat(accordPriseChargeDTO.getValeurAssure().toString());
				Float vn = Float.parseFloat(quotation.getPriceNewVehicle().toString());

				float rpdv = ins / vn;
				factDevis.setRpDV(new Float(rpdv));

				if ((Float.parseFloat(quotation.getPriceNewVehicle().toString()) >= Float
						.parseFloat(accordPriseChargeDTO.getValeurAssure().toString())) && Float
						.parseFloat(accordPriseChargeDTO.getValeurAssure().toString()) < 50000F  ) {

					if (factDevis.getRegleproporAssujettieCarte() < Float
							.valueOf(factDevis.getCapitalRestant().toString())) {
						if (Float.valueOf(factDevis.getTotHt().toString()) < Float
								.valueOf(factDevis.getCapitalRestant().toString())) {
							factDevis.setRegleproporAssujettie((Float.valueOf(factDevis.getTotHt().toString())
									- Float.valueOf(factDevis.getVetuste().toString())) * (1F - factDevis.getRpDV()));
						} else {
							factDevis.setRegleproporAssujettie(Float.valueOf(factDevis.getCapitalRestant().toString())
									- (Float.valueOf(factDevis.getTotHt().toString())
											- Float.valueOf(factDevis.getVetuste().toString())) * factDevis.getRpDV());
						}

					} else {
						factDevis.setRegleproporAssujettie(0F);
					}
					if (factDevis.getRegleproporNonAssujettieCarte() < Float
							.valueOf(factDevis.getCapitalRestant().toString())) {
						if (Ttc < Float.valueOf(factDevis.getCapitalRestant().toString())) {
							factDevis.setRegleproporNonAssujettie(
									(Ttc - Float.valueOf(factDevis.getVetuste().toString()))
											* (1F - factDevis.getRpDV()));
						} else {
							factDevis.setRegleproporNonAssujettie(Float
									.valueOf(factDevis.getCapitalRestant().toString())
									- (Ttc - Float.valueOf(factDevis.getVetuste().toString())) * factDevis.getRpDV());
						}

					} else {
						factDevis.setRegleproporNonAssujettie(0F);
					}

				} else {
					factDevis.setRegleproporNonAssujettie(0F);
					factDevis.setRpDV(1F);
					factDevis.setRegleproporAssujettie(0F);
				}

			}
			sinisterPecService.save(accordPriseChargeDTO);

			/***************************** END REGLE PROPORTIONNE ******************/

			factDevis.setTtcDetailsMo(ttcMo);
			factDevis.setTtcPieceR(ttcPr);
			factDevis.setTtcPieceIP(ttcPf + ttcPi);

			return (getInsuredParticipation(factDevis));
		} else
			return factDevis;

	}

	public FactDevis getInsuredParticipation(FactDevis devis) {

		KieSession kieSession = kieBase.newKieSession();

		kieSession.insert(devis);

		for (FactHandle handle : kieSession.getFactHandles()) {

			if (kieSession.getObject(handle) instanceof FactDevis) {
				FactDevis d = (FactDevis) kieSession.getObject(handle);
			}

		}
		int ruleFiredCount = kieSession.fireAllRules();

		for (FactHandle handle : kieSession.getFactHandles()) {

			if (kieSession.getObject(handle) instanceof FactDevis) {
				FactDevis d = (FactDevis) kieSession.getObject(handle);
			}

		}


		return devis;
	}
}
