package censusanalyser;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder<E> {
    <E> Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CensusAnalyserException, CSVBuilderException;
    <E> List<E> getCSVFileList(Reader reader, Class csvClass) throws CensusAnalyserException, CSVBuilderException;

}
