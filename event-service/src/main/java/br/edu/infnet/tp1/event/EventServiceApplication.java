package br.edu.infnet.tp1.event;

import br.edu.infnet.tp1.event.model.Event;
import br.edu.infnet.tp1.event.repository.EventRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EventServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner seedEvents(EventRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }

            repository.saveAll(List.of(
                    new Event(
                            "Spring Boot Experience",
                            "Rio de Janeiro",
                            LocalDate.now().plusDays(30),
                            100,
                            "TECH",
                            Map.of("track", "Microservices", "speaker", "Infnet")),
                    new Event(
                            "Noite de Jazz",
                            "Niteroi",
                            LocalDate.now().plusDays(45),
                            60,
                            "MUSIC",
                            Map.of("style", "Jazz", "ageRating", "Livre"))));
        };
    }
}
