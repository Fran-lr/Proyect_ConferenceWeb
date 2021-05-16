package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * Clase para gestionar todos los accesos a la información de la tabla Salas
 * @author FranJ
 * @version 1.0
 */
public class SalasDao {
private static SessionFactory sessionFactory;
	
	/**
	 * Constructor de la clase sin parámetros
	 * Este constructor incluye todos los metadatos sobre el mapeo relacional / de objetos
	 */
	public SalasDao(){
		try {
			sessionFactory = HibernateUtil.getSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Fallo al crear el objeto sessionFactory." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	/**
	 * Método para obtener todas las filas de la tabla salas
	 * @return
	 */
	public List<Salas> mostrarSalas() {

		List<Salas> miLista = new ArrayList<Salas>();
		Session session = null;
		try {
			//Se abre una session
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery("from Salas");
			miLista = query.list();//Obtiene los datos de la BD Salas
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("!! Error -> "+e.getMessage());
		} finally {
			session.close();
		}
		return miLista;
	}
	
	/**
	 * Método para insertar una sala en la tabla
	 * @param us
	 * @return
	 */
	public boolean insertarSala(Salas sal) {
		Session session = null;
		boolean correcto = false;
		long id = 0;
		try {
			//Se abre una session
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			id = (int) session.save(sal);//Insertamos la sala
			if(id > 0) {
				System.out.println("Insertado la sala con id  = " + id);
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
	 * Método para modificar una sala en la tabla
	 * @param use
	 * @return
	 */
	public boolean modificarSala(Salas sal) {
		
		Session session = null;
		boolean correcto = false;
		
		try {
			if (sal.getIdsala().equals(null)) {
				System.out.println("La sala no existe en la base de datos");
				correcto = false;
			} else {
				//Se abre una session
				session = sessionFactory.openSession();
				Transaction tx = session.beginTransaction();
				session.update(sal);//Modifica la sala
				System.out.println("Modificado la sala con id = " + sal.getIdsala());
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
	 * Método para borrar una sala en la tabla
	 * @param user
	 * @return
	 */
	public boolean borrarSala(Salas sal) {

		Session session = null;
		boolean correcto = false;
		try {
			if (sal.getIdsala().equals(null)) {
				System.out.println("La sala no existe en la base de datos");
				correcto = false;
			} else {
				//Se abre una session
				session = sessionFactory.openSession();
				Transaction tx = session.beginTransaction();
				System.out.println("Borrando la sala con id  = " + sal.getIdsala());
				session.delete(sal);//Borra la sala
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
	 * Método para consultar si una sala tiene una Orden Pendiente
	 * @param id
	 * @return
	 */
	public boolean consultaSalaTieneOrden(int id)  {
		
		List<Orden> lis = new ArrayList<Orden>();
		OrdenDao ordendao = new OrdenDao();
		lis = ordendao.obtenerTodasLasOrdenes();
	
		if(lis !=null){
			Iterator<Orden> ite = lis.iterator();
			Orden or = new Orden();
			while(ite.hasNext()){
				or=(Orden) ite.next();
				if(or.getSalas().getIdsala() == id){
				  return true;
				}	
			}
		}
		return false;
	}
	
	/**
	 * Método para obtener una Sala a partir de el ID
	 * @param id
	 * @return
	 */
	public Salas ObtenerSala(int id) {
		Session session = null;
		Salas sala = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			//Se obtiene la Sala
			sala = (Salas) session.get(Salas.class, id);
			if (sala.getIdsala().equals(null)) {
				System.out.println("No existe el id en la base de datos");
			} else {
				tx.commit();
				return sala;
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
	 * Método para consultar si una sala existe a partir de un ID
	 * @param id
	 * @return
	 */
	public boolean consultaExisteSala(int id)  {
		
		List<Salas> lis = new ArrayList<Salas>();
		SalasDao salaDao = new SalasDao();
		lis = salaDao.mostrarSalas();
		if(lis !=null){
			Iterator<Salas> ite = lis.iterator();
			Salas sal = new Salas();
			//Se revisa si existe
			while(ite.hasNext()){
				sal=(Salas) ite.next();
				if(sal.getIdsala() == id){
				  return true;
				}	
			}
		}
		return false;
	}	
}
