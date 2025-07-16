package org.example.factory;

import org.example.model.Complaint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ComplaintFactory {
    public static final Logger log = LoggerFactory.getLogger(ComplaintFactory.class);

    public static List<Complaint> toComplaints(Sheet sheet) {
        log.info("Converting sheet {}", sheet.getSheetName());
        var spliterator = Spliterators.spliteratorUnknownSize(sheet.rowIterator(), Spliterator.CONCURRENT);

        return StreamSupport.stream(spliterator, false)
                .map(ComplaintFactory::fromRow)
                .collect(Collectors.toList());
    }

    private static Complaint fromRow(Row row) {
        String agency = row.getCell(0).getStringCellValue();
        String type = row.getCell(1).getStringCellValue();
        String descriptor = row.getCell(2).getStringCellValue();
        String additionalDetails = row.getCell(3).getStringCellValue();
        Boolean publicView = row.getCell(4).getBooleanCellValue();
        String locationType = row.getCell(5).getStringCellValue();

        return new Complaint(agency, type, descriptor, additionalDetails, publicView, locationType);
    }
}
