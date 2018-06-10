package br.com.javaparaweb.financeiro.web;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.javaparaweb.financeiro.usuario.Usuario;
import br.com.javaparaweb.financeiro.usuario.UsuarioRN;

@ManagedBean
@SessionScoped
public class ContextoBean implements Serializable{

	private static final long serialVersionUID = 4693275947797122717L;
	private int codigoContaAtiva = 0;
	
	public Usuario getUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String login = external.getRemoteUser();
		if(login != null) {
			UsuarioRN usuarioRN = new UsuarioRN();
			return usuarioRN.buscarPorLogin(login);
		}
		
		return null;
	}

}
