package activeRecord;

public class DBConnection {

    private static DBConnection instance;

    private DBConnection() {

    }

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
}
