package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

  private static DBConnection instance;
  private static Connection connect;

  private String nomDB = "testpersonne";

  public DBConnection(){

  }

  public static synchronized DBConnection getInstance(){
    if (instance == null) instance = new DBConnection();
    return instance;
  }

  public synchronized Connection getConnection() throws SQLException {
    if (connect == null){
      // variables a modifier en fonction de la base
      String userName = "root";
      String password = "";
      String serverName = "localhost";
      //Attention, sous MAMP, le port est 8889
      String portNumber = "3306";
      String tableName = "personne";

      // iL faut une base nommee testPersonne !
      String dbName = nomDB;

      // creation de la connection
      Properties connectionProps = new Properties();
      connectionProps.put("user", userName);
      connectionProps.put("password", password);
      String urlDB = "jdbc:mysql://" + serverName + ":";
      urlDB += portNumber + "/" + dbName;
      connect = DriverManager.getConnection(urlDB, connectionProps);
    }

    return connect;
  }

  public synchronized void setNomDB(String nouveauNomDB) {
    if (connect != null) {
      try {
        connect.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      connect = null;
    }
    this.nomDB = nouveauNomDB;
  }

}
