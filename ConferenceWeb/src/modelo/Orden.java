package modelo;
// Generated 4 may. 2021 11:26:47 by Hibernate Tools 5.3.20.Final

/**
 * Orden generated by hbm2java
 */
public class Orden implements java.io.Serializable {

	private Integer idorden;
	private Ciudades ciudades;
	private Clientes clientes;
	private Salas salas;
	private String precio;
	private String fechaentrada;
	private String fechasalida;

	public Orden() {
	}

	public Orden(Ciudades ciudades, Clientes clientes, Salas salas, String precio, String fechaentrada,String fechasalida) {
		this.ciudades = ciudades;
		this.clientes = clientes;
		this.salas = salas;
		this.precio = precio;
		this.fechaentrada = fechaentrada;
		this.fechasalida = fechasalida;
	}

	//Get Y Set
	public Integer getIdorden() {
		return this.idorden;
	}

	public void setIdorden(Integer idorden) {
		this.idorden = idorden;
	}

	public Ciudades getCiudades() {
		return this.ciudades;
	}

	public void setCiudades(Ciudades ciudades) {
		this.ciudades = ciudades;
	}

	public Clientes getClientes() {
		return this.clientes;
	}

	public void setClientes(Clientes clientes) {
		this.clientes = clientes;
	}

	public Salas getSalas() {
		return this.salas;
	}

	public void setSalas(Salas salas) {
		this.salas = salas;
	}

	public String getPrecio() {
		return this.precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getFechaentrada() {
		return this.fechaentrada;
	}

	public void setFechaentrada(String fechaentrada) {
		this.fechaentrada = fechaentrada;
	}

	public String getFechasalida() {
		return this.fechasalida;
	}

	public void setFechasalida(String fechasalida) {
		this.fechasalida = fechasalida;
	}

}
