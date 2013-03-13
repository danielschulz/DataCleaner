package de.novensa.ai.dataanalysis.datacleaner.ubiquitous;

import de.novensa.ai.dataanalysis.datacleaner.ubiquitous.subjectSpecificMappings.SynonymToPatientIdentificationMapping;

/**
 * The overall context interface.
 *
 * @author Daniel Schulz
 */
public abstract class Context  extends SynonymToPatientIdentificationMapping implements Constants {
    // feel free to use here "implements Constants" instead of "PrivateConstants" as well -- there's no difference.
    // This is just a convenience while developing. --> swapped from PrivateConstants completely to Constants as
    // working directory can be obtained from command line and archives now being read dynamically from it.

    public abstract Context getContext();
}
