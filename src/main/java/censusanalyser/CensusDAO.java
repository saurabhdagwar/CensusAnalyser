package censusanalyser;

public class CensusDAO {
    public String state;
    public String stateId;
    public int population;
    public double totalArea;
    public double populationDensity;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        totalArea = indiaCensusCSV.totalArea;
        populationDensity =  indiaCensusCSV.populationDensity;
        population = indiaCensusCSV.population;
    }
    public CensusDAO(USCensusCSV censusCSV) {
        state = censusCSV.state;
        stateId = censusCSV.stateId;
        population = censusCSV.population;
        totalArea = censusCSV.totalArea;
        populationDensity = censusCSV.populationDensity;

    }
  //  public IndiaCensusCSV getIndiaCensusCSV(){
    //    return new IndiaCensusCSV(state,population,(int)populationDensity,(int)totalArea);
   // }
}
