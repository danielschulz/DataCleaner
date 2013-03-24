package de.novensa.ai.dataanalysis.datacleaner.ubiquitous.subjectSpecificMappings;

import java.util.TreeMap;

/**
 * The subject´s mappings are placed here. This is the place for patients´ synonyms to identification mapping.
 *
 * @author Daniel Schulz
 */
public abstract class SynonymToPatientIdentificationMapping {

    private final TreeMap<String, String> specialSynonyms;

    public SynonymToPatientIdentificationMapping() {
        this.specialSynonyms = new TreeMap<String, String>();
        specialSynonyms.put("DAISEY", "DAISY");
        specialSynonyms.put("LILY", "LILLY");
    }

    public final String getPatientIdentificatorIgnoringSynonyms(String possiblyTheSynonym) {
        return this.specialSynonyms.get(possiblyTheSynonym);
    }

    public final boolean hasPatientIdentificatorIgnoringSynonyms(String possiblyTheSynonym) {
        return null != this.specialSynonyms.get(possiblyTheSynonym);
    }
}
