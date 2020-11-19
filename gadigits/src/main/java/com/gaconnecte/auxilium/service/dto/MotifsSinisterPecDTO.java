package com.gaconnecte.auxilium.service.dto;

import java.io.Serializable;

public class MotifsSinisterPecDTO implements Serializable {
	
  private Long id;	
  private Long sinisterPecId;
  private Long refMotifId;
  private Long refModeGestionId;

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Long getSinisterPecId() {
    return sinisterPecId;
  }
  public void setSinisterPecId(Long sinisterPecId) {
    this.sinisterPecId = sinisterPecId;
  }
  public Long getRefMotifId() {
    return refMotifId;
  }
  public void setRefMotifId(Long refMotifId) {
    this.refMotifId = refMotifId;
  }
  public Long getRefModeGestionId() {
    return refModeGestionId;
  }
  public void setRefModeGestionId(Long refModeGestionId) {
    this.refModeGestionId = refModeGestionId;
  }

}