package models.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Livro	{
	
	private Integer id;
	private String titulo;
	private String autor;
	private String category;
	private String isbn;
	private Integer qtd;
	private BigDecimal preco;

	public Livro() {
	}

	public Livro(String titulo, String autor, String category, String isbn,
			Integer qtd, BigDecimal preco) {
		this.titulo = titulo;
		this.autor = autor;
		this.category = category;
		this.isbn = isbn;
		this.qtd = qtd;
		this.preco = preco;
	}

	public Livro(Integer id, String titulo, String autor, String category,
			String isbn, Integer qtd, BigDecimal preco) {
		this.titulo = titulo;
		this.isbn = isbn;
		this.autor = autor;
		this.category = category;
		this.id = id;
		this.qtd = qtd;
		this.preco = preco;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getAutor() {
		return autor;
	}

	public String getCategory() {
		return category;
	}

	public Integer getQtd() {
		return qtd;
	}

	public BigDecimal getPreco() {
		return preco.setScale(2, RoundingMode.CEILING);
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
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
		Livro other = (Livro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Livro [id=" + id + ", titulo=" + titulo + ", autor=" + autor
				+ ", category=" + category + ", isbn=" + isbn + ", qtd=" + qtd
				+ ", preco=" + preco + "]";
	}

	
}
