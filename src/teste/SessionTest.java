package teste;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

import models.dao.User;
import models.factory.GenericDaoFactory;
import models.jdbc.JdbcCon;

import org.junit.BeforeClass;
import org.junit.Test;

public class SessionTest {
	private GenericDaoFactory dao;
	private static Connection connection = JdbcCon.getConnection(
			"jdbc:postgresql://localhost:5432/livraria", "postgres", "123");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void convertEnunsToString() {

		dao = new GenericDaoFactory(connection);
		User user = dao.getById(User.class, 5);
		assertNotNull(user);

	}

}
