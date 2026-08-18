package br.edu.infnet.tp1.event.repository;

import br.edu.infnet.tp1.event.model.Event;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {

    List<Event> findByCategoryIgnoreCase(String category);
}
