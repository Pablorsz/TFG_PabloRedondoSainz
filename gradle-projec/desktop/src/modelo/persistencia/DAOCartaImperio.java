package modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.CartaImperio;
public class DAOCartaImperio {

	public static ArrayList<CartaImperio> cargarCartasImperio() {
		ArrayList<CartaImperio> cartas = new ArrayList<CartaImperio>();
		
	
		String dbURL = "jdbc:derby:.//db;user=admin;password=admin";
		try {
			Connection conn = DriverManager.getConnection(dbURL);
	        Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM CartaImperio");
			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				String produccionInicial = rs.getString("recursosProduccion");
				int puntos = rs.getInt("puntos");
				int tipoPuntos = rs.getInt("tipoPuntos");
				CartaImperio c = new CartaImperio(id, nombre, produccionInicial, puntos, tipoPuntos);
				cartas.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cartas;
	}
}
