package dao.Ticket;

import entity.TicketEntity;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.*;

public class TicketDao {
    public static final TicketDao INSTANCE = new TicketDao();
    public static final String DELETE_SQL = """
            DELETE FROM ticket
            WHERE id = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO ticket(passenger_no, passenger_name, flight_id, seat_no, cost)
            VALUES (?, ?, ?, ?, ?);
            """;

    private TicketDao(){
    }

    public TicketEntity save(TicketEntity ticketEntity){
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, ticketEntity.getPassengerNo());
            prepareStatement.setString(2, ticketEntity.getPassengerName());
            prepareStatement.setLong(3, ticketEntity.getFlightId());
            prepareStatement.setString(4, ticketEntity.getSeatNo());
            prepareStatement.setBigDecimal(5, ticketEntity.getCost());

            prepareStatement.executeUpdate();

            var generatedKeys = prepareStatement.getGeneratedKeys();
            if(generatedKeys.next()){
                ticketEntity.setId(generatedKeys.getLong("id"));
            }
            return ticketEntity;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }

    }

    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setLong(1, id);

            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public static TicketDao getInstance() {
        return INSTANCE;
    }
}
