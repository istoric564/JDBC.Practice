import org.postgresql.Driver;
import util.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
//        Long flightId = 11L;
//        var ticketsByFlightId = getTicketsByFlightId(flightId);
//        System.out.println(ticketsByFlightId);
        var result = getFlightsBetween(LocalDate.of(2020, 1, 1).atStartOfDay(), LocalDateTime.now());
        System.out.println(result);
    }

    private static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) throws SQLException {
        String sql = """
                SELECT id
                FROM flight
                WHERE departure_date BETWEEN ? AND ?
                """;
        List <Long> result = new ArrayList<>();
        try (var open = ConnectionManager.get();
             var prepareStatement = open.prepareStatement(sql)) {
            prepareStatement.setFetchSize(50);
            prepareStatement.setQueryTimeout(10);
            prepareStatement.setMaxRows(100);


            System.out.println(prepareStatement);
            prepareStatement.setTimestamp(1,Timestamp.valueOf(start));
            System.out.println(prepareStatement);
            prepareStatement.setTimestamp(2,Timestamp.valueOf(end));
            System.out.println(prepareStatement);
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()){
                result.add(resultSet.getLong("id"));
            }
        }
        return result;
    }


    private static List<Long> getTicketsByFlightId(Long flightId) throws SQLException {
        String sql = """
                SELECT id
                FROM ticket
                WHERE flight_id = ?
                """;
        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(sql)) {
            prepareStatement.setLong(1, flightId);


            var resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getObject("id", Long.class));
            }
        }
        return result;
    }
}
