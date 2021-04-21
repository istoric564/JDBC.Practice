package dao.Ticket;

import entity.TicketEntity;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private static final String UPDATE_SQL = """
            UPDATE ticket
            SET passenger_no = ?,
            passenger_name = ?,
            flight_id = ?,
            seat_no = ?,
            cost = ?
            WHERE id = ?
                        
            """;
    public static final String FIND_ALL_SQL = """
            SELECT id,
            passenger_no,
            passenger_name,
            flight_id,
            seat_no,
            cost
            FROM ticket
            """;
    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?     
            """;

    private TicketDao() {
    }
    public List<TicketEntity> findAll(){
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<TicketEntity> tickets = new ArrayList<>();
            while (resultSet.next()){
                tickets.add(buildTicket(resultSet));
            }
            return tickets;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }
    public Optional<TicketEntity> findById(Long id){
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1,id);

            var resultSet = preparedStatement.executeQuery();
            TicketEntity ticketEntity = null;
            if(resultSet.next()){
                ticketEntity = buildTicket(resultSet);
            }
            return Optional.ofNullable(ticketEntity);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private TicketEntity buildTicket(ResultSet resultSet) throws SQLException {
        return new TicketEntity(
                resultSet.getLong("id"),
                resultSet.getString("passenger_no"),
                resultSet.getString("passenger_name"),
                resultSet.getLong("flight_id"),
                resultSet.getString("seat_no"),
                resultSet.getBigDecimal("cost")
        );
    }

    public void update(TicketEntity ticketEntity) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement.setString(1, ticketEntity.getPassengerNo());
            prepareStatement.setString(2, ticketEntity.getPassengerName());
            prepareStatement.setLong(3, ticketEntity.getFlightId());
            prepareStatement.setString(4, ticketEntity.getSeatNo());
            prepareStatement.setBigDecimal(5, ticketEntity.getCost());
            prepareStatement.setLong(6, ticketEntity.getId());

            prepareStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }


    public TicketEntity save(TicketEntity ticketEntity) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, ticketEntity.getPassengerNo());
            prepareStatement.setString(2, ticketEntity.getPassengerName());
            prepareStatement.setLong(3, ticketEntity.getFlightId());
            prepareStatement.setString(4, ticketEntity.getSeatNo());
            prepareStatement.setBigDecimal(5, ticketEntity.getCost());

            prepareStatement.executeUpdate();

            var generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
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
