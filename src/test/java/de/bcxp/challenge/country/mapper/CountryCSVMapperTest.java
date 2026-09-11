package de.bcxp.challenge.country.mapper;

import de.bcxp.challenge.common.exception.DataImportException;
import de.bcxp.challenge.common.reader.CSVFileReader;
import de.bcxp.challenge.common.service.DataImportService;
import de.bcxp.challenge.country.domain.CountryRecord;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CountryCSVMapperTest {

    private final DataImportService<CSVRecord, CountryRecord> importer =
            new DataImportService<>(new CSVFileReader(';'), new CountryCSVMapper());

    @Test
    void acceptsGermanGroupedWholeNumberFromProvidedData() {
        CountryRecord country = importSingle(
                "Name;Population;Area (km\u00B2)\nCroatia;4.036.355,00;56594\n"
        );

        assertEquals(4_036_355L, country.getPopulation());
    }

    @Test
    void ignoresMalformedColumnsThatAreIrrelevantForTheChallenge() {
        CountryRecord country = importSingle(
                "HDI;Area (km\u00B2);Name;Population\nnot-a-number;316;Malta;516100\n"
        );

        assertEquals("Malta", country.getName());
        assertEquals(316, country.getArea());
    }

    @Test
    void rejectsTrailingCharactersAndReportsColumnAndRecord() {
        DataImportException exception = assertThrows(DataImportException.class, () -> importSingle(
                "Name;Population;Area (km\u00B2)\nInvalid;123abc;100\n"
        ));

        assertTrue(exception.getMessage().contains("Population"));
        assertTrue(exception.getMessage().contains("record 1"));
    }

    @Test
    void rejectsNonPositiveArea() {
        DataImportException exception = assertThrows(DataImportException.class, () -> importSingle(
                "Name;Population;Area (km\u00B2)\nInvalid;100;0\n"
        ));

        assertTrue(exception.getMessage().contains("Area must be positive"));
    }

    @Test
    void rejectsMissingRequiredHeader() {
        DataImportException exception = assertThrows(DataImportException.class, () -> importSingle(
                "Name;Population;Size\nInvalid;100;10\n"
        ));

        assertTrue(exception.getMessage().contains("Area (km\u00B2)"));
    }

    private CountryRecord importSingle(String csv) {
        List<CountryRecord> records = importer.importDataFromReader(new StringReader(csv));
        return records.get(0);
    }
}
