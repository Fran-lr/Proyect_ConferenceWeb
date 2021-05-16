package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


/**
 * Clase para gestionar todos los accesos a la información de la tabla Ciudades
 * @author FranJ
 * @version 1.0
 */
public class CiudadesDao {

	private static SessionFactory sessionFactory;
	
	/**
	 * Constructor de la clase sin parámetros
	 * Este constructor incluye todos los metadatos sobre el mapeo relacional / de objetos
	 */
	public CiudadesDao() {
		try {
			sessionFactory = HibernateUtil.getSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Fallo al crear el objeto sessionFactory." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Método para obtener todas las filas de la tabla Ciudades
	 * @return
	 */
	public List<Ciudades> mostrarCiudades() {

		List<Ciudades> miLista = new ArrayList<Ciudades>();

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery("from Ciudades");//Obtiene los datos
			miLista = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("!! Error -> "+e.getMessage());
		} finally {
			session.close();
		}
		return miLista;
	}

	/**
	 * Método para insertar una ciudad en la tabla
	 * @param ciudad
	 * @return
	 */
	public boolean insertarCiudad(Ciudades ciudad) {
		Session session = null;
		boolean correcto = false;
		long id = 0;
		try {
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			id = (int) session.save(ciudad);//Inserta Ciudad
			if(id > 0) {
				System.out.println("Insertado la ciudad con Id = " + id);
				tx.commit();
				correcto = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("!! Error -> "+e.getMessage());
		} finally {
			session.close();
		}
		return correcto;
	}
	/**
	 * Mètodo para modificar una ciudad de la tabla
	 * @param ciudad
	 * @return
	 */
	public boolean modificarCiudad(Ciudades ciudad) {
		Session session = null;
		boolean correcto = false;
		
		try {
			if (ciudad.getIdciudad().equals(null)) {
				System.out.println("La ciudad no existe en la base de datos");
				correcto = false;
			} else {
				session = sessionFactory.openSession();
				Transaction tx = session.beginTransaction();
				session.update(ciudad);//Modifica Ciudad
				System.out.println("Modificado la ciudad con id = " + ciudad.getIdciudad());
				tx.commit();
				correcto = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("!! Error -> "+e.getMessage());
		} finally {
			session.close();
		}
		return correcto;
	}
	
	/**
	 * Método para borrar una ciudad de la tabla
	 * @param ciudad
	 * @return
	 */
	public boolean borrarCiudad(Ciudades ciudad) {

		Session session = null;
		boolean correcto = false;
		
		try {
			if (ciudad.getIdciudad().equals(null)) {
				System.out.println("La ciudad no existe en la base de datos");
				correcto = false;
			} else {
				session = sessionFactory.openSession();
				Transaction tx = session.beginTransaction();
				System.out.println("Eliminando la ciudad con id = " + ciudad.getIdciudad());
				session.delete(ciudad);//Borra Ciudad
				tx.commit();
				correcto = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("!! Error -> "+e.getMessage());
		} finally {
			session.close();
		}
		return correcto;
	}	
	
	/**
	 * Método para comprobar si una Ciudad tiene una Orden a partir del ID
	 * @param id
	 * @return
	 */
	public boolean consultaCiudadTieneOrden(int id)  {
		
		List<Orden> lis = new ArrayList<Orden>();
		OrdenDao ordendao = new OrdenDao();
		//Obtenemos todas las ordenes
		lis = ordendao.obtenerTodasLasOrdenes();
	
		if(lis !=null){
			Iterator<Orden> ite = lis.iterator();
			Orden or = new Orden();
			while(ite.hasNext()){
				or=(Orden) ite.next();
				if(or.getCiudades().getIdciudad() == id){
				  return true;
				}	
			}
		}
		return false;
	}
	
	/**
	 * Método para obtener una ciudad a partir del ID 
	 * @param id
	 * @return
	 */
	public Ciudades ObtenerCiudad(int id) {
		Session session = null;
		Ciudades ciu = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			//Obtenemos la ciudad
			ciu = (Ciudades) session.get(Ciudades.class, id);
			if (ciu.getIdciudad().equals(null)) {
				System.out.println("No existe el id en la base de datos");
			} else {
				tx.commit();
				return ciu;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("!! Error -> "+e.getMessage());
		} finally {
			session.close();
		}
		return null;
	}
	
	/**
	 * Método para comprobar si existe una ciudad a partir del ID
	 * @param id
	 * @return
	 */
	public boolean consultaExisteCiudad(int id)  {
		
		List<Ciudades> lis = new ArrayList<Ciudades>();
		CiudadesDao ciudadDao = new CiudadesDao ();
		//Obtenemos todas las ciudades
		lis = ciudadDao.mostrarCiudades();
	
		if(lis !=null){
			Iterator<Ciudades> ite = lis.iterator();
			Ciudades ciu = new Ciudades();
			
			while(ite.hasNext()){
				ciu=(Ciudades) ite.next();
				if(ciu.getIdciudad() == id){
				  return true;
				}	
			}
		}
		return false;
	}
}
