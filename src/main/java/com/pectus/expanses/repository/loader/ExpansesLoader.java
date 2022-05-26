package com.pectus.expanses.repository.loader;


import com.pectus.expanses.model.Expanse;
import com.pectus.expanses.repository.ExpanseRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Slf4j
public class ExpansesLoader {

    private static final String EXPANSES_CSV_FILE_PATH = "/expanses.csv";
    private static final DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    private final ExpanseRepository expanseRepository;

    public ExpansesLoader(ExpanseRepository expanseRepository) {
        this.expanseRepository = expanseRepository;
    }

    @PostConstruct
    public void init() {
        persistRecords();
    }

    private void persistRecords() {
        List<CSVRecord> csvRecords = parseFile();

        if (csvRecords == null || csvRecords.size() == 0) {
            log.info("CSV file is EMPTY!");
        }

        csvRecords.forEach(expanse -> {
            Expanse record = Expanse.builder()
                    .department(expanse.get(ExpanseHeaders.departments))
                    .projectName(expanse.get(ExpanseHeaders.project_name))
                    .amount(getAmountAsBigDecimal(expanse.get(ExpanseHeaders.amount)))
                    .date(LocalDate.parse(expanse.get(ExpanseHeaders.date), dateTimeformatter))
                    .memberName(expanse.get(ExpanseHeaders.member_name))
                    .build();
            expanseRepository.save(record);
        });

        log.info("Expanses records persisted successfully");

    }

    private List<CSVRecord> parseFile() {
        // read file from data directory
        try {
            InputStream resource = getClass().getResourceAsStream(EXPANSES_CSV_FILE_PATH);
            Reader in = new InputStreamReader(resource);
            return CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(in)
                    .getRecords();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private BigDecimal getAmountAsBigDecimal(String amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        String pattern = "#,##0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        try {
            return (BigDecimal) decimalFormat.parse(amount);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
