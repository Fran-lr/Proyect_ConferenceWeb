package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.query.Query;


/**
 * Clase para gestionar todos los accesos a la informacón de la tabla Clientes
 * @author FranJ
 * @version 1.0
 */
public class ClientesDao {
private static SessionFactory sessionFactory;
	
	/**
	 * Construtor de la clase sin parametros
	 * Este constructor incluye todos los metadatos sobre el mapeo relacional / de objetos
	 */
	public ClientesDao() {
		try {
			sessionFactory = HibernateUtil.getSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Fallo al crear el objeto sessionFactory." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Método para obtener todas las filas de la tabla Clientes
	 * @return
	 */
	public List<Clientes> mostrarClientes() {

		List<Clientes> miLista = new ArrayList<Clientes>();

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			//Obtiene los datos de la BD
			Query query = session.createQuery("from Clientes");
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
	 * Método para insertar un cliente en la tabla
	 * @param us
	 * @return
	 */
	public boolean insertarCliente(Clientes clie) {
		Session session = null;
		boolean correcto = false;
		long id = 0;
		try {
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			id = (int) session.save(clie);//Inserta Cliente
			if(id > 0) {
				System.out.println("Insertado el Cliente con Id = " + id);
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
	 * Método para modificar un cliente en la tabla
	 * @param use
	 * @return
	 */
	public boolean modificarClientes(Clientes clie) {
		Session session = null;
		boolean correcto = false;
		try {
			if (clie.getIdcliente().equals(null)) {
				System.out.println("El cliente no existe en la base de datos");
				correcto = false;
			} else {
				session = sessionFactory.openSession();
				Transaction tx = session.beginTransaction();
				session.update(clie);//Modifica Cliente
				System.out.println("Modificado cliente con id = " + clie.getIdcliente());
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
	 * Método para borrar un cliente en la tabla
	 * @param user
	 * @return
	 */
	public boolean borrarClientes(Clientes clie) {

		Session session = null;
		boolean correcto = false;
		try {
			if (clie.getIdcliente().equals(null)) {
				System.out.println("El cliente no existe en la base de datos");
				correcto = false;
			} else {
				session = sessionFactory.openSession();
				Transaction tx = session.beginTransaction();
				System.out.println("Eliminando cliente con ID = " + clie.getIdcliente());
				session.delete(clie);//Borrar cliente
				tx.commit();
				correcto = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("¡¡ Error -> "+e.getMessage());
		}finally {
			session.close();
		}
		return correcto;
	}

	/**
	 * Metodo para consultar Si un cliente tiene una Orden Pendiente
	 * @param id
	 * @return
	 */
	public boolean consultaClienteTieneOrden(int id)  {
		
		List<Orden> lis = new ArrayList<Orden>();
		OrdenDao ordendao = new OrdenDao();
		lis = ordendao.obtenerTodasLasOrdenes();
	
		if(lis !=null){
			Iterator<Orden> ite = lis.iterator();
			Orden or = new Orden();
			//Buscamos el cliente dentro de la BD de Orden
			while(ite.hasNext()){
				or=(Orden) ite.next();
				if(or.getClientes().getIdcliente() == id){
				  return true;
				}	
			}
		}
		return false;
	}
	
	/**
	 * Método para obtener un cliente a partir del ID
	 * @param id
	 * @return
	 */
	public Clientes ObtenerCliente(int id) {
		Session session = null;
		Clientes cliente = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			//Obtenemos el cliente
			cliente = (Clientes) session.get(Clientes.class, id);
			if (cliente.getIdcliente().equals(null)) {
				System.out.println("No existe el id en la base de datos");
			} else {
				tx.commit();
				return cliente;
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
	 * Métdo para consultar si un cliente existe
	 * @param id
	 * @return
	 */
	public boolean consultaExisteCliente(int id)  {
		
		List<Clientes> lis = new ArrayList<Clientes>();
		ClientesDao clientendao = new ClientesDao();
		lis = clientendao.mostrarClientes();
	
		if(lis !=null){
			Iterator<Clientes> ite = lis.iterator();
			Clientes cli = new Clientes();
			//Buscamos el cliente
			while(ite.hasNext()){
				cli=(Clientes) ite.next();
				if(cli.getIdcliente() == id){
				  return true;
				}	
			}
		}
		return false;
	}
	
}
