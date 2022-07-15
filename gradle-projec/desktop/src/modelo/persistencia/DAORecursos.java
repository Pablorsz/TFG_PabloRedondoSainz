package modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import modelo.Recursos;

public class DAORecursos {
	public static HashMap<String,Recursos> cargarLineasRecursos() {

		HashMap<String,Recursos> lineasRecursos = new HashMap<String,Recursos>();
		String dbURL = "jdbc:derby:.//db;user=admin;password=admin";	
		try {
			Connection conn = DriverManager.getConnection(dbURL);
	        Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM LineaRecursos");
			while (rs.next()) {
				String id = rs.getString("id");
				int materiales = rs.getInt("material");
				int energia = rs.getInt("energia");
				int ciencia = rs.getInt("ciencia");
				int oro = rs.getInt("oro");
				int exploracion = rs.getInt("exploracion");
				int financieros = rs.getInt("financieros");
				int generales = rs.getInt("generales");
				int krystallium = rs.getInt("krystallium");
				Recursos lr = new Recursos(id, materiales, energia, ciencia, oro, exploracion,
						financieros, generales, krystallium);
				lineasRecursos.put(id,lr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lineasRecursos;
	}
}
