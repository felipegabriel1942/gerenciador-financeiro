package br.com.javaparaweb.financeiro.usuario;

/**
 * Classe utilizada para acessar os DAOs do sistema
 * @author pinhe
 *
 */
public class DAOFactory {
	
	public static UsuarioDAO criarUsuarioDAO() {
		UsuarioDAOHibernate usuarioDAO = new UsuarioDAOHibernate();
		usuarioDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return null;		
	}
}
