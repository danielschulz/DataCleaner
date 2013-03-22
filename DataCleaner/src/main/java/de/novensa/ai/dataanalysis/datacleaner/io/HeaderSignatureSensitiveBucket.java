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
public class HeaderSignatureSensitiveBucket<T> {

    private List<Pair<String, CsvDataFrame<T>>> pairs;

    public HeaderSignatureSensitiveBucket(String filename, CsvDataFrame<T> csvDataFrame) {
        this.pairs = new ArrayList<Pair<String, CsvDataFrame<T>>>();
        this.pairs.add(new Pair<String, CsvDataFrame<T>>(filename, csvDataFrame));
    }

    @SuppressWarnings("UnusedDeclaration")
    public List<Pair<String, CsvDataFrame<T>>> getPairs() {
        return pairs;
    }

    public void addPair(String filename, CsvDataFrame<T> csvDataFrame) {
        this.pairs.add(new Pair<String, CsvDataFrame<T>>(filename, csvDataFrame));
    }
}
