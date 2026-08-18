package br.edu.infnet.tp1.reservation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "event-service")
public interface EventServiceFeignClient {

    @GetMapping("/events/{eventId}")
    EventSummary findById(@PathVariable String eventId);
}
