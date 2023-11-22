package activeRecord;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

  @Test
  void testGetConnection() throws SQLException {
    Connection c1 = DBConnection.getConnection();
    Connection c2 = DBConnection.getConnection();

    assertEquals(c1, c2, "ça devrait être la même connection !");
  }
}