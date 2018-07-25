package at.irsigler.parser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@Slf4j
public class ParserRestController {

    private final ParserService parserService;

    @Autowired
    public ParserRestController(ParserService parserService) {
        this.parserService = parserService;
    }

    @RequestMapping("/test")
    public String test() {
        return parserService.getEWZUrl();
    }

    @RequestMapping("/testfile")
    public ResponseEntity<InputStreamResource> testDownload() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("test.txt");
        log.error(classPathResource.getPath());
        File testFile = classPathResource.getFile();
        InputStreamResource isr = new InputStreamResource(classPathResource.getInputStream());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(testFile.length())
                .contentType(MediaType.parseMediaType("text/plain"))
                .body(isr);
    }

    @RequestMapping("/ewz")
    public ResponseEntity<InputStreamResource> parseEWZ(
            @RequestParam(value = "countrycode", defaultValue = "") String countrycode,
            @RequestParam(value = "year", defaultValue = "2018") String year) throws IOException {
        return parserService.getEZWFile(countrycode, year);
    }
}
