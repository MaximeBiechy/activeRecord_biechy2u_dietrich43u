package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Personne {

  private int id;
  private String nom, prenom;

  public Personne(String nom, String prenom){
    this.id = -1;
    this.nom = nom;
    this.prenom = prenom;
  }

  public static Personne findById(int id_personne) throws SQLException {
    Personne personne = null;

    Connection connect = DBConnection.getInstance().getConnection();
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

  public static Personne findByName(String nom_personne) throws SQLException {
    Personne personne = null;

    Connection connect = DBConnection.getInstance().getConnection();
    String SQLPrep = "SELECT * FROM Personne WHERE nom=?;";
    PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
    prep1.setString(1, nom_personne);
    prep1.execute();
    ResultSet rs = prep1.getResultSet();
    // s'il y a des resultats
    while (rs.next()) {
      String nom = rs.getString("nom");
      String prenom = rs.getString("prenom");
      personne = new Personne(nom, prenom);
      int id = rs.getInt("id");
      System.out.println("  -> (" + id + ") " + nom + ", " + prenom);
    }
    System.out.println();

    return personne;
  }


}
