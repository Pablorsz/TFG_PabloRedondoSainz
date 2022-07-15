package modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import modelo.CartaDesarrollo;
import modelo.Partida;

public class DAOPartida {

	public static int guardarPartida(Partida partida) {
		String dbURL = "jdbc:derby:.//db;user=admin;password=admin";
		
		
	  try {
            Connection con = DriverManager.getConnection(dbURL);
            PreparedStatement ps = con.prepareStatement("INSERT INTO Partida (idEscenario, financieros,"
            		+ "generales, krystallium, puntuacionFinal) VALUES (?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, partida.getIdEscenario());
            ps.setInt(2, partida.getFinancieros());
            ps.setInt(3, partida.getGenerales());
            ps.setInt(4, partida.getKrystallium());
            ps.setInt(5, partida.getPuntuacionFinal());
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int idPartida = rs.getInt(1);
            System.out.println("El id es "+idPartida);
            for (CartaDesarrollo c : partida.getCompletadas()) {
            	  Statement ps1 = con.createStatement();
            	  System.out.println(c.getId());
            	  System.out.println(c.getId());
                  ps1.executeUpdate("INSERT INTO UsoEnPartida "
              	  		+ "(idPartida, idCarta) VALUES ("+idPartida+", "+c.getId()+")");
                  
			}
            System.out.println("todo correcto");
            return idPartida;
        }catch (Exception e) {
        	e.printStackTrace();
		}
		return -1;
		
	}

}
