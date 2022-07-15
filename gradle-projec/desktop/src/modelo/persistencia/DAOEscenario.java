package modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Escenario;

public class DAOEscenario {

	public static ArrayList<Escenario> cargarEscenarios() {
		ArrayList<Escenario> escenarios = new ArrayList<>();
		String dbURL = "jdbc:derby:.//db;user=admin;password=admin";
		try {
			Connection conn = DriverManager.getConnection(dbURL);
	        Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Escenario");
			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				String descripcion = rs.getString("descripcion");
				int idImperio = rs.getInt("idCartaImperio");
				int bronce = rs.getInt("bronce");
				int plata = rs.getInt("plata");
				int oro = rs.getInt("oro");
						
				ArrayList<Integer> idCartas = new ArrayList<>();
			
		        Statement st2 = conn.createStatement();
				ResultSet rs2 = st2.executeQuery("SELECT * FROM CartasEscenario WHERE idEscenario="+id);
				while (rs2.next()) {
					int idCarta = rs2.getInt("idCarta");
					idCartas.add(idCarta);	
				}
				Escenario e = new Escenario(id, nombre, descripcion, idCartas, idImperio, bronce, plata, oro);
				escenarios.add(e);
				
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return escenarios;
	}

}
