package de.novensa.ai.dataanalysis.datacleaner.aggregate;

import com.google.common.base.Joiner;

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
    private final String commandLineArgs;

    public RuntimeInfo(final long startTime, final long endTime,
                       final Map<String, CsvDataFrame<T>> precessedMap, final String[] args) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.diffTimes = endTime - startTime;
        this.processedMap = precessedMap;

        Joiner joiner = Joiner.on(" ").skipNulls();
        this.commandLineArgs = joiner.join(args);
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

    public String getCommandLineArgs() {
        return commandLineArgs;
    }
}
