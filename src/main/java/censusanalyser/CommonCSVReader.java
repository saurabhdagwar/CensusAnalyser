package censusanalyser;
import java.io.Reader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

class BasicCSVReader {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";

    public static void main(String[] args) throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(INDIA_CENSUS_CSV_FILE_PATH));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {
            for (CSVRecord csvRecord : csvParser) {
                String state = csvRecord.get(0);
                String population = csvRecord.get(1);
                String areaInSqKm = csvRecord.get(2);
                String densityInSqKm = csvRecord.get(3);

                System.out.println("---------------");
                System.out.println("State : " + state);
                System.out.println("population : " + population);
                System.out.println("AreaInSqKm : " + areaInSqKm);
                System.out.println("densityInSqKm : " + densityInSqKm);
                System.out.println("---------------\n\n");
            }
        }
    }
}