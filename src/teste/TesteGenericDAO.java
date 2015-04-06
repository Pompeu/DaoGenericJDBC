package teste;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import models.dao.GenericDao;
import models.dao.Livro;
import models.dao.User;
import models.jdbc.JdbcCon;

import org.junit.Before;
import org.junit.Test;

public class TesteGenericDAO {
	User user;
	GenericDao dao;
	Livro livro;

	@Before
	public void before() {
		user = new User("Maria", "12345678959", "maria@net.com", "552525");
		livro = new Livro("titulo1", "autor1", "category1", "isbn1", 10,
				new BigDecimal(15.25));
		
		dao = new GenericDao(JdbcCon.getConnection(
				"jdbc:postgresql://localhost:5432/livraria", "postgres", "123"));
	}

	@Test
	public void genericaDaoSaveTeste() {
		assertNotNull(dao.persist(user));
		assertNotNull(dao.persist(livro));
	}

	@Test
	public void getUserbyIdTest() {
		User user = dao.getById(User.class, 5);
		
		assertNotNull(user.getNome());
		assertNotNull(user.getCpf());
		assertNotNull(user.getEmail());
		assertNotNull(user.getPassword());
	}

	@Test
	public void getLivrobyIdTest() {
		Livro livro = dao.getById(Livro.class, 5);
		
		assertNotNull(livro.getAutor());
		assertNotNull(livro.getCategory());
		assertNotNull(livro.getId());
		assertNotNull(livro.getIsbn());
		assertNotNull(livro.getPreco());
		assertNotNull(livro.getQtd());
		assertNotNull(livro.getTitulo());
	}

}
