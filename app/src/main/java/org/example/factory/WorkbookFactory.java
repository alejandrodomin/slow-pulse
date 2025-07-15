package org.example.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.nio.file.Path;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkbookFactory {
    public static Optional<Workbook> getWorkbook(Path file) {
        try (FileInputStream dataset = new FileInputStream(file.toFile())) {
            return Optional.of(new XSSFWorkbook(dataset));
        } catch (IOException exp) {
            System.out.printf("Could not open data set, excetpion %s", exp);
        }

        return Optional.empty();
    }
}
