package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class OrdenDao {

private static SessionFactory sessionFactory;
	
	/**
	 * Constructor de la clase sin parámetros
	 * Este constructor incluye todos los metadatos sobre el mapeo relacional / de objetos
	 */
	public OrdenDao() {
		try {
			sessionFactory = HibernateUtil.getSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Fallo al crear el objeto sessionFactory." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	/**
	 * Método para obtener todas las filas de la tabla de las Orden
	 * @return
	 */
	public List<Orden> obtenerTodasLasOrdenes(){
		
		Session session = sessionFactory.openSession();
		//Obtiene todas las Ordenes de la tabla
		Query query = session.createQuery("from Orden");
		List<Orden> lista = query.list();
		Iterator<Orden> ite = lista.iterator();
		Orden orden = new Orden();

		while(ite.hasNext()){
			orden=(Orden) ite.next();
			Clientes cli = session.get(Clientes.class, orden.getClientes().getIdcliente());
			Hibernate.initialize(cli.getOrdens());
			Salas sala = session.get(Salas.class, orden.getSalas().getIdsala());
			Hibernate.initialize(sala.getOrdens());
			Ciudades ciudad = session.get(Ciudades.class, orden.getCiudades().getIdciudad());
			Hibernate.initialize(ciudad.getOrdens());
		}
		session.close();
		return lista;
		
	}

	/**
	 * Método para insertar una orden en la tabla Orden
	 * @param orden
	 * @return
	 */
	public boolean insertarOrden(Orden orden) {
		Session session = null;
		boolean correcto = false;
		long id = 0;
		try {
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			id = (int) session.save(orden);//Inserta Orden
			if(id > 0) {
				System.out.println("Insertado la orden con Id = " + id);
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
	 * Método para modificar una orden en la tabla
	 * @param orden
	 * @return
	 */
	public boolean modificarOrden(Orden orden) {
		Session session = null;
		boolean correcto = false;
		
		try {
			if (orden.getIdorden().equals(null)) {
				System.out.println("La orden no existe en la base de datos");
				correcto = false;
			} else {
				session = sessionFactory.openSession();
				Transaction tx = session.beginTransaction();
				session.update(orden);//Modifica Orden
				System.out.println("Modificado la orden con id = " + orden.getIdorden());
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
	 * Método para borrar una orden en la tabla
	 * @param orden
	 * @return
	 */
	public boolean borrarOrden(Orden orden) {
		Session session = null;
		boolean correcto = false;
		
		try {
			if (orden.getIdorden().equals(null)) {
				System.out.println("La orden no existe en la base de datos");
				correcto = false;
			} else {
				session = sessionFactory.openSession();
				Transaction tx = session.beginTransaction();
				System.out.println("Eliminando la orden con id = " +  orden.getIdorden());
				session.delete(orden);//Borra Orden
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
}
