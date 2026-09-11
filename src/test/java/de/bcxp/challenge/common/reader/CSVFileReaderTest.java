package de.bcxp.challenge.common.reader;

import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVFileReaderTest {

    @Test
    void skipsHeaderAndReadsCommaSeparatedRecords() {
        List<CSVRecord> records = new CSVFileReader().read(
                new StringReader("name,value\nfirst,10\nsecond,20\n")
        );

        assertEquals(2, records.size());
        assertEquals("first", records.get(0).get(0));
        assertEquals("20", records.get(1).get(1));
    }

    @Test
    void supportsConfiguredDelimiter() {
        List<CSVRecord> records = new CSVFileReader(';').read(
                new StringReader("name;value\nexample;42\n")
        );

        assertEquals(1, records.size());
        assertEquals("example", records.get(0).get(0));
        assertEquals("42", records.get(0).get(1));
    }

    @Test
    void supportsQuotedDelimiterInsideAValue() {
        List<CSVRecord> records = new CSVFileReader().read(
                new StringReader("name,value\n\"Last, First\",42\n")
        );

        assertEquals("Last, First", records.get(0).get("name"));
    }
}
