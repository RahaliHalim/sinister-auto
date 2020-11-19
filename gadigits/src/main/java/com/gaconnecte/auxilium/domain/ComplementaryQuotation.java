package com.gaconnecte.auxilium.domain;

import javax.persistence.*;


@Entity
@DiscriminatorValue("2")
public class ComplementaryQuotation extends Quotation {

}