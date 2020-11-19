package com.gaconnecte.auxilium.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gaconnecte.auxilium.domain.Reparateur;
import com.gaconnecte.auxilium.domain.Loueur;
import com.gaconnecte.auxilium.domain.Partner;
import com.gaconnecte.auxilium.domain.RefRemorqueur;
import com.gaconnecte.auxilium.domain.VisAVis;
import com.gaconnecte.auxilium.repository.RefRemorqueurRepository;
import com.gaconnecte.auxilium.repository.search.RefRemorqueurSearchRepository;
import com.gaconnecte.auxilium.service.DelegationService;
import com.gaconnecte.auxilium.service.RefRemorqueurService;
import com.gaconnecte.auxilium.service.dto.DelegationDTO;
import com.gaconnecte.auxilium.service.dto.RefRemorqueurDTO;
import com.gaconnecte.auxilium.service.mapper.RefRemorqueurMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing RefRemorqueur.
 */
@Service
@Transactional
public class RefRemorqueurServiceImpl implements RefRemorqueurService {

    private final Logger log = LoggerFactory.getLogger(RefRemorqueurServiceImpl.class);

    private final RefRemorqueurRepository refRemorqueurRepository;

    private final RefRemorqueurMapper refRemorqueurMapper;

    private final RefRemorqueurSearchRepository refRemorqueurSearchRepository;

    @Autowired
    private RefRemorqueurService refRemorqueurService;
    
    private final DelegationService delegationService;

    public RefRemorqueurServiceImpl(RefRemorqueurRepository refRemorqueurRepository, RefRemorqueurMapper refRemorqueurMapper, 
            RefRemorqueurSearchRepository refRemorqueurSearchRepository, DelegationService delegationService) {
        this.refRemorqueurRepository = refRemorqueurRepository;
        this.refRemorqueurMapper = refRemorqueurMapper;
        this.refRemorqueurSearchRepository = refRemorqueurSearchRepository;
        this.delegationService = delegationService;
    }

    /**
     * Save a refRemorqueur.
     *
     * @param refRemorqueurDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RefRemorqueurDTO save(RefRemorqueurDTO refRemorqueurDTO) {
        log.debug("Request to save RefRemorqueur : {}", refRemorqueurDTO);
        RefRemorqueur refRemorqueur = refRemorqueurMapper.toEntity(refRemorqueurDTO);
        Partner partner = null;
        Reparateur reparateur = null;
        Loueur loueur = null;
        if (refRemorqueur.getVisAViss() != null) {
            for (VisAVis visAVis : refRemorqueur.getVisAViss()) {
                visAVis.setRemorqueur(refRemorqueur);
                visAVis.setPartner(partner);
                visAVis.setReparateur(reparateur);
                visAVis.setLoueur(loueur);
            }
        }

        refRemorqueur = refRemorqueurRepository.save(refRemorqueur);
        RefRemorqueurDTO result = refRemorqueurMapper.toDto(refRemorqueur);
        //refRemorqueurSearchRepository.save(refRemorqueur);
        return result;
    }

    /**
     * Get all the refRemorqueurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RefRemorqueurDTO> findAll() {
        log.debug("Request to get all RefRemorqueurs");
        List<RefRemorqueurDTO> listRmqDTO = refRemorqueurRepository.findAllRemorqueur().stream().map(refRemorqueurMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));

        listRmqDTO.forEach(item -> {
            RefRemorqueur ref = refRemorqueurRepository.findOne(item.getId());
            if (ref.getSociete() != null && ref.getSociete().getVille() != null
                    && ref.getSociete().getVille().getGovernorate() != null) {
                item.setLibelleGouvernorat(ref.getSociete().getVille().getGovernorate().getLabel());
            }

        });
        return listRmqDTO;
    }

    /**
     * Get all the refRemorqueurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefRemorqueurDTO> findAllRemorqueur(Pageable pageable) {
        log.debug("Request to get all RefRemorqueurs");
        return refRemorqueurRepository.findAllRemorqueurnonbloque(pageable)
                .map(refRemorqueurMapper::toDto);
    }

    /**
     * Get all the refRemorqueurs for Bordereau .
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefRemorqueurDTO> findAllRemorqueurCloture(Pageable pageable) {
        log.debug("Request to get all RefRemorqueurs");
        return refRemorqueurRepository.findAllRemorqueurCloture(pageable)
                .map(refRemorqueurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RefRemorqueurDTO> findAllRemorqueurWithOrder(Long idVille, Pageable pageable) {
        log.debug("Request to get all RefRemorqueurs With Order");
        List<RefRemorqueurDTO> listRefRemorqueurDTO = StreamSupport
                .stream(refRemorqueurRepository.findAllRemorqueurnonbloque(pageable).spliterator(), false)
                .map(refRemorqueurMapper::toDto).collect(Collectors.toCollection(LinkedList::new));

        List<RefRemorqueurDTO> listeRemorqueursMemeVille = new ArrayList<>();
        List<RefRemorqueurDTO> listeRemorqueursMemeGovVilleDiff = new ArrayList<>();
        List<RefRemorqueurDTO> listeRemorqueursAutres = new ArrayList<>();

        DelegationDTO destVille = delegationService.findOne(idVille);

        for (RefRemorqueurDTO rem : listRefRemorqueurDTO) {
            DelegationDTO remVilleDto = delegationService.findOne(rem.getVilleId());
            if (remVilleDto == null) {
                listeRemorqueursAutres.add(rem);
            } else {
                if (remVilleDto.equals(destVille)) {
                    listeRemorqueursMemeVille.add(rem);
                } else {
                    if (!remVilleDto.equals(destVille) && remVilleDto.getGovernorateId().compareTo(destVille.getGovernorateId()) == 0) {
                        listeRemorqueursMemeGovVilleDiff.add(rem);
                    } else if (remVilleDto.getGovernorateId().compareTo(destVille.getGovernorateId()) != 0) {
                        listeRemorqueursAutres.add(rem);
                    }
                }
            }
        }

        listeRemorqueursMemeVille.sort(new Comparator<RefRemorqueurDTO>() {
            @Override
            public int compare(RefRemorqueurDTO r1, RefRemorqueurDTO r2) {
                Long nbr_affect1 = 0L;
                Long nbr_affect2 = 0L;
                DelegationDTO sysVilleDTO1 = delegationService.findOne(r1.getId());
                DelegationDTO sysVilleDTO2 = delegationService.findOne(r2.getId());

                if (sysVilleDTO1.getId().compareTo(idVille) == 0) {
                    if (sysVilleDTO1.getId().compareTo(sysVilleDTO2.getId()) == 0) {
                        if (r1.isHasHabillage().compareTo(true) == 0 && r1.isHasHabillage().compareTo(r2.isHasHabillage()) == 0) {
                            nbr_affect1 = refRemorqueurService.countDossierByRemorqueur(r1.getId());
                            nbr_affect2 = refRemorqueurService.countDossierByRemorqueur(r2.getId());
                            if (nbr_affect1.compareTo(nbr_affect2) == 0) {
                                return 0;
                            } else {
                                if (nbr_affect1.compareTo(nbr_affect2) < 0) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        } else {
                            if (r2.isHasHabillage().compareTo(true) == 0) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    } else {

                        return 1;
                    }
                } else {
                    return -1;
                }

            }
        });

        Collections.reverse(listeRemorqueursMemeVille);

        listRefRemorqueurDTO.clear();

        listRefRemorqueurDTO.addAll(listeRemorqueursMemeVille);
        listRefRemorqueurDTO.addAll(listeRemorqueursMemeGovVilleDiff);
        listRefRemorqueurDTO.addAll(listeRemorqueursAutres);

        return new PageImpl<>(listRefRemorqueurDTO, pageable, listRefRemorqueurDTO.size());

    }

    /**
     * Get one refRemorqueur by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RefRemorqueurDTO findOne(Long id) {
        log.debug("Request to get RefRemorqueur : {}", id);
        RefRemorqueur refRemorqueur = refRemorqueurRepository.findOne(id);
        return refRemorqueurMapper.toDto(refRemorqueur);
    }

    /**
     * Delete the refRemorqueur by id.
     *
     * @param id the id of the entity
     */
    @Override
    public Boolean delete(Long id) {
        log.debug("Request to delete RefRemorqueur : {}", id);
        RefRemorqueur refRemorqueur = refRemorqueurRepository.findOne(id);
        Boolean delete = validerDeleteRemorqueur(refRemorqueur);
        if (delete) {
            refRemorqueur.setIsDelete(Boolean.TRUE);
            refRemorqueur = refRemorqueurRepository.save(refRemorqueur);
            refRemorqueurSearchRepository.delete(id);
        }
        return delete;
    }

    /**
     * Search for the refRemorqueur corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RefRemorqueurDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RefRemorqueurs for query {}", query);
        Page<RefRemorqueur> result = refRemorqueurSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(refRemorqueurMapper::toDto);
    }

    /**
     * Block the refRemorqueur by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void bloque(Long id) {
        log.debug("Request to Blocke refRemorqueur : {}", id);
        RefRemorqueur refRemorqueur = refRemorqueurRepository.findOne(id);
        refRemorqueur.setIsBloque(!refRemorqueur.getIsBloque());
        refRemorqueurSearchRepository.save(refRemorqueur);

    }

    /**
     * valider supprission d'un refRemorqueur.
     *
     * @param refRemorqueur
     * @return valider delete
     */
    public Boolean validerDeleteRemorqueur(RefRemorqueur refRemorqueur) {
        Boolean deleteValide = Boolean.TRUE;
        // TODO: Correct this 
        Long NbrdossierRmq = 0L;//serviceRmqRepository.countDossierByRemorqueur(refRemorqueur.getId());
        if (NbrdossierRmq != 0) {
            deleteValide = Boolean.FALSE;
        }
        return deleteValide;
    }

    public Long countDossierByRemorqueur(Long id) {
        // TODO: Correct this 
        return 0L;//serviceRmqRepository.countDossierByRemorqueur(id);

    }

    public Double convertRad(Double input) {
        return ((Math.PI * input) / 180);
    }

    public Double distanceAssureRemorqueur(Double lat_a_degre, Double lon_a_degre, Double lat_b_degre, Double lon_b_degre) {
        Integer R = 6371;
        Double lat_a = convertRad(lat_a_degre);
        //Double lon_a= convertRad(lon_a_degre);
        Double lat_b = convertRad(lat_b_degre);
        //Double lon_b= convertRad(lon_b_degre);
        Double dlon = convertRad(lon_b_degre - lon_a_degre);
        Double d = R * (Math.acos(Math.sin(lat_b) * Math.sin(lat_a) + Math.cos(dlon) * Math.cos(lat_b) * Math.cos(lat_a)));
        return d;

    }

    @Override
    @Transactional(readOnly = true)
    public List<RefRemorqueurDTO> RechercheRemorqueurs(Double latitudeAssure, Double longitudeAssure) {
        List<RefRemorqueur> listesRemorqueurs = refRemorqueurRepository.findtoutRemorqueurnonbloque();
        List<RefRemorqueur> listesRemorqueursProches = new ArrayList<RefRemorqueur>();
        List<RefRemorqueur> listes3RemorqueursProches = new ArrayList<RefRemorqueur>();
        for (RefRemorqueur refRemorqueur : listesRemorqueurs) {
            if (refRemorqueur.getLatitude() != null && refRemorqueur.getLongitude() != null && refRemorqueur.getIsConnected() != null) {
                if (refRemorqueur.getIsConnected()) {
                    Double distance = distanceAssureRemorqueur(latitudeAssure, longitudeAssure, refRemorqueur.getLatitude(), refRemorqueur.getLongitude());
                    //System.out.println(distance);
                    //log.debug("distance", distance);
                    if (distance <= 15000) {
                        listesRemorqueursProches.add(refRemorqueur);
                    }
                }
            }
        }
        RefRemorqueur rmqTemp = new RefRemorqueur();
        Integer i = 0;
        Integer j = 0;
        for (i = 0; i < listesRemorqueursProches.size(); i++) {
            for (j = i; j < listesRemorqueursProches.size(); j++) {
                if (distanceAssureRemorqueur(latitudeAssure, longitudeAssure, listesRemorqueursProches.get(j).getLatitude(), listesRemorqueursProches.get(j).getLongitude()) < distanceAssureRemorqueur(latitudeAssure, longitudeAssure, listesRemorqueursProches.get(i).getLatitude(), listesRemorqueursProches.get(i).getLongitude())) {
                    rmqTemp = listesRemorqueursProches.get(i);
                    listesRemorqueursProches.set(i, listesRemorqueursProches.get(j));
                    listesRemorqueursProches.set(j, rmqTemp);
                }
            }
        }
        Integer k = 0;
        for (RefRemorqueur refRemorqueur : listesRemorqueursProches) {
            if (k < 3) {
                listes3RemorqueursProches.add(refRemorqueur);
                k = k + 1;
            }

        }

        return refRemorqueurMapper.toDto(listes3RemorqueursProches);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RefRemorqueurDTO> findTousRemorqueurNonBloque() {
        log.debug("Request to get all RefRemorqueurs");
        List<RefRemorqueur> listes = refRemorqueurRepository.findtoutRemorqueurnonbloque();
        return refRemorqueurMapper.toDto(listes);
    }

    @Override
    @Transactional(readOnly = true)
    public RefRemorqueurDTO findRefRemorqueurByMailAndMdp(String mail, String mdp) {
        log.debug("Request to get Assure : {}", mail, mdp);
        RefRemorqueur refRemorqueur = refRemorqueurRepository.findRemorqueurBymailAndMdp(mail, mdp);
        System.out.println(mail + "!!!!" + mdp);
        return refRemorqueurMapper.toDto(refRemorqueur);
    }

    @Override
    public RefRemorqueurDTO findRefRemorqueurByMail(String mail) {
        RefRemorqueur refRemorqueur = refRemorqueurRepository.findRefRemorqueurByMail(mail);
        return refRemorqueurMapper.toDto(refRemorqueur);
    }
    
    @Override
    public RefRemorqueurDTO findRefRemorqueurByDeviceId(String deviceId) {
        RefRemorqueur refRemorqueur = refRemorqueurRepository.findRefRemorqueurByDeviceId(deviceId);
        return refRemorqueurMapper.toDto(refRemorqueur);
    }

    @Override
    @Transactional(readOnly = true)
    public RefRemorqueurDTO getTugCompanyByName(String pname, String matriculeFiscale, String registreCommerce) {
        log.debug("Request to get RefRemorqueurDTO : {}", pname);
        RefRemorqueur refRemorqueur = refRemorqueurRepository.findRefRemorqueurByName(pname, matriculeFiscale, registreCommerce);
        if (refRemorqueur == null) {
            return null;
        }
        return refRemorqueurMapper.toDto(refRemorqueur);

    }

}
