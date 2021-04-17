package dao.Ticket;

public class TicketDao {
    public static final TicketDao INSTANCE = new TicketDao();

    private TicketDao(){
    }

    public static TicketDao getInstance() {
        return INSTANCE;
    }
}
