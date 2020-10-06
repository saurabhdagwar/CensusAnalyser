package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Type;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIAN_STATE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

    @Test
    public void givenIndianCensusCSVFile_ReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givingIndianStateCSV_ShouldReturnExactCount() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int noOfStateCode = censusAnalyser.loadIndianStateCode(INDIAN_STATE_CSV_FILE_PATH);
            Assert.assertEquals(37, noOfStateCode);
        } catch (CensusAnalyserException e) {
        }
    }

     @Test
    public void givingUSCensusData_ShouldReturnCorrectRecords(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int CensusDataCount = censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, CensusDataCount);
        }catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            final String WRONG_CSV_FILE_PATH = "./src/resources/IndiaStateCensusData.csv";
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithIncorrectType_ShouldThrowException() {
        try {
            final String WRONG_INDIAN_CENSUS_CSV_FILE_TYPE = "./src/test/resources/IndiaStateCensusData.txt";
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException expectedRule = ExpectedException.none();
            expectedRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_INDIAN_CENSUS_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithIncorrectDelimiter_ShouldThrowException() {
        try {
            final String WRONG_CSV_DELIMITER = "./src/main/resources/IndiaStateCensusData.csv";
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException expectedRule = ExpectedException.none();
            expectedRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.RUNTIME_EXCEPTION, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongHeader_ShouldThrowException() {
        try {
            final String WRONG_CSV_HEADER = "./src/main/resources/IndiaStateCensus.csv";
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException expectedRule = ExpectedException.none();
            expectedRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.RUNTIME_EXCEPTION, e.type);
        }
    }



    @Test
    public void givenIndiaStateCSV_WithWrongFilePath_ShouldThrowException() {
        try {
            final String WRONG_CSV_FILE_PATH = "./src/resources/IndiaStateCode.csv";
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianStateCode(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givingIndianStateCSV_WithIncorrectType_ShouldThrowException() {
        try {
            final String WRONG_INDIAN_CENSUS_CSV_FILE_TYPE = "./src/test/resources/IndiaStateCode.txt";
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException expectedRule = ExpectedException.none();
            expectedRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianStateCode(WRONG_INDIAN_CENSUS_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }


    @Test
    public void givingIndianStateCSV_WithIncorrectDelimiter_ShouldThrowException() {
        try {
            final String WRONG_CSV_DELIMITER = "./src/main/resources/IndiaStateCode.csv";
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException expectedRule = ExpectedException.none();
            expectedRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianStateCode(WRONG_CSV_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.RUNTIME_EXCEPTION, e.type);
        }
    }

    @Test
    public void givingIndianStateCSV_WithWrongHeader_ShouldThrowException() {
        try {
            final String WRONG_CSV_HEADER = "./src/main/resources/IndiaStateCode.csv";
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException expectedRule = ExpectedException.none();
            expectedRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianStateCode(WRONG_CSV_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.RUNTIME_EXCEPTION, e.type);
        }
    }

    @Test
    public void givingIndianCensusData_WhenSortOnState_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateSortCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givingIndianCensusData_WhenSortOnPopulation_ShouldReturnSortedResult(){
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStatePopulationSort();
            IndiaCensusCSV[] censusCSVS = new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals(607688,censusCSVS[0].population);
        }catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givingIndianStateData_WhenSortOnStateCode_ShouldReturnSortedResult(){
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndianStateCode(INDIAN_STATE_CSV_FILE_PATH);
            String sortedStateData = censusAnalyser.getStateCodeSort();
            IndiaStateCodeCSV[] stateCSVS = new Gson().fromJson(sortedStateData,IndiaStateCodeCSV[].class);
            Assert.assertEquals("AD",stateCSVS[0].stateCode);
        }catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givingIndianStateData_WhenSortOnDensity_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedStateDensity = censusAnalyser.getCensusDensitySort();
            IndiaCensusCSV[] censusCSVS = new Gson().fromJson(sortedStateDensity, IndiaCensusCSV[].class);
            Assert.assertEquals(50, censusCSVS[0].populationDensity);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givingIndianCensusData_WhenSortOnArea_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedStateDensity = censusAnalyser.getCensusAreaSort();
            IndiaCensusCSV[] censusCSVS = new Gson().fromJson(sortedStateDensity, IndiaCensusCSV[].class);
            Assert.assertEquals(342239, censusCSVS[0].totalArea);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givingUSCensusData_WhenSortOnPopulation_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStatePopulationSort();
          USCensusCSV[] censusCSVS = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals(563626, censusCSVS[0].population);
        } catch (CensusAnalyserException e) {
        }
    }

}