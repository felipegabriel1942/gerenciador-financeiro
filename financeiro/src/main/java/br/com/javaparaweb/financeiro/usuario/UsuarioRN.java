package br.com.javaparaweb.financeiro.usuario;

import java.util.List;

import br.com.javaparaweb.financeiro.categoria.CategoriaRN;
import br.com.javaparaweb.financeiro.util.DAOFactory;

/**
 * Essa classe trabalha auxiliando para a interface(telas)
 * 
 * @author Felipe
 *
 */
public class UsuarioRN {

	private UsuarioDAO usuarioDAO;

	public UsuarioRN() {
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
	}

	// Carrega uma unica inst�ncia com base emm seu c�digo
	public Usuario carregar(Integer codigo) {
		return this.usuarioDAO.carregar(codigo);
	}

	// Carrega as informa��es do usua�rio logo ap�s o login
	public Usuario buscarPorLogin(String login) {
		return this.usuarioDAO.buscarPorLogin(login);
	}

	// Salva ou atualiza um usuario
	public void salvar(Usuario usuario) {
		Integer codigo = usuario.getCodigo();

		// Verifica se o usuario ja existe ou n�o
		// Se n�o existir cria o usuario se existe faz o update
		if (codigo == null || codigo == 0) {
			usuario.getPermissao().add("ROLE_USUARIO");
			this.usuarioDAO.salvar(usuario);
			CategoriaRN categoriaRN = new CategoriaRN();
			categoriaRN.salvaEstruturaPadrao(usuario);
		} else {
			this.usuarioDAO.atualizar(usuario);
		}
	}

	public void excluir(Usuario usuario) {
		CategoriaRN categoriaRN = new CategoriaRN();
		categoriaRN.excluir(usuario);
		this.usuarioDAO.excluir(usuario);
	}

	public List<Usuario> listar() {
		return this.usuarioDAO.listar();
	}
}
