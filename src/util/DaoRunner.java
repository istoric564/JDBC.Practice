package util;

import dao.Ticket.TicketDao;
import dto.TicketFilter;
import entity.TicketEntity;

import java.math.BigDecimal;
import java.util.Optional;

public class DaoRunner {
    public static void main(String[] args) {
        var ticketEntity = TicketDao.getInstance().findById(41L);
        System.out.println(ticketEntity);

    }

    private static void filterTest() {
        var ticketFilter = new TicketFilter(3, 0, "Олег Иванов", "A1");

        var ticketDao = TicketDao.getInstance().findAll(ticketFilter);
        System.out.println(ticketDao);
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
//        ticketEntity.setFlight(10L);
        ticketEntity.setSeatNo("B3E");
        ticketEntity.setCost(BigDecimal.TEN);
        var save = ticketDao.save(ticketEntity);
        System.out.println(save);
    }
}
