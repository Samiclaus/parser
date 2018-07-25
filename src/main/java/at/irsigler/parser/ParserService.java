package at.irsigler.parser;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.TempFile;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

@Service
@Slf4j
public class ParserService {

    public String getTestString() {
        return "Hello World!";
    }

    public ResponseEntity<InputStreamResource> getEZWFile(String countryCode, String year) throws IOException {
        System.setProperty("file.encoding", "UTF-8");
        UrlResource urlResource = new UrlResource("http://www.statistik.at/wcm/idc/idcplg?IdcService=GET_NATIVE_FILE&RevisionSelectionMethod=LatestReleased&dDocName=080904");
        CountryCode country = StringUtils.isNotEmpty(countryCode) ? CountryCode.lookup(countryCode) : null;
        YearMapping yearMapping = YearMapping.lookup(year);
        File temp = TempFile.createTempFile("ewz", "txt");


        try (XSSFWorkbook inputWorkbook = new XSSFWorkbook(urlResource.getInputStream()); FileWriter fw = new FileWriter(temp)) {
            XSSFSheet inputSheet = inputWorkbook.getSheetAt(0);
            for (Row row : inputSheet) {
                if (row.getLastCellNum() >= 18 && CellType.NUMERIC.equals(row.getCell(0).getCellTypeEnum())) {
                    Cell numberCell = row.getCell(yearMapping.getColumnIndex());
                    fw.write("Community code: " + row.getCell(0).getNumericCellValue() + " Number: " + numberCell.getNumericCellValue() + "\n");
                }
            }
            InputStreamResource isr = new InputStreamResource(new FileInputStream(temp));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("text/plain"))
                    .body(isr);


        }

    }
}
