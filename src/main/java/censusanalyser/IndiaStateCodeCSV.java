package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {

    @CsvBindByName(column = "State Name", required = true)
    public String state;

    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;

    @Override
    public String toString(){
        return "IndianStateCodeCSV("+
                "state="+state+ '\''+
                ", stateCode= "+ stateCode +  '\''+
                "}";

    }

}
