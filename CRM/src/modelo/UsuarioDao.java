package modelo;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UsuarioDao {

	private static SessionFactory sessionFactory;
	
	public UsuarioDao(){
		try {
			sessionFactory = HibernateUtil.getSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Fallo al crear el objeto sessionFactory." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public void insertar(String nombre) {
		Session session = null;
		long id = 0;
		try {
			
			session = sessionFactory.openSession();
			Usuarios usu = new Usuarios();
			System.out.println("Insertando registro");
			Transaction tx = session.beginTransaction();
			usu.setNombre(nombre);
			id = (Long) session.save(usu);

			tx.commit();
			System.out.println("Insertado persona con id = " + id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}
	}
	public void modificar(long Mid,String nomb) {
		Session session = null;
	
		try {
			session = sessionFactory.openSession();
			Usuarios usu = (Usuarios) session.get(Usuarios.class, Mid);
			
			if(usu.getIdusuario().equals(null)){
				System.out.println("No existe el id en la base de datos");
				
			}else{
				System.out.println("Modificando registro");
				Transaction tx = session.beginTransaction();
				usu.setNombre(nomb);
				session.update(usu);
				tx.commit();
				System.out.println("Modificado persona con id = " + Mid);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}
	}
	
	public void borrar(long Mid){
		Session session = null;

		try {
			session = sessionFactory.openSession();
			Usuarios usu = (Usuarios)
			session.load(Usuarios.class, (long) Mid);
			session.delete(usu);
			
			System.out.println("Borrada persona con id = " + Mid);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}
	}
	
	public void mostrar(long Mid){
		Session session = null;
		long id = 0;
		Usuarios usu = null;
		
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Transaction  tx = session.beginTransaction();
			usu = (Usuarios) session.get(Usuarios.class, Mid);
			if(usu.getIdusuario().equals(null)){
				System.out.println("No existe el id en la base de datos");
			}
			else{
				tx.commit();
				System.out.println("Mostrando persona con id = " + id+"\n Con nombre : "+usu.getNombre());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}
	}
	
	public void mostrarTodo(){
		Session session = null;
		try {
			
			session = HibernateUtil.getSessionFactory().openSession();
			Usuarios usu = new Usuarios();
			Query query = session.createQuery("from Usuarios");
			List<Usuarios> lista = query.list();
			Iterator<Usuarios> iter= lista.iterator();
			
			while (iter.hasNext() ) {
				usu=(Usuarios) iter.next();
				System.out.println(usu.getNombre()+ "*" + usu.getContrasena());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}
	}
	public boolean comprobarAcceso(String usuario , String contrasena){
		boolean res= false;
		Session session = null;
		try {
			
			session = HibernateUtil.getSessionFactory().openSession();
			Usuarios usu = new Usuarios();
			Query<Usuarios> query = session.createQuery("from Usuarios s WHERE s.nombre =:nombre AND s.contrasena =:contrasena");
			query.setString("nombre", usuario);
			query.setString("contrasena", contrasena);
			List<Usuarios> lista = query.list();
			Iterator<Usuarios> iter= lista.iterator();
			
			while (iter.hasNext() ) {
				usu=(Usuarios) iter.next();
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}
		return res;
		
	}
}
