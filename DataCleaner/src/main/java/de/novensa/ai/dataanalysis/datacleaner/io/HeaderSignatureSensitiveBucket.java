package de.novensa.ai.dataanalysis.datacleaner.io;

import de.novensa.ai.dataanalysis.datacleaner.aggregate.CsvDataFrame;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains multiple CsvDataFrames with the exact same header in it.
 *
 * @author Daniel Schulz
 */
public class HeaderSignatureSensitiveBucket {

    private List<Pair<String, CsvDataFrame>> pairs;

    public HeaderSignatureSensitiveBucket(String filename, CsvDataFrame csvDataFrame) {
        this.pairs = new ArrayList<Pair<String, CsvDataFrame>>();
        this.pairs.add(new Pair<String, CsvDataFrame>(filename, csvDataFrame));
    }

    @SuppressWarnings("UnusedDeclaration")
    public List<Pair<String, CsvDataFrame>> getPairs() {
        return pairs;
    }

    public void addPair(String filename, CsvDataFrame csvDataFrame) {
        this.pairs.add(new Pair<String, CsvDataFrame>(filename, csvDataFrame));
    }
}
