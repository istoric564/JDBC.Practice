package util;

import dao.Ticket.TicketDao;
import entity.TicketEntity;

import java.math.BigDecimal;

public class DaoRunner {
    public static void main(String[] args) {
        var ticketDao = TicketDao.getInstance();
        var deleteResult = ticketDao.delete(78L);
        System.out.println(deleteResult);

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
