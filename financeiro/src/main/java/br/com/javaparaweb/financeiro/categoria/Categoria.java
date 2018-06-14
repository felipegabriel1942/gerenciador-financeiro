package br.com.javaparaweb.financeiro.categoria;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import br.com.javaparaweb.financeiro.usuario.Usuario;

@Entity
public class Categoria implements Serializable {

	private static final long serialVersionUID = -1034076418067764242L;

	@Id
	@GeneratedValue
	private Integer codigo;

	@ManyToOne
	@JoinColumn(name = "categoria_pai", nullable = true, foreignKey = @ForeignKey(name = "fk_categoria_categoria")) // Criação
																													// da
																													// chave
																													// estrangeria
	private Categoria pai;

	@ManyToOne
	@JoinColumn(name = "usuario", foreignKey = @ForeignKey(name = "fk_categoria_usuario")) // Criação da chave
																							// estrangeria
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Usuario usuario;

	private String descrição;

	private int fator;

	/**
	 * FetchType.EAGER ->a lista de é carregada automaticamente ao fazer o "get" do
	 * objeto.
	 * 
	 * CascadeType.REMOVE -> Em caso de remoção todas as entidades relacionadas com
	 * essa serão removidas também.
	 * 
	 * @JoinColumn(name = "categoria_pai", updatable = false) -> Carrega todos os
	 *                  casos em que o codigo na tabela filho seja igual a da tabela
	 *                  pai. updatable = false -> Garante que um update na tabela
	 *                  pai não afete as tabelas filhof
	 */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "categoria_pai", updatable = false) //
	@org.hibernate.annotations.OrderBy(clause = "descricao asc")
	private List<Categoria> filhos;

	public Categoria() {

	}

	public Categoria(Categoria pai, Usuario usuario, String descrição, int fator) {
		this.pai = pai;
		this.usuario = usuario;
		this.descrição = descrição;
		this.fator = fator;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Categoria getPai() {
		return pai;
	}

	public void setPai(Categoria pai) {
		this.pai = pai;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescrição() {
		return descrição;
	}

	public void setDescrição(String descrição) {
		this.descrição = descrição;
	}

	public int getFator() {
		return fator;
	}

	public void setFator(int fator) {
		this.fator = fator;
	}

	public List<Categoria> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<Categoria> filhos) {
		this.filhos = filhos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
