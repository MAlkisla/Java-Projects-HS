package cinema.controller;

import cinema.exception.SeatLocationOutOfBoundsException;
import cinema.exception.SeatNotAvailableException;
import cinema.model.CinemaSeats;
import cinema.model.Seat;
import cinema.model.Ticket;
import cinema.repo.CinemaStaticRepo;
import cinema.repo.TicketStaticRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Collections;
@RestController
public class CinemaController {

    private final CinemaStaticRepo cinemaStaticRepo;
    private final TicketStaticRepo ticketStaticRepo;

    public CinemaController(CinemaStaticRepo cinemaStaticRepo, TicketStaticRepo ticketStaticRepo) {
        this.cinemaStaticRepo = cinemaStaticRepo;
        this.ticketStaticRepo = ticketStaticRepo;
    }

    @GetMapping("/seats")
    public CinemaSeats getCinemaSeats() {
        return cinemaStaticRepo.getCinemaSeats();
    }

    @PostMapping("/purchase")
    public ResponseEntity<Ticket> purchaseTicket(@RequestBody Seat seat) {
        Optional<Seat> optionalOfSeat = cinemaStaticRepo.getSeat(seat.getRow(), seat.getColumn());

        if (optionalOfSeat.isEmpty()) {
            throw new SeatLocationOutOfBoundsException("The number of a row or a column is out of bounds!");

        } else if (!optionalOfSeat.get().isAvailable()) {
            throw new SeatNotAvailableException("The ticket has been already purchased!");

        } else {
            Seat bookedSeat = optionalOfSeat.get();
            bookedSeat.setAvailable(false);
            Ticket ticket = new Ticket(bookedSeat);
            ticketStaticRepo.saveTicket(ticket);
            return ResponseEntity.ok(ticket);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<Ticket> returnTicket(@RequestBody Map<String,String> tokenJson){
        UUID tokenId = UUID.fromString(tokenJson.get("token"));
        Ticket ticket = ticketStaticRepo.deleteTicket(tokenId);
        ticket.getTicket().setAvailable(true);
        return ResponseEntity.ok().body(ticket);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@RequestParam(required = false) String password) {
        if (password != null && password.equals("super_secret")) {
            int totalIncome = calculateTotalIncome();
            int availableSeats = calculateAvailableSeats();
            int purchasedTickets = calculatePurchasedTickets();

            Map<String, Integer> stats = new HashMap<>();
            stats.put("income", totalIncome);
            stats.put("available", availableSeats);
            stats.put("purchased", purchasedTickets);

            return ResponseEntity.ok(stats);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "The password is wrong!"));
        }
    }

    private int calculateTotalIncome() {
        int totalIncome = 0;
        for (Ticket ticket : ticketStaticRepo.getAllSoldTickets()) {
            totalIncome += ticket.getTicket().getPrice();
        }
        return totalIncome;
    }

    private int calculateAvailableSeats() {
        int availableSeats = 0;
        for (Seat seat : cinemaStaticRepo.getCinemaSeats().getSeats()) {
            if (seat.isAvailable()) {
                availableSeats++;
            }
        }
        return availableSeats;
    }

    private int calculatePurchasedTickets() {
        return ticketStaticRepo.getAllSoldTickets().size();
    }
}