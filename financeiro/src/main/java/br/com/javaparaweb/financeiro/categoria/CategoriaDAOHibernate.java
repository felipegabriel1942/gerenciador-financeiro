package br.com.javaparaweb.financeiro.categoria;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.javaparaweb.financeiro.usuario.Usuario;

public class CategoriaDAOHibernate implements CategoriaDAO{
	
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	/**
	 * O merge serve para que seja possivel salvar ou editar uma entidade que esteja carregada em outra sessão
	 * Como exemplo um combobox preenchido atraves do bd
	 * 
	 *  flush() -> Força a sincronização dos objetos em memória com o banco de dados.
	 *  
	 *  clear() -> Remove todos os objetos carregados e força a recarga da instância do banco de dados. 
	 */
	@Override
	public Categoria salvar(Categoria categoria) {
		Categoria merged = (Categoria) this.session.merge(categoria);
		this.session.flush();
		this.session.clear();
		return merged;
	}

	@Override
	public void excluir(Categoria categoria) {
		categoria = (Categoria) this.carregar(categoria.getCodigo());
		this.session.delete(categoria);
		this.session.flush();
		this.session.clear();
		
	}

	@Override
	public Categoria carregar(Integer categoria) {
		return (Categoria) this.session.get(Categoria.class, categoria);
	}

	@Override
	public List<Categoria> listar(Usuario usuario) {
		String hql = "select c from Categoria c where c.pai is null and c.usuario = :usuario";
		Query query = this.session.createQuery(hql);
		query.setInteger("usuario", usuario.getCodigo());
		
		@SuppressWarnings("unchecked")
		List<Categoria> lista = query.list();
		return lista;
	}

}
