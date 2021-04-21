package util;

import dao.Ticket.TicketDao;
import entity.TicketEntity;

import java.math.BigDecimal;
import java.util.Optional;

public class DaoRunner {
    public static void main(String[] args) {

        var ticketDao = TicketDao.getInstance().findAll();
        System.out.println();

    }

    private static void updateTest() {
        var ticketDao = TicketDao.getInstance();
        var maybeTicket = ticketDao.findById(41L);
        System.out.println(maybeTicket);

        maybeTicket.ifPresent(ticket -> {
            ticket.setCost(BigDecimal.valueOf(188.88));
            ticketDao.update(ticket);
        });
    }

    private static void saveTest() {
        var ticketDao = TicketDao.getInstance();
        var ticketEntity = new TicketEntity();
        ticketEntity.setPassengerNo("123321");
        ticketEntity.setPassengerName("Guga");
        ticketEntity.setFlightId(10L);
        ticketEntity.setSeatNo("B3E");
        ticketEntity.setCost(BigDecimal.TEN);
        var save = ticketDao.save(ticketEntity);
        System.out.println(save);
    }
}
