package modelo.persistencia;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.CartaDesarrollo;

public class DAOCartaDesarrollo {

	public static ArrayList<CartaDesarrollo> cargarCartasDesarrollo() {

		ArrayList<CartaDesarrollo> cartas = new ArrayList<CartaDesarrollo>();
		String dbURL = "jdbc:derby:.//db;user=admin;password=admin";
		try {
			Connection conn = DriverManager.getConnection(dbURL);
	        Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM CartaDesarrollo");
			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				int copias = rs.getInt("copias");
				int tipo = rs.getInt("idTipoCarta");
				int reciclaje = rs.getInt("idReciclaje");
				String costeConstruccion = rs.getString("recursosConstruccion");
				String produccion = rs.getString("recursosProduccion");
				int multProduccion = rs.getInt("multProduccion");
				String recompensa = rs.getString("recursosRecompensa");
				int puntos = rs.getInt("puntos");
				int tipoPuntos = rs.getInt("tipoPuntos");
				CartaDesarrollo c = new CartaDesarrollo(id, nombre, tipo, costeConstruccion,
						produccion,multProduccion, reciclaje, recompensa, puntos,tipoPuntos, copias);
				cartas.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cartas;
	}

}
