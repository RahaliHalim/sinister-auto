package com.gaconnecte.auxilium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Journal.
 */
@Entity
@Table(name = "journal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "journal")
public class Journal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 2000)
    @Column(name = "commentaire", length = 2000)
    private String commentaire;

    @NotNull
    @Column(name = "jhi_timestamp", nullable = false)
    private ZonedDateTime timestamp;

    @ManyToOne
    private SysActionUtilisateur sysActionUtilisateur;

    @Column(name = "utilisateur")
    private String utilisateur;

    @Column(name = "ipaddress")
    private String ipaddress;

    @Column(name = "reference")
    private String reference;

    @ManyToOne
    private Dossier dossier;

    @ManyToOne
    private PrestationPEC prestation;

    @ManyToOne
    private RefRemorqueur refRemorqueur;
    
    
      
    //@ManyToOne
    //private Quotation quotation;

    @ManyToOne
    private Reparateur reparateur;



    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "journal_motif",
            joinColumns = @JoinColumn(name = "journals_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "motifs_id", referencedColumnName = "id"))
    private Set<RefMotif> motifs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Journal commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public Journal timestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Journal utilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
        return this;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public Journal ipaddress(String ipaddress) {
        this.ipaddress = ipaddress;
        return this;
    }

    /*public Quotation getQuotation() {
        return quotation;
    }

    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
    }*/

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Journal reference(String reference) {

        this.reference = reference;

        return this;

    }
   

	public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Dossier getDossier() {
        return dossier;
    }

    public Journal dossier(Dossier dossier) {
        this.dossier = dossier;
        return this;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    public RefRemorqueur getRefRemorqueur() {
        return refRemorqueur;
    }

    public Journal refRemorqueur(RefRemorqueur refRemorqueur) {
        this.refRemorqueur = refRemorqueur;
        return this;
    }

    public void setRefRemorqueur(RefRemorqueur refRemorqueur) {
        this.refRemorqueur = refRemorqueur;
    }

    public PrestationPEC getPrestation() {
        return prestation;
    }

    public Journal prestation(PrestationPEC prestation) {
        this.prestation = prestation;
        return this;
    }

    public void setPrestation(PrestationPEC prestation) {
        this.prestation = prestation;
    }

    public SysActionUtilisateur getSysActionUtilisateur() {
        return sysActionUtilisateur;
    }

    public Journal sysActionUtilisateur(SysActionUtilisateur sysActionUtilisateur) {
        this.sysActionUtilisateur = sysActionUtilisateur;
        return this;
    }

    public void setSysActionUtilisateur(SysActionUtilisateur sysActionUtilisateur) {
        this.sysActionUtilisateur = sysActionUtilisateur;
    }

    public Set<RefMotif> getMotifs() {
        return motifs;
    }

    public Journal motifs(Set<RefMotif> refMotifs) {
        this.motifs = refMotifs;
        return this;
    }

    public Reparateur getReparateur() {
        return reparateur;
    }

    public void setReparateur(Reparateur reparateur) {
        this.reparateur = reparateur;
    }

    public Journal addMotif(RefMotif refMotif) {
        this.motifs.add(refMotif);
        refMotif.getJournals().add(this);
        return this;
    }

    public Journal removeMotif(RefMotif refMotif) {
        this.motifs.remove(refMotif);
        refMotif.getJournals().remove(this);
        return this;
    }

    public void setMotifs(Set<RefMotif> refMotifs) {
        this.motifs = refMotifs;
    }


	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Journal journal = (Journal) o;
        if (journal.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), journal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Journal{"
                + "id=" + id
                + ", commentaire='" + commentaire + '\''
                + ", timestamp=" + timestamp
                + ", utilisateur='" + utilisateur + '\''
                + ", ipaddress='" + ipaddress + '\''
                + '}';
    }
}
