package activeRecord;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

  @Test
  void testGetConnection() throws SQLException {
    Connection c1 = DBConnection.getInstance().getConnection();
    Connection c2 = DBConnection.getInstance().getConnection();

    assertEquals(c1, c2, "ça devrait être la même connection !");
    assertNotEquals(null, c1, "a ne doit pas être null");
    assertNotEquals(null, c2, "a ne doit pas être null");
  }

  @Test
  void testSetNomDB() throws SQLException {
    Connection c1 = DBConnection.getInstance().getConnection();
    DBConnection.getInstance().setNomDB("deefy");
    Connection c2 = DBConnection.getInstance().getConnection();

    assertNotEquals(c1, c2, "ça ne devrait pas être la même connection !");
    assertNotEquals(null, c1, "a ne doit pas être null");
    assertNotEquals(null, c2, "a ne doit pas être null");
  }
}