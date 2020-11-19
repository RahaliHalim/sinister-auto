package com.gaconnecte.auxilium.domain;

import javax.persistence.*;
import javax.persistence.DiscriminatorValue;


@Entity
@DiscriminatorValue("1")
public class PrimaryQuotation extends Quotation {

	
}