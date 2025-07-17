package org.example.factory;

import org.example.model.Complaint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ComplaintFactory {
    public static final Logger log = LoggerFactory.getLogger(ComplaintFactory.class);

    public static List<Complaint> toComplaints(Sheet sheet) {
        log.info("Converting sheet {}", sheet.getSheetName());
        return Stream.of(sheet)
                .map(Sheet::iterator)
                .map(iter -> Spliterators.spliteratorUnknownSize(iter, Spliterator.CONCURRENT))
                .flatMap(split -> StreamSupport.stream(split, false))
                .filter(row -> row.getRowNum() > 11)
                .map(ComplaintFactory::fromRow)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private static Optional<Complaint> fromRow(Row row) {
        try {
            log.trace("Converting row {} to complaint.", row.getRowNum());

            String agency = row.getCell(0).getStringCellValue();
            String type = row.getCell(1).getStringCellValue();
            String descriptor = row.getCell(2).getStringCellValue();
            String details = row.getCell(3).getStringCellValue();
            String publicView = row.getCell(4).getStringCellValue();
            String locationType = row.getCell(5).getStringCellValue();

            var complaint = new Complaint(agency, type, descriptor, details, publicView, locationType);
            return Optional.of(complaint);
        } catch (NullPointerException exp) {
            log.debug("Could not convert row {} due to null cells", row.getRowNum());
        }

        return Optional.empty();
    }
}
