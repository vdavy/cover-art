//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.5-2 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2012.07.29 à 11:54:51 AM CEST 
//


package com.stationmillenium.coverart.schema.lastfmtracksearch;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="track">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="mbid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                   &lt;element name="streamable" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>unsignedByte">
 *                           &lt;attribute name="fulltrack" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="listeners" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                   &lt;element name="playcount" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
 *                   &lt;element name="artist" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="mbid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="album" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="artist" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="mbid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="image" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="position" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="toptags" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="tag" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="wiki" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="published" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="summary" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="status" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "track"
})
@XmlRootElement(name = "lfm")
public class Lfm {

    @XmlElement(required = true)
    protected Lfm.Track track;
    @XmlAttribute(name = "status")
    protected String status;

    /**
     * Obtient la valeur de la propriété track.
     * 
     * @return
     *     possible object is
     *     {@link Lfm.Track }
     *     
     */
    public Lfm.Track getTrack() {
        return track;
    }

    /**
     * Définit la valeur de la propriété track.
     * 
     * @param value
     *     allowed object is
     *     {@link Lfm.Track }
     *     
     */
    public void setTrack(Lfm.Track value) {
        this.track = value;
    }

    /**
     * Obtient la valeur de la propriété status.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Définit la valeur de la propriété status.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
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
     *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="mbid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *         &lt;element name="streamable" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>unsignedByte">
     *                 &lt;attribute name="fulltrack" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="listeners" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *         &lt;element name="playcount" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/>
     *         &lt;element name="artist" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="mbid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="album" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="artist" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="mbid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="image" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="position" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="toptags" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="tag" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="wiki" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="published" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="summary" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "id",
        "name",
        "mbid",
        "url",
        "duration",
        "streamable",
        "listeners",
        "playcount",
        "artist",
        "album",
        "toptags",
        "wiki"
    })
    public static class Track {

        @XmlSchemaType(name = "unsignedInt")
        protected Long id;
        protected String name;
        protected String mbid;
        protected String url;
        @XmlSchemaType(name = "unsignedInt")
        protected Long duration;
        protected Lfm.Track.Streamable streamable;
        @XmlSchemaType(name = "unsignedInt")
        protected Long listeners;
        @XmlSchemaType(name = "unsignedInt")
        protected Long playcount;
        protected Lfm.Track.Artist artist;
        protected Lfm.Track.Album album;
        protected Lfm.Track.Toptags toptags;
        protected Lfm.Track.Wiki wiki;

        /**
         * Obtient la valeur de la propriété id.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getId() {
            return id;
        }

        /**
         * Définit la valeur de la propriété id.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setId(Long value) {
            this.id = value;
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
         * Obtient la valeur de la propriété mbid.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMbid() {
            return mbid;
        }

        /**
         * Définit la valeur de la propriété mbid.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMbid(String value) {
            this.mbid = value;
        }

        /**
         * Obtient la valeur de la propriété url.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUrl() {
            return url;
        }

        /**
         * Définit la valeur de la propriété url.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUrl(String value) {
            this.url = value;
        }

        /**
         * Obtient la valeur de la propriété duration.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getDuration() {
            return duration;
        }

        /**
         * Définit la valeur de la propriété duration.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setDuration(Long value) {
            this.duration = value;
        }

        /**
         * Obtient la valeur de la propriété streamable.
         * 
         * @return
         *     possible object is
         *     {@link Lfm.Track.Streamable }
         *     
         */
        public Lfm.Track.Streamable getStreamable() {
            return streamable;
        }

        /**
         * Définit la valeur de la propriété streamable.
         * 
         * @param value
         *     allowed object is
         *     {@link Lfm.Track.Streamable }
         *     
         */
        public void setStreamable(Lfm.Track.Streamable value) {
            this.streamable = value;
        }

        /**
         * Obtient la valeur de la propriété listeners.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getListeners() {
            return listeners;
        }

        /**
         * Définit la valeur de la propriété listeners.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setListeners(Long value) {
            this.listeners = value;
        }

        /**
         * Obtient la valeur de la propriété playcount.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getPlaycount() {
            return playcount;
        }

        /**
         * Définit la valeur de la propriété playcount.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setPlaycount(Long value) {
            this.playcount = value;
        }

        /**
         * Obtient la valeur de la propriété artist.
         * 
         * @return
         *     possible object is
         *     {@link Lfm.Track.Artist }
         *     
         */
        public Lfm.Track.Artist getArtist() {
            return artist;
        }

        /**
         * Définit la valeur de la propriété artist.
         * 
         * @param value
         *     allowed object is
         *     {@link Lfm.Track.Artist }
         *     
         */
        public void setArtist(Lfm.Track.Artist value) {
            this.artist = value;
        }

        /**
         * Obtient la valeur de la propriété album.
         * 
         * @return
         *     possible object is
         *     {@link Lfm.Track.Album }
         *     
         */
        public Lfm.Track.Album getAlbum() {
            return album;
        }

        /**
         * Définit la valeur de la propriété album.
         * 
         * @param value
         *     allowed object is
         *     {@link Lfm.Track.Album }
         *     
         */
        public void setAlbum(Lfm.Track.Album value) {
            this.album = value;
        }

        /**
         * Obtient la valeur de la propriété toptags.
         * 
         * @return
         *     possible object is
         *     {@link Lfm.Track.Toptags }
         *     
         */
        public Lfm.Track.Toptags getToptags() {
            return toptags;
        }

        /**
         * Définit la valeur de la propriété toptags.
         * 
         * @param value
         *     allowed object is
         *     {@link Lfm.Track.Toptags }
         *     
         */
        public void setToptags(Lfm.Track.Toptags value) {
            this.toptags = value;
        }

        /**
         * Obtient la valeur de la propriété wiki.
         * 
         * @return
         *     possible object is
         *     {@link Lfm.Track.Wiki }
         *     
         */
        public Lfm.Track.Wiki getWiki() {
            return wiki;
        }

        /**
         * Définit la valeur de la propriété wiki.
         * 
         * @param value
         *     allowed object is
         *     {@link Lfm.Track.Wiki }
         *     
         */
        public void setWiki(Lfm.Track.Wiki value) {
            this.wiki = value;
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
         *         &lt;element name="artist" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="mbid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="image" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="position" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "artist",
            "title",
            "mbid",
            "url",
            "image"
        })
        public static class Album {

            protected String artist;
            protected String title;
            protected String mbid;
            protected String url;
            protected List<Lfm.Track.Album.Image> image;
            @XmlAttribute(name = "position")
            @XmlSchemaType(name = "unsignedByte")
            protected Short position;

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
             * Obtient la valeur de la propriété mbid.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMbid() {
                return mbid;
            }

            /**
             * Définit la valeur de la propriété mbid.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMbid(String value) {
                this.mbid = value;
            }

            /**
             * Obtient la valeur de la propriété url.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUrl() {
                return url;
            }

            /**
             * Définit la valeur de la propriété url.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUrl(String value) {
                this.url = value;
            }

            /**
             * Gets the value of the image property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the image property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getImage().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Lfm.Track.Album.Image }
             * 
             * 
             */
            public List<Lfm.Track.Album.Image> getImage() {
                if (image == null) {
                    image = new ArrayList<Lfm.Track.Album.Image>();
                }
                return this.image;
            }

            /**
             * Obtient la valeur de la propriété position.
             * 
             * @return
             *     possible object is
             *     {@link Short }
             *     
             */
            public Short getPosition() {
                return position;
            }

            /**
             * Définit la valeur de la propriété position.
             * 
             * @param value
             *     allowed object is
             *     {@link Short }
             *     
             */
            public void setPosition(Short value) {
                this.position = value;
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
             *       &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}string" />
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
            public static class Image {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "size")
                protected String size;

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
                 * Obtient la valeur de la propriété size.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSize() {
                    return size;
                }

                /**
                 * Définit la valeur de la propriété size.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSize(String value) {
                    this.size = value;
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
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="mbid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "name",
            "mbid",
            "url"
        })
        public static class Artist {

            protected String name;
            protected String mbid;
            protected String url;

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
             * Obtient la valeur de la propriété mbid.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMbid() {
                return mbid;
            }

            /**
             * Définit la valeur de la propriété mbid.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMbid(String value) {
                this.mbid = value;
            }

            /**
             * Obtient la valeur de la propriété url.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUrl() {
                return url;
            }

            /**
             * Définit la valeur de la propriété url.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUrl(String value) {
                this.url = value;
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
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>unsignedByte">
         *       &lt;attribute name="fulltrack" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" />
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
        public static class Streamable {

            @XmlValue
            @XmlSchemaType(name = "unsignedByte")
            protected short value;
            @XmlAttribute(name = "fulltrack")
            @XmlSchemaType(name = "unsignedByte")
            protected Short fulltrack;

            /**
             * Obtient la valeur de la propriété value.
             * 
             */
            public short getValue() {
                return value;
            }

            /**
             * Définit la valeur de la propriété value.
             * 
             */
            public void setValue(short value) {
                this.value = value;
            }

            /**
             * Obtient la valeur de la propriété fulltrack.
             * 
             * @return
             *     possible object is
             *     {@link Short }
             *     
             */
            public Short getFulltrack() {
                return fulltrack;
            }

            /**
             * Définit la valeur de la propriété fulltrack.
             * 
             * @param value
             *     allowed object is
             *     {@link Short }
             *     
             */
            public void setFulltrack(Short value) {
                this.fulltrack = value;
            }

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
         *         &lt;element name="tag" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "tag"
        })
        public static class Toptags {

            protected List<Lfm.Track.Toptags.Tag> tag;

            /**
             * Gets the value of the tag property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the tag property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getTag().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Lfm.Track.Toptags.Tag }
             * 
             * 
             */
            public List<Lfm.Track.Toptags.Tag> getTag() {
                if (tag == null) {
                    tag = new ArrayList<Lfm.Track.Toptags.Tag>();
                }
                return this.tag;
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
             *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                "name",
                "url"
            })
            public static class Tag {

                protected String name;
                protected String url;

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
                 * Obtient la valeur de la propriété url.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getUrl() {
                    return url;
                }

                /**
                 * Définit la valeur de la propriété url.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setUrl(String value) {
                    this.url = value;
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
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="published" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="summary" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "published",
            "summary",
            "content"
        })
        public static class Wiki {

            protected String published;
            protected String summary;
            protected String content;

            /**
             * Obtient la valeur de la propriété published.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPublished() {
                return published;
            }

            /**
             * Définit la valeur de la propriété published.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPublished(String value) {
                this.published = value;
            }

            /**
             * Obtient la valeur de la propriété summary.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSummary() {
                return summary;
            }

            /**
             * Définit la valeur de la propriété summary.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSummary(String value) {
                this.summary = value;
            }

            /**
             * Obtient la valeur de la propriété content.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getContent() {
                return content;
            }

            /**
             * Définit la valeur de la propriété content.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setContent(String value) {
                this.content = value;
            }

        }

    }

}
