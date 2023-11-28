package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

  public Personne getRealisateur() throws SQLException {
    return Personne.findById(this.id_real);
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
