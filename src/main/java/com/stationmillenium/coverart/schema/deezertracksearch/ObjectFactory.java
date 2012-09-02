//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.5-2 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2012.08.29 à 07:21:18 PM CEST 
//


package com.stationmillenium.coverart.schema.deezertracksearch;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.stationmillenium.coverart.schema.deezertracksearch package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.stationmillenium.coverart.schema.deezertracksearch
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Root }
     * 
     */
    public Root createRoot() {
        return new Root();
    }

    /**
     * Create an instance of {@link Root.Data }
     * 
     */
    public Root.Data createRootData() {
        return new Root.Data();
    }

    /**
     * Create an instance of {@link Root.Data.Track }
     * 
     */
    public Root.Data.Track createRootDataTrack() {
        return new Root.Data.Track();
    }

    /**
     * Create an instance of {@link Root.Data.Track.Artist }
     * 
     */
    public Root.Data.Track.Artist createRootDataTrackArtist() {
        return new Root.Data.Track.Artist();
    }

    /**
     * Create an instance of {@link Root.Data.Track.Album }
     * 
     */
    public Root.Data.Track.Album createRootDataTrackAlbum() {
        return new Root.Data.Track.Album();
    }

}
