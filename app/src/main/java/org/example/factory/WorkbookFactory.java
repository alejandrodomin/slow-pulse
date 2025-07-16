package org.example.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.nio.file.Path;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkbookFactory {
    public static final Logger log = LoggerFactory.getLogger(WorkbookFactory.class);

    public static Optional<Workbook> getWorkbook(Path file) {
        log.info("Generating workbook.");
        try (FileInputStream dataset = new FileInputStream(file.toFile())) {
            return Optional.of(new XSSFWorkbook(dataset));
        } catch (IOException exp) {
            log.error("Could not open data set.", exp);
        }

        log.warn("Empty workbook, dataset extraction failed.");
        return Optional.empty();
    }
}
