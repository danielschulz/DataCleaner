package de.novensa.ai.dataanalysis.datacleaner.ubiquitous;

/**
 * The overall context interface.
 *
 * @author Daniel Schulz
 */
public abstract class Context implements PrivateConstants {
    // feel free to use here "implements Constants" instead of "PrivateConstants" as well -- there's no difference.
    // This is just a convenience while developing.

    public abstract Context getContext();
}
