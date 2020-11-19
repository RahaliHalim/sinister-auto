//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.06.21 à 08:29:11 AM CET 
//


package com.gaconnecte.auxilium.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="operation">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="userId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="entityName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="dateOperation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="fields">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="field" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="ancienVal" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="nouvVal" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "operation",
    "fields"
})
@XmlRootElement(name = "histories")
public class Histories {

    @XmlElement(required = true)
    protected Histories.Operation operation;
    @XmlElement(required = true)
    protected Histories.Fields fields;

    /**
     * Obtient la valeur de la propriété operation.
     * 
     * @return
     *     possible object is
     *     {@link Histories.Operation }
     *     
     */
    public Histories.Operation getOperation() {
        return operation;
    }

    /**
     * Définit la valeur de la propriété operation.
     * 
     * @param value
     *     allowed object is
     *     {@link Histories.Operation }
     *     
     */
    public void setOperation(Histories.Operation value) {
        this.operation = value;
    }

    /**
     * Obtient la valeur de la propriété fields.
     * 
     * @return
     *     possible object is
     *     {@link Histories.Fields }
     *     
     */
    public Histories.Fields getFields() {
        return fields;
    }

    /**
     * Définit la valeur de la propriété fields.
     * 
     * @param value
     *     allowed object is
     *     {@link Histories.Fields }
     *     
     */
    public void setFields(Histories.Fields value) {
        this.fields = value;
    }


    /**
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="field" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="ancienVal" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="nouvVal" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "field"
    })
    public static class Fields {

        protected List<Histories.Fields.Field> field;

        /**
         * Gets the value of the field property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the field property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getField().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Histories.Fields.Field }
         * 
         * 
         */
        
        public List<Histories.Fields.Field> getField() {
            if (field == null) {
                field = new ArrayList<Histories.Fields.Field>();
            }
            return this.field;
        }


        public void setField(List<Histories.Fields.Field> field) {
			this.field = field;
		}


		/**
         * <p>Classe Java pour anonymous complex type.
         * 
         * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="ancienVal" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="nouvVal" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Field {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "name")
            protected String name;
            @XmlAttribute(name = "ancienVal")
            protected String ancienVal;
            @XmlAttribute(name = "nouvVal")
            protected String nouvVal;

            /**
             * Obtient la valeur de la propriété value.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Définit la valeur de la propriété value.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Obtient la valeur de la propriété name.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getName() {
                return name;
            }

            /**
             * Définit la valeur de la propriété name.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * Obtient la valeur de la propriété ancienVal.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAncienVal() {
                return ancienVal;
            }

            /**
             * Définit la valeur de la propriété ancienVal.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAncienVal(String value) {
                this.ancienVal = value;
            }

            /**
             * Obtient la valeur de la propriété nouvVal.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNouvVal() {
                return nouvVal;
            }

            /**
             * Définit la valeur de la propriété nouvVal.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNouvVal(String value) {
                this.nouvVal = value;
            }

        }

    }


    /**
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="userId" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="entityName" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="dateOperation" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Operation {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "Name")
        protected String name;
        @XmlAttribute(name = "userId")
        protected Long userId;
        @XmlAttribute(name = "entityName")
        protected String entityName;
        @XmlAttribute(name = "dateOperation")
        protected  String dateOperation;

        /**
         * Obtient la valeur de la propriété value.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Définit la valeur de la propriété value.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Obtient la valeur de la propriété name.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Définit la valeur de la propriété name.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Obtient la valeur de la propriété userId.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public Long getUserId() {
            return userId;
        }

        /**
         * Définit la valeur de la propriété userId.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUserId(Long value) {
            this.userId = value;
        }

        /**
         * Obtient la valeur de la propriété entityName.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEntityName() {
            return entityName;
        }

        /**
         * Définit la valeur de la propriété entityName.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEntityName(String value) {
            this.entityName = value;
        }

        /**
         * Obtient la valeur de la propriété dateOperation.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDateOperation() {
            return dateOperation;
        }

        /**
         * Définit la valeur de la propriété dateOperation.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDateOperation(String value) {
            this.dateOperation = value;
        }

    }

}
