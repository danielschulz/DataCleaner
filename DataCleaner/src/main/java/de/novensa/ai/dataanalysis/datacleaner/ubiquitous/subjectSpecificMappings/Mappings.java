package de.novensa.ai.dataanalysis.datacleaner.ubiquitous.subjectSpecificMappings;

/**
 * All medical mappings.
 *
 * @author Daniel Schulz
 */
public class Mappings extends PatientsHealthMapping {

    public static final String HEADER_ENDING = "patientId;" + "patientSex;" + "healthState;" +
            "HDL;" + "mediaType;" + "date;" + "time";

    public Mappings() {
    }
}
