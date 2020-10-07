package censusanalyser;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CensusAnalyser {
    private static final String CENSUS_JSON_FILE = "./src/test/resources/IndiaStateCensusData.json";
    List<CensusDAO> censusList = null;
    List<CensusDAO> stateList = null ;
    Map<String, IndianStateDAO> stateMap = null ;
    Map<String, CensusDAO> censusMap = null;

    public CensusAnalyser(){
        this.censusList = new ArrayList<>();
        this.censusMap = new HashMap<>();
        this.stateList = new ArrayList<CensusDAO>();
        this.stateMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String... csvFilePath) throws CensusAnalyserException {
        censusMap = new CensusLoader().loadCensusData(IndiaCensusCSV.class, csvFilePath);
        return censusMap.size();
    }
    public int loadUSCensusData(String... csvFilePath) throws CensusAnalyserException {
        censusMap = new CensusLoader().loadCensusData( USCensusCSV.class, csvFilePath);
        return censusMap.size();
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
        if(censusMap == null || censusMap.size() ==0){
            throw new CensusAnalyserException("No State Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }

        stateList.addAll(censusMap.values());
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.stateId);
        this.sortStateData(censusComparator);
        String sortedStateCode = new Gson().toJson(this.stateList);
        return sortedStateCode;
    }
    //bubble sort for sorting IndianStateDAO
    private void sortStateData(Comparator<CensusDAO> csvComparator) {
        for (int i=0 ; i<stateList.size()-1;i++)
        {
            for (int j=0 ; j<stateList.size()-i-1;j++){
                CensusDAO census1=stateList.get(j);
                CensusDAO census2=stateList.get(j+1);
                if(csvComparator.compare(census1,census2) > 0)
                {
                    stateList.set(j,census2);
                    stateList.set(j+1,census1);
                }
            }
        }
    }

}
