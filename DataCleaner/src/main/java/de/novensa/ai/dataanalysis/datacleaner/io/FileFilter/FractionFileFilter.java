package de.novensa.ai.dataanalysis.datacleaner.io.fileFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.Random;

/**
 * Fraction File Filter to filter files according to a fraction threshold given in the constructor.
 *
 * @author Daniel Schulz
 */
public class FractionFileFilter implements FileFilter {

    private final double fractionThreshold;
    private final Random random = new Random();

    public FractionFileFilter(double fractionThreshold) {
        this.fractionThreshold = fractionThreshold;
    }

    @Override
    public boolean accept(File file) {
        return random.nextDouble() >= this.fractionThreshold;
    }
}
