package at.irsigler.parser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ParserConfiguration.class)
public class ParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParserApplication.class, args);
    }
}
