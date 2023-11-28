package activeRecord;

import java.sql.*;
import java.util.ArrayList;

public class Film {

  private String titre;
  private int id, id_real;

  public Film(String titre, Personne personne){
    this.titre = titre;
    this.id_real = personne.getId();
    this.id = -1;
  }

  private Film(int id, int id_real, String titre){
    this.titre = titre;
    this.id = id;
    this.id_real = id_real;
  }

  public static Film findById(int id_personne) throws SQLException {
    Film film = null;

    Connection connect = DBConnection.getInstance().getConnection();
    System.out.println("6) Recupere film d'id " + id_personne + "");
    String SQLPrep = "SELECT * FROM Personne WHERE id=?;";
    PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
    prep1.setInt(1, id_personne);
    prep1.execute();
    ResultSet rs = prep1.getResultSet();
    // s'il y a un resultat
    if (rs.next()) {
      String titre = rs.getString("titre");
      int id = rs.getInt(1);
      int id_real = rs.getInt(3);
      film = new Film(id, id_real, titre);
      film.setId(id);
      System.out.println("  -> (" + id + ") " + titre + ", " + id_real);
    }
    System.out.println();

    return film;
  }

  public static ArrayList<Film> finbByRealisateur(Personne p) throws SQLException {
    Connection connect = DBConnection.getInstance().getConnection();
    System.out.println("7) Recupere Film du réalisateur " + p.getId() + "");
    String SQLPrep = "SELECT * FROM Film WHERE ID_REA=?;";
    PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
    prep1.setInt(1, p.getId());
    prep1.execute();
    ResultSet rs = prep1.getResultSet();
    // s'il y a des resultats
    ArrayList<Film> list = new ArrayList<Film>();
    while (rs.next()) {
      Film film = null;
      String titre = rs.getString("titre");
      int id_rea = rs.getInt("id_rea");
      film = new Film(titre, Personne.findById(id_rea));
      int id = rs.getInt("id");
      film.setId(id);
      System.out.println("  -> (" + id + ") " + titre + ", " + id_rea);
      list.add(film);
    }
    System.out.println();

    return list;
  }

  public void save() throws SQLException {
    if (this.id > -1){
      update();
    } else {
      saveNew();
    }
  }

  private void saveNew() throws SQLException {
    String SQLPrep = "INSERT INTO Film (titre, id_rea) VALUES (?, ?);";
    Connection connect = DBConnection.getInstance().getConnection();

    PreparedStatement prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
    prep.setString(1, this.titre);
    prep.setInt(2, this.id_real);
    prep.executeUpdate();
    System.out.println("3) ajout " + this.titre );

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
    prep.setString(1, this.titre);
    prep.setInt(2, this.id_real);
    prep.setInt(3, this.id);  // Use the current object's ID
    prep.execute();
    System.out.println("8) Effectue modification Film id " + this.id);
    System.out.println();
  }

  public Personne getRealisateur() throws SQLException {
    return Personne.findById(this.id_real);
  }

  public static void createTable() throws SQLException {
    Connection connect = DBConnection.getInstance().getConnection();

    String createString = "CREATE TABLE Film ( " + "ID INTEGER  AUTO_INCREMENT, "
            + "TITRE varchar(40) NOT NULL, " + "ID_REA INTEGER DEFAULT NULL, " + "PRIMARY KEY (ID) )";
    Statement stmt = connect.createStatement();
    stmt.executeUpdate(createString);
    System.out.println("1) creation table Film\n");
  }

  public static void deleteTable() throws SQLException {
    Connection connect = DBConnection.getInstance().getConnection();
    String drop = "DROP TABLE Film";
    Statement stmt = connect.createStatement();
    stmt.executeUpdate(drop);
    System.out.println("10) Supprime table Film");
  }


  public String getTitre() {
    return titre;
  }

  public void setTitre(String titre) {
    this.titre = titre;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId_real() {
    return id_real;
  }

  public void setId_real(int id_real) {
    this.id_real = id_real;
  }
}
