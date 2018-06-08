package br.com.javaparaweb.financeiro.usuario;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class UsuarioDAOHibernate implements UsuarioDAO{
	
	//Session é o responsavel por fazer as operações do hibernate chegarem ao banco de dados
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	@Override
	public void salvar(Usuario usuario) {
		this.session.save(usuario);
		
	}

	@Override
	public void atualizar(Usuario usuario) {
		//Verificação se o usuario tem permissões
		if(usuario.getPermissao() == null || usuario.getPermissao().size() == 0) {
			//Joga o usuario a um objeto a parte
			Usuario usuarioPermissao = this.carregar(usuario.getCodigo());
			//transfere as permissoes originais para o objeto usuario a ser salvo
			usuario.setPermissao(usuarioPermissao.getPermissao());
			//elimina o usuarioPermissao
			this.session.evict(usuarioPermissao);
		}
		this.session.update(usuario);
		
	}

	@Override
	public void excluir(Usuario usuario) {
		this.session.delete(usuario);
		
	}

	@Override
	public Usuario carregar(Integer codigo) {
		return (Usuario) this.session.get(Usuario.class, codigo);
	}

	@Override
	public Usuario buscarPorLogin(String login) {
		String hql = "select u from Usuario u where u.login = :login";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("login", login);
		return (Usuario) consulta.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> listar() {
		return this.session.createCriteria(Usuario.class).list();
	}

}
