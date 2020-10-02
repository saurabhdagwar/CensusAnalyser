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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    private static final String CENSUS_JSON_FILE = "./src/test/resources/IndiaStateCensusData.json";
    List<IndiaCensusDAO> censusList = null;
    List<IndianStateDAO> stateList = null ;

    public CensusAnalyser(){
        this.censusList = new ArrayList<IndiaCensusDAO>();
        this.stateList = new ArrayList<IndianStateDAO>();
    }


    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while(csvFileIterator.hasNext()){
                this.censusList.add( new IndiaCensusDAO(csvFileIterator.next()));
            }
            return this.censusList.size();
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

    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
        ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            Iterator<IndiaStateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            while(stateCSVIterator.hasNext()){
                this.stateList.add(new IndianStateDAO(stateCSVIterator.next()));
            }
            return  this.stateList.size();
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

    public String getStateSortCensusData() throws CensusAnalyserException {
        if(censusList == null || censusList.size() ==0){
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        this.sortCensusData(censusComparator);
        String sortedStatedCensusJson = new Gson().toJson(this.censusList);
        return sortedStatedCensusJson;

    }

    public String getStatePopulationSort() throws CensusAnalyserException {
        if(censusList == null || censusList.size() ==0){
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        this.sortCensusData(censusComparator);
        String sortedStatedCensusJson = new Gson().toJson(this.censusList);
        return sortedStatedCensusJson;
    }

    private void sortCensusData( Comparator<IndiaCensusDAO> csvComparator) {
        for (int i=0 ; i<censusList.size()-1;i++)
        {
            for (int j=0 ; j<censusList.size()-i-1;j++){
                IndiaCensusDAO census1=censusList.get(j);
                IndiaCensusDAO census2=censusList.get(j+1);
                if(csvComparator.compare(census1,census2) > 0)
                {
                    censusList.set(j,census2);
                    censusList.set(j+1,census1);
                }
            }
        }
    }

    public String getStateCodeSort() throws CensusAnalyserException{
        if(stateList == null || stateList.size() ==0){
            throw new CensusAnalyserException("No State Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndianStateDAO> censusComparator = Comparator.comparing(census -> census.stateCode);
        this.sortStateData(censusComparator);
        String sortedStateCode = new Gson().toJson(this.stateList);
        return sortedStateCode;

    }
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

    public String getCensusDensitySort() throws CensusAnalyserException {
        if(censusList == null || censusList.size() ==0){
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        this.sortCensusData(censusComparator);
        String sortedStatedCensusJson = new Gson().toJson(this.censusList);
        writeCensusJsonFile(sortedStatedCensusJson,CENSUS_JSON_FILE);
        return sortedStatedCensusJson;
    }

    private void writeCensusJsonFile(String jsonString, String jsonPath) {
        try(FileWriter fileWriter = new FileWriter(jsonPath)){
            fileWriter.write(jsonString);
        } catch (IOException e) {

        }

    }
}
