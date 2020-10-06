package censusanalyser;
import com.csvbuilder.CSVBuilderException;
import com.csvbuilder.CSVBuilderFactory;
import com.csvbuilder.ICSVBuilder;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    private static final String CENSUS_JSON_FILE = "./src/test/resources/IndiaStateCensusData.json";
    List<CensusDAO> censusList = null;
    List<IndianStateDAO> stateList = null ;
    Map<String, IndianStateDAO> stateMap = null ;

    Map<String, CensusDAO> censusMap = null;

    public CensusAnalyser(){
        this.censusList = new ArrayList<>();
        this.censusMap = new HashMap<>();
        this.stateList = new ArrayList<>();
        this.stateMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        censusMap = new CensusLoader().loadCensusData(csvFilePath, IndiaCensusCSV.class);
        return censusMap.size();
    }
    public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {
        censusMap = new CensusLoader().loadCensusData(csvFilePath, USCensusCSV.class);
        return censusMap.size();
    }

    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
        ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            Iterator<IndiaStateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .forEach(censusCSV -> stateMap.put(censusCSV.state ,new IndianStateDAO(censusCSV)));
            return this.stateMap.size();


        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.RUNTIME_EXCEPTION);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int entries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return entries;
    }

    //Sorting by state name
    public String getStateSortCensusData() throws CensusAnalyserException {
        if(censusMap == null || censusMap.size() ==0){
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        censusList.addAll(censusMap.values());
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        this.sortCensusData(censusComparator);
        String sortedStatedCensusJson = new Gson().toJson(this.censusList);
        return sortedStatedCensusJson;
    }
    //Sorting by state population
    public String getStatePopulationSort() throws CensusAnalyserException {
        if(censusMap == null || censusMap.size() ==0){
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        censusList.addAll(censusMap.values());
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        this.sortCensusData(censusComparator);
        String sortedStatedCensusJson = new Gson().toJson(this.censusList);
        return sortedStatedCensusJson;
    }
    //Sorting by state density per SqKm
    public String getCensusDensitySort() throws CensusAnalyserException {
        if(censusMap == null || censusMap.size() ==0){
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        censusList.addAll(censusMap.values());
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.populationDensity);
        this.sortCensusData(censusComparator);
        String sortedStatedCensusJson = new Gson().toJson(this.censusList);
        try(FileWriter fileWriter = new FileWriter(CENSUS_JSON_FILE)){
            fileWriter.write(sortedStatedCensusJson);
        } catch (IOException e) { }
        return sortedStatedCensusJson;
    }
    //Sorting by state area in SqKm
    public String getCensusAreaSort() throws CensusAnalyserException {
        if(censusMap == null || censusMap.size() ==0){
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        censusList.addAll(censusMap.values());
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.totalArea,Comparator.reverseOrder());
        this.sortCensusData(censusComparator);
        String sortedStatedCensusJson = new Gson().toJson(this.censusList);
        try(FileWriter fileWriter = new FileWriter(CENSUS_JSON_FILE)){
            fileWriter.write(sortedStatedCensusJson);
        } catch (IOException e) { }
        return sortedStatedCensusJson;
    }
    //bubble sort for sorting IndianCensusDAO
    private void sortCensusData( Comparator<CensusDAO> csvComparator) {
        for (int i=0 ; i<censusList.size()-1;i++)
        {
            for (int j=0 ; j<censusList.size()-i-1;j++){
                CensusDAO census1=censusList.get(j);
                CensusDAO census2=censusList.get(j+1);
                if(csvComparator.compare(census1,census2) > 0)
                {
                    censusList.set(j,census2);
                    censusList.set(j+1,census1);
                }
            }
        }
    }
    //Sorting by state code
    public String getStateCodeSort() throws CensusAnalyserException{
        if(stateMap == null || stateMap.size() ==0){
            throw new CensusAnalyserException("No State Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        stateList.addAll(stateMap.values());
        Comparator<IndianStateDAO> censusComparator = Comparator.comparing(census -> census.stateCode);
        this.sortStateData(censusComparator);
        String sortedStateCode = new Gson().toJson(this.stateList);
        return sortedStateCode;
    }
    //bubble sort for sorting IndianStateDAO
    private void sortStateData( Comparator<IndianStateDAO> csvComparator) {
        for (int i=0 ; i<stateList.size()-1;i++)
        {
            for (int j=0 ; j<stateList.size()-i-1;j++){
                IndianStateDAO census1=stateList.get(j);
                IndianStateDAO census2=stateList.get(j+1);
                if(csvComparator.compare(census1,census2) > 0)
                {
                    stateList.set(j,census2);
                    stateList.set(j+1,census1);
                }
            }
        }
    }



}
