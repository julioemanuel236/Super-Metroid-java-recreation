package modelo.jugador;

public class Record implements Comparable<Record>{

	/*
	 * clase usada para guardar temporalmente y ordenar los records
	 */
	private String nombre;
	private int puntaje;
		
	public Record() {
		
	}

	public Record(String nombre, int puntaje) {		
		this.nombre = nombre;
		this.puntaje = puntaje;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	@Override
	public int compareTo(Record o) {
		if(o.puntaje<puntaje)return -1;
		if(o.puntaje>puntaje)return 1;
		else return nombre.compareToIgnoreCase(o.nombre);			
	}
	
}
