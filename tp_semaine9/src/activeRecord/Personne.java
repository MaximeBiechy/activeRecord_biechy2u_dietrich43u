package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Personne {

  private int id;
  private String nom, prenom;

  public Personne(String nom, String prenom){
    this.id = -1;
    this.nom = nom;
    this.prenom = prenom;
  }


  public static List<Personne> findAll() throws SQLException {
    System.out.println("4) Recupere personne");
    String SQLPrep = "SELECT * FROM Personne;";
    Connection connect = DBConnection.getInstance().getConnection();

    PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
    prep1.execute();
    ResultSet rs = prep1.getResultSet();
    // s'il y a un resultat
    List<Personne> list = new ArrayList<Personne>();
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


}
