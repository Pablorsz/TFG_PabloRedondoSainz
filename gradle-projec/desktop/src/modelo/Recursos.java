package modelo;

import java.util.Arrays;
import java.util.HashMap;

import modelo.persistencia.DAORecursos;

public class Recursos {
	
	private static HashMap<String,Recursos> lineasRecursos;
	private int recursos[] = new int[8] ;
	public Recursos(String id, int materiales,	int energia, int ciencia, int oro, int investigacion, 
			int financieros, int generales, int krystallium) {
		recursos[0] = materiales; 
		recursos[1] = energia; 
		recursos[2] = ciencia; 
		recursos[3] = oro;
		recursos[4] = investigacion;
		recursos[5] = financieros;
		recursos[6] = generales;
		recursos[7] = krystallium;

	}
	public Recursos() {

		recursos[0] = 0; 
		recursos[1] = 0; 
		recursos[2] = 0; 
		recursos[3] = 0;
		recursos[4] = 0;
		recursos[5] = 0;
		recursos[6] = 0;
		recursos[7] = 0;
	}
	
	public Recursos(Recursos r) {
		if (r==null) {
			recursos[0] = 0; 
			recursos[1] = 0; 
			recursos[2] = 0; 
			recursos[3] = 0;
			recursos[4] = 0;
			recursos[5] = 0;
			recursos[6] = 0;
			recursos[7] = 0;
		}else {
			recursos[0] = r.get(Recurso.MATERIAL);
			recursos[1] = r.get(Recurso.ENERGIA); 
			recursos[2] = r.get(Recurso.CIENCIA); 
			recursos[3] = r.get(Recurso.ORO);
			recursos[4] = r.get(Recurso.EXPLORACION);
			recursos[5] = r.get(Recurso.FINANCIERO);
			recursos[6] = r.get(Recurso.GENERAL);
			recursos[7] = r.get(Recurso.KRYSTALLIUM);
		}
	}
	
	
	private int[] getRecursos() {
		return recursos;
	}


	public Recursos sumarRecursos(Recursos r) {
		if (r==null) {
			return this;
		}
	 	for (int i = 0; i<r.getRecursos().length; i++) {
	 		recursos[i]+=r.getRecursos()[i];
	 	}
	 	return this;
	}
	
	public Recursos multiplicarRecursos(int m) {
		for (int i = 0; i<getRecursos().length; i++) {
	 		recursos[i]*=m;
	 	}
		return this;
	}

	public static HashMap<String,Recursos> getLineasRecursos() {
		if (lineasRecursos==null) {
			lineasRecursos = DAORecursos.cargarLineasRecursos();
		}
		return lineasRecursos;
		
	}

	public static Recursos getLinea(String idLinea) {
		if(idLinea==null) {
			return new Recursos();
		}
		return new Recursos(lineasRecursos.get(idLinea));
	}

	public int get(Recurso r) {
		switch (r) {
			case MATERIAL:
				return recursos[0];
			case ENERGIA:
				return recursos[1];
			case CIENCIA:
				return recursos[2];
			case ORO:
				return recursos[3];
			case EXPLORACION:
				return recursos[4];
			case FINANCIERO:
				return recursos[5];
			case GENERAL:
				return recursos[6];
			case KRYSTALLIUM:
				return recursos[7];
		}
		return -1;
	}
	
	public void set(Recurso r, int num) {
		switch (r) {
			case MATERIAL:
				recursos[0] = num;
				break;
			case ENERGIA:
				recursos[1] = num;
				break;
			case CIENCIA:
				recursos[2] = num;
				break;
			case ORO:
				recursos[3] = num;
				break;
			case EXPLORACION:
				recursos[4] = num;
				break;
			case FINANCIERO:
				recursos[5] = num;
				break;
			case GENERAL:
				recursos[6] = num;
				break;
			case KRYSTALLIUM:
				recursos[7] = num;
				break;
		}
	}
	
	public void colocarRecurso(Recurso r) {
		switch (r) {
			case MATERIAL:
				recursos[0]--;
				break;
			case ENERGIA:
				recursos[1]--;
				break;
			case CIENCIA:
				recursos[2]--;
				break;
			case ORO:
				recursos[3]--;
				break;
			case EXPLORACION:
				recursos[4]--;
				break;
			case FINANCIERO:
				recursos[5]--;
				break;
			case GENERAL:
				recursos[6]--;
				break;
			case KRYSTALLIUM:
				recursos[7]--;
				break;
		}
	}
	
	public int recursosHasta(int r) {
		int num = 0;
		for (int i = 0; i<r; i++) {
			num+=recursos[i];
	 	}
		return num;
	}

	public boolean terminado() {
		for (int i = 0; i<getRecursos().length; i++) {
	 		if(recursos[i]>0) {
	 			return false;
	 		}
	 	}
	 	return true;
	}
	
	@Override
	public String toString() {
		String r = "";
		if (recursos[0]>0) {
			r += " Material:"+this.recursos[0];
		}if (recursos[1]>0) {
			r += " Energía:"+this.recursos[1];
		}if (recursos[2]>0) {
			r += " Ciencia:"+this.recursos[2];
		}if (recursos[3]>0) {
			r += " Oro:"+this.recursos[3];
		}if (recursos[4]>0) {
			r += " Exploracion:"+this.recursos[4];
		}if (recursos[5]>0) {
			r += " Financieros:"+this.recursos[5];
		}if (recursos[6]>0) {
			r += " Generales:"+this.recursos[6];
		}if (recursos[7]>0) {
			r += " Krystallium:"+this.recursos[7];
		}
		
		return r;
	}

	public int totalRecursos() {
		int num = 0;
		for(int i = 0; i<recursos.length;i++) {
			num+=recursos[i];
		}
		return num;
	}

	public static int getNum(Recurso r) {

		switch (r) {
			case MATERIAL:
				return 0;
			case ENERGIA:
				return 1;
			case CIENCIA:
				return 2;
			case ORO:
				return 3;
			case EXPLORACION:
				return 4;
			case FINANCIERO:
				return 5;
			case GENERAL:
				return 6;
			case KRYSTALLIUM:
				return 7;
	}
		return -1;
	}

	public int getAny() {
		return recursos[0]+recursos[1]+recursos[2]+recursos[3]+recursos[4]+recursos[7];
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recursos other = (Recursos) obj;
		if (!Arrays.equals(recursos, other.recursos))
			return false;
		return true;
	}

	public Recurso recompensa() {
		if(get(Recurso.FINANCIERO)>0) {
			return Recurso.FINANCIERO;
		}
		if(get(Recurso.GENERAL)>0) {
			return Recurso.GENERAL;
		}
		if(get(Recurso.KRYSTALLIUM)>0) {
			return Recurso.KRYSTALLIUM;
		}
		return Recurso.MATERIAL;
	}
	
}
