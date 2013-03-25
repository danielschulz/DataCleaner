package de.novensa.ai.dataanalysis.datacleaner.aggregate;

import java.util.Map;

/**
 * Displays general information about the runtime itself. Will be written into it´s own results file.
 *
 * @author Daniel Schulz
 *
 * @param <T> The data type within each result´s CSV file cell.
 */
@SuppressWarnings("UnusedDeclaration")
public class RuntimeInfo<T> {

    private final long startTime;
    private final long endTime;
    private final long diffTimes;
    private final Map<String, CsvDataFrame<T>> processedMap;

    public RuntimeInfo(long startTime, long endTime, long diffTimes, Map<String, CsvDataFrame<T>> precessedMap) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.diffTimes = diffTimes;
        this.processedMap = precessedMap;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getDiffTimes() {
        return diffTimes;
    }

    public Map<String, CsvDataFrame<T>> getProcessedMap() {
        return processedMap;
    }
}
