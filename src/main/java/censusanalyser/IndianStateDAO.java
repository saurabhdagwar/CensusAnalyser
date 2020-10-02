package censusanalyser;

public class IndianStateDAO {
    public String state;
    public String stateCode;

    public IndianStateDAO(IndiaStateCodeCSV indiaStateCodeCSV){
        state = indiaStateCodeCSV.state;
        stateCode = indiaStateCodeCSV.stateCode;
    }

}


