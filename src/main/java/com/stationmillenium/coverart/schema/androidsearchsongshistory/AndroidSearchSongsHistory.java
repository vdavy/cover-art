//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.5-2 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2013.11.21 à 04:23:48 PM CET 
//


package com.stationmillenium.coverart.schema.androidsearchsongshistory;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="historySong">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="playedDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="artist" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="image" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;all>
 *                             &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="width" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="height" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/all>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/all>
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
    "historySong"
})
@XmlRootElement(name = "androidSearchSongsHistory")
public class AndroidSearchSongsHistory {

    protected List<AndroidSearchSongsHistory.HistorySong> historySong;

    /**
     * Gets the value of the historySong property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the historySong property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHistorySong().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AndroidSearchSongsHistory.HistorySong }
     * 
     * 
     */
    public List<AndroidSearchSongsHistory.HistorySong> getHistorySong() {
        if (historySong == null) {
            historySong = new ArrayList<AndroidSearchSongsHistory.HistorySong>();
        }
        return this.historySong;
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
     *       &lt;all>
     *         &lt;element name="playedDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="artist" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="image" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;all>
     *                   &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="width" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="height" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                 &lt;/all>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/all>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {

    })
    public static class HistorySong {

        @XmlElement(required = true)
        protected String playedDate;
        @XmlElement(required = true)
        protected String artist;
        @XmlElement(required = true)
        protected String title;
        protected AndroidSearchSongsHistory.HistorySong.Image image;

        /**
         * Obtient la valeur de la propriété playedDate.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPlayedDate() {
            return playedDate;
        }

        /**
         * Définit la valeur de la propriété playedDate.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPlayedDate(String value) {
            this.playedDate = value;
        }

        /**
         * Obtient la valeur de la propriété artist.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getArtist() {
            return artist;
        }

        /**
         * Définit la valeur de la propriété artist.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setArtist(String value) {
            this.artist = value;
        }

        /**
         * Obtient la valeur de la propriété title.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTitle() {
            return title;
        }

        /**
         * Définit la valeur de la propriété title.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTitle(String value) {
            this.title = value;
        }

        /**
         * Obtient la valeur de la propriété image.
         * 
         * @return
         *     possible object is
         *     {@link AndroidSearchSongsHistory.HistorySong.Image }
         *     
         */
        public AndroidSearchSongsHistory.HistorySong.Image getImage() {
            return image;
        }

        /**
         * Définit la valeur de la propriété image.
         * 
         * @param value
         *     allowed object is
         *     {@link AndroidSearchSongsHistory.HistorySong.Image }
         *     
         */
        public void setImage(AndroidSearchSongsHistory.HistorySong.Image value) {
            this.image = value;
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
         *       &lt;all>
         *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="width" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="height" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *       &lt;/all>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {

        })
        public static class Image {

            protected String path;
            protected String width;
            protected String height;

            /**
             * Obtient la valeur de la propriété path.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPath() {
                return path;
            }

            /**
             * Définit la valeur de la propriété path.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPath(String value) {
                this.path = value;
            }

            /**
             * Obtient la valeur de la propriété width.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getWidth() {
                return width;
            }

            /**
             * Définit la valeur de la propriété width.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setWidth(String value) {
                this.width = value;
            }

            /**
             * Obtient la valeur de la propriété height.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHeight() {
                return height;
            }

            /**
             * Définit la valeur de la propriété height.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHeight(String value) {
                this.height = value;
            }

        }

    }

}
