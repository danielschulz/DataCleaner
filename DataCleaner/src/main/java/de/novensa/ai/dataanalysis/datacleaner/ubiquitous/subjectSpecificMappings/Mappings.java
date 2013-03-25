package de.novensa.ai.dataanalysis.datacleaner.ubiquitous.subjectSpecificMappings;

import com.google.common.base.Joiner;
import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.Constants;

/**
 * All medical mappings.
 *
 * @author Daniel Schulz
 */
public class Mappings extends PatientsHealthMapping {

    private static final String[] HEADER_ITEMS = new String[]{
            "patientsId", "patientsSex", "patientsAge", "patientsAgeDiagnosisPut",
            "healthState", "HDL", "mediaType", "date", "time"
    };

    public static final String HEADER_ENDING =
            (Joiner.on(Constants.HEADER_SIGNATURES_DELIMITER).skipNulls()).join(HEADER_ITEMS);

    public Mappings() {
    }
}
