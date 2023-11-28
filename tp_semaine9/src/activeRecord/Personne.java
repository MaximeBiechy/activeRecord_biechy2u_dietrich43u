package activeRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Personne {
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  private int id;
  private String nom, prenom;

  public Personne(String nom, String prenom){
    this.id = -1;
    this.nom = nom;
    this.prenom = prenom;
  }


  public static ArrayList<Personne> findAll() throws SQLException {
    System.out.println("4) Recupere personne");
    String SQLPrep = "SELECT * FROM Personne;";
    Connection connect = DBConnection.getInstance().getConnection();

    PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
    prep1.execute();
    ResultSet rs = prep1.getResultSet();
    // s'il y a un resultat
    ArrayList<Personne> list = new ArrayList<Personne>();
    while(rs.next()) {
      String nom = rs.getString("nom");
      String prenom = rs.getString("prenom");
      int id = rs.getInt("id");
      System.out.println("  -> (" + id + ") " + nom + ", " + prenom);
      Personne p = new Personne(nom, prenom);
      p.id = id;
      list.add(p);
    }
    return list;
  }

  public static Personne findById(int id_personne) throws SQLException {
    Personne personne = null;

    Connection connect = DBConnection.getInstance().getConnection();
    System.out.println("6) Recupere personne d'id " + id_personne + "");
    String SQLPrep = "SELECT * FROM Personne WHERE id=?;";
    PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
    prep1.setInt(1, id_personne);
    prep1.execute();
    ResultSet rs = prep1.getResultSet();
    // s'il y a un resultat
    if (rs.next()) {
      String nom = rs.getString("nom");
      String prenom = rs.getString("prenom");
      personne = new Personne(nom, prenom);
      int id = rs.getInt("id");
      System.out.println("  -> (" + id + ") " + nom + ", " + prenom);
    }
    System.out.println();

    return personne;
  }

  public static ArrayList<Personne> findByName(String nom_personne) throws SQLException {
    Connection connect = DBConnection.getInstance().getConnection();
    System.out.println("7) Recupere personne de nom " + nom_personne + "");
    String SQLPrep = "SELECT * FROM Personne WHERE nom=?;";
    PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
    prep1.setString(1, nom_personne);
    prep1.execute();
    ResultSet rs = prep1.getResultSet();
    // s'il y a des resultats
    ArrayList<Personne> list = new ArrayList<Personne>();
    while (rs.next()) {
      String nom = rs.getString("nom");
      String prenom = rs.getString("prenom");
      Personne personne = new Personne(nom, prenom);
      int id = rs.getInt("id");
      System.out.println("  -> (" + id + ") " + nom + ", " + prenom);
      list.add(personne);
    }
    System.out.println();

    return list;
  }

  public static void createTable() throws SQLException {
    Connection connect = DBConnection.getInstance().getConnection();

    String createString = "CREATE TABLE Personne ( " + "ID INTEGER  AUTO_INCREMENT, "
            + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
    Statement stmt = connect.createStatement();
    stmt.executeUpdate(createString);
    System.out.println("1) creation table Personne\n");
  }
  public void delete() throws SQLException {
    Connection connect = DBConnection.getInstance().getConnection();
    PreparedStatement prep = connect.prepareStatement("DELETE FROM Personne WHERE id=?");
    prep.setInt(1, this.id);
    prep.execute();
    System.out.println("5) Suppression personne id " + this.id + "");
    System.out.println();
  }

  public static void deleteTable() throws SQLException {
    Connection connect = DBConnection.getInstance().getConnection();
    String drop = "DROP TABLE Personne";
    Statement stmt = connect.createStatement();
    stmt.executeUpdate(drop);
    System.out.println("10) Supprime table Personne");
  }

  public void save() throws SQLException {
    if (this.id > -1){
      update();
      System.out.println("update");
    } else {
      saveNew();
      System.out.println("new");
    }
  }

  private void saveNew() throws SQLException {
    String SQLPrep = "INSERT INTO Personne (nom, prenom) VALUES (?, ?);";
    Connection connect = DBConnection.getInstance().getConnection();

    PreparedStatement prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
    prep.setString(1, this.nom);
    prep.setString(2, this.prenom);
    prep.executeUpdate();
    System.out.println("3) ajout " + this.nom + ", " + this.prenom);

    int autoInc = -1;
    ResultSet rs = prep.getGeneratedKeys();
    if (rs.next()){
      autoInc = rs.getInt(1);
    }
    System.out.println(" -> id utilise lors de l'ajout : " + autoInc);
    System.out.println();
    this.id = autoInc;
  }

  private void update() throws SQLException {
      Connection connect = DBConnection.getInstance().getConnection();
      String SQLprep = "update Personne set nom=?, prenom=? where id=?;";
      PreparedStatement prep = connect.prepareStatement(SQLprep);
      prep.setString(1, this.nom);
      prep.setString(2, this.prenom);
      prep.setInt(3, this.id);  // Use the current object's ID
      prep.execute();
      System.out.println("8) Effectue modification Personne id " + this.id);
      System.out.println();
    }
}
