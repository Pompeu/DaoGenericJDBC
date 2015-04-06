package models.dao;

import java.sql.Connection;

public class GenericDao {

	private Connection con;
	
	public GenericDao(Connection con) {
		this.con = con;
	}

	public <T> T persist(T objeto) {	
		return new Session(con).persist(objeto);
	}
	
	public <T> T getById(Class<T> type, Integer id) {
		return new Session(con).getById(type, id);
	}
}
