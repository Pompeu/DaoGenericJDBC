package models.dao;

import java.sql.Connection;
import java.util.List;

public class GenericDaoFactory {

	private Connection con;
	
	public GenericDaoFactory(Connection con) {
		this.con = con;
	}

	public <T> T persist(T objeto) {	
		return new Session(con).persist(objeto);
	}
	
	public <T> T marge(T objeto) {	
		return new Session(con).marge(objeto);
	}
	
	public <T> T getById(Class<T> type, Integer id) {
		return new Session(con).getById(type, id);
	}
	
	public <T> List<T> getAll(Class<T> type) {
		return new Session(con).getAll(type);
	}

	public <T> Boolean delete(T object) {
		return new Session(con).delete(object);		
	}

	
}
