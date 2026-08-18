package br.edu.infnet.tp1.reservation.repository;

import br.edu.infnet.tp1.reservation.model.Reservation;
import br.edu.infnet.tp1.reservation.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
            select coalesce(sum(r.quantity), 0)
            from Reservation r
            where r.eventId = :eventId
              and r.status = :status
            """)
    Long sumQuantityByEventIdAndStatus(
            @Param("eventId") String eventId,
            @Param("status") ReservationStatus status);
}
