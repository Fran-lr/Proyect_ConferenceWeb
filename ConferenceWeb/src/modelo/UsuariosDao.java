package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * Clase para gestionar todos los accesos a la información de la tabla Usuarios
 * @author FranJ
 * @version 1.0
 */
public class UsuariosDao {

	private static SessionFactory sessionFactory;

	/**
	 * Constructor de la clase sin parámetros
	 * Este constructor incluye todos los metadatos sobre el mapeo relacional de objetos
	 */
	public UsuariosDao() {
		try {
			sessionFactory = HibernateUtil.getSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Fallo al crear el objeto sessionFactory." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Método para comprobar si las credenciales pasadas por parámetro existe o no en la tabla usuario
	 * @param usuario
	 * @param contrasena
	 * @return
	 */
	public boolean comprobarAcceso(String usuario, String contrasena) {
		
		boolean res = false;
		Session session = null;
		
		try {
			//Se abre una session
			session = HibernateUtil.getSessionFactory().openSession();
			Usuarios usu = new Usuarios();
			//Se realiza la consulta
			Query<Usuarios> query = session.createQuery("from Usuarios s WHERE s.nombre =:nombre AND s.contrasena =:contrasena");
			query.setString("nombre", usuario);
			query.setString("contrasena", contrasena);
			List<Usuarios> lista = query.list();//Obtiene los datos
			Iterator<Usuarios> iter = lista.iterator();
			while (iter.hasNext()) {//Revisamos si existe 
				usu = (Usuarios) iter.next();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("!! Error -> "+e.getMessage());
		} finally {
			session.close();
		}
		return res;
	}
	
	/**
	 * Método para obtener todas las filas de la tabla usuarios
	 * @return
	 */
	public List<Usuarios> mostrarUsuarios() {
		
		List<Usuarios> miLista = new ArrayList<Usuarios>();
		Session session = null;
		try {
			//Se abre una sesión
			session = HibernateUtil.getSessionFactory().openSession();
			Usuarios usu = new Usuarios();
			Query query = session.createQuery("from Usuarios");
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
	 * Método para insertar un usuario en la tabla
	 * @param us
	 * @return
	 */
	public boolean insertarUsuario(Usuarios us) {
		Session session = null;
		int id = 0;
		boolean correcto = false;
		try {
			//Se abre una sesión
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			id = (int) session.save(us);//Inserta usuario
			if(id > 0) {
				System.out.println("Insertado persona con id = " + id);
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
	 * Método para modificar un usuario en la tabla
	 * @param use
	 * @return
	 */
	public boolean modificarUsuario(Usuarios use) {
		Session session = null;
		boolean correcto = false;
		try {
			session = sessionFactory.openSession();
			if (use.getIdusuario().equals(null)) {
				System.out.println("El usuario no existe en la base de datos");
				correcto = false;
			} else {
				Transaction tx = session.beginTransaction();
				session.update(use);//Modifica Usuario
				System.out.println("Modificado usuario con id = " + use.getIdusuario());
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
	 * Método para borrar el usuario de la tabla
	 * @param user
	 * @return
	 */
	public boolean borrarUsuario(Usuarios user) {
		Session session = null;
		boolean correcto = false;
		try {
			if (user.getIdusuario().equals(null)) {
				System.out.println("El usuario no existe en la base de datos");
				correcto = false;
			} else {
				session = sessionFactory.openSession();
				Transaction tx = session.beginTransaction();
				System.out.println("Borrando el usuario con id = " + user.getIdusuario());
				session.delete(user);//Borra usuario
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
