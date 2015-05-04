package teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.dao.Livro;
import models.dao.Nivel;
import models.dao.User;
import models.factory.GenericDaoFactory;
import models.jdbc.JdbcCon;

import org.junit.Before;
import org.junit.Test;

public class TesteGenericDAO {
	private User user;
	private GenericDaoFactory dao;
	private Livro livro;
	private Integer idUser;
	private Integer idLivro;
	private static Connection connection = JdbcCon.getConnection(
			"jdbc:postgresql://localhost:5432/livraria", "postgres", "123");


	@Before
	public void before() {
		user = new User("Maria", "12345678959", "maria@net.com", "552525",
				Nivel.ADMIN);

		livro = new Livro("titulo1", "autor1", "category1", "isbn1", 10,
				new BigDecimal(15.25));

		dao = new GenericDaoFactory(connection);
	}


	@Test
	public void genericaDaoSaveTeste() {
		User user = dao.persist(this.user);
		idUser = user.getId();
		assertNotNull(user);

		Livro livro = dao.persist(this.livro);
		idLivro = livro.getId();
		assertNotNull(livro);
		deleteById(idUser, idLivro);
		System.out.println(idUser + " " + idLivro);
	}

	private void deleteById(Integer idUser, Integer idLivro) {

		User byId2 = dao.getById(User.class, idUser);
		Boolean delete = dao.delete(byId2);
		assertNotNull(delete);
		assertTrue(delete);

		Livro byId = dao.getById(Livro.class, idLivro);
		Boolean deleteById = dao.delete(byId);
		assertNotNull(deleteById);
		assertTrue(deleteById);

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

	@Test
	public void margeObjectTest() {
		Livro livro = dao.getById(Livro.class, 5);
		Integer qtd = new Random().nextInt(150);
		livro.setQtd(qtd);

		Livro marge = dao.marge(livro);

		assertEquals(livro, marge);
		assertEquals(marge.getQtd(), qtd);
		assertEquals(livro.getQtd(), marge.getQtd());
	}

	@Test
	public void getAllTeste() {
		List<Livro> allLivros = dao.getAll(Livro.class);
		assertNotNull(allLivros);
		assertTrue(allLivros.size() > 0);
		assertEquals(allLivros.getClass(), ArrayList.class);

		List<User> allUsers = dao.getAll(User.class);
		assertNotNull(allUsers);
		assertTrue(allUsers.size() > 0);
		assertEquals(allUsers.getClass(), ArrayList.class);

	}

}
