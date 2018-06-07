package br.com.javaparaweb.financeiro.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import br.com.javaparaweb.financeiro.usuario.HibernateUtil;
/**
 * Esta classe serve para implementar a a técnica do Open Session in View, que consiste em
 * que a sessão do Hibernate sera aberta no inicio do processamento do servidor e finalizada
 * no fim do processamento. 
 * @author Felipe
 *
 */
/*A anotação webfilter serve para configurar qual o tipo d requisição web esta classe Filter vai interceptar*/
@WebFilter(urlPatterns = {"*.jsf}"})
public class ConexaoHibernateFilter implements Filter{
	
	private SessionFactory sf;
	
	//Executado quando o aplicativo web é desativado ou o servidor cai
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	//Metodo que irá interceptar a requisição web
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		Session currentSession = this.sf.getCurrentSession();
		
		Transaction transaction = null;
		
		try {
			//A linha abaixo inicia uma transação com o banco de dados
			transaction = currentSession.beginTransaction();
			//A linha abaixo serve para passar o processamento adiante
			chain.doFilter(servletRequest, servletResponse);
			//A linha abaixo conclui a transação com o banco de dados
			transaction.commit();
			
			//A condição abaixo fecha a conexão com o banco caso o mesmo ainda esteja aberto
			if(currentSession.isOpen()) {
				currentSession.close();
			}
			
			//Abaixo seguem as tratativas de erro
		}catch (Throwable ex) {
			try {
				if(transaction.isActive()) {
					transaction.rollback();
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
			throw new ServletException(ex);
		}
	}
	
	
	//Metodo executado quando a aplicação é ativada
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.sf = HibernateUtil.getSessionFactory();
		
	}
	
}
