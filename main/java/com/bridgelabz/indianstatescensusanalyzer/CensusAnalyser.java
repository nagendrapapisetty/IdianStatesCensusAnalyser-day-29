package com.bridgelabz.indianstatescensusanalyzer;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.bridgelabz.indianstatescensusanalyzer.CensusAnalyserException.ExceptionType;
public class CensusAnalyser {

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {

        try {

            if(csvFilePath.contains("txt")) {
                throw new CensusAnalyserException("File must be in CSV Format", ExceptionType.CENSUS_INCORRECT_FILE_FORMAT);
            }
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<IndianCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<IndianCensusCSV>(reader);
            csvToBeanBuilder.withType(IndianCensusCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndianCensusCSV> csvToBean = csvToBeanBuilder.build();
            Iterator<IndianCensusCSV> censusCSVIterator = csvToBean.iterator();

            int numberOfEntries = 0;
            while(censusCSVIterator.hasNext()) {
                numberOfEntries++;
                IndianCensusCSV censusData = censusCSVIterator.next();
            }
            return numberOfEntries;
        }
        catch(IOException e) {
            throw new CensusAnalyserException(e.getMessage(), ExceptionType.CENSUS_FILE_PROBLEM);
        }
        catch(RuntimeException e) {
            throw new CensusAnalyserException("CSV File Must Have Comma As Delimiter Or Has Incorrect Header", ExceptionType.CENSUS_WRONG_DELIMITER_OR_WRONG_HEADER);
        }
    }


}

