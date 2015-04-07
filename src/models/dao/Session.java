package models.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import models.dao.exeptions.PompeuHibernado;

public class Session {

	private Connection con;
	private PreparedStatement stm;

	public Session(Connection con) {
		this.con = con;
	}

	@SuppressWarnings("unchecked")
	public <T> T persist(T objeto) {
		int lenfilds = objeto.getClass().getDeclaredFields().length;
		int indice = 1;
		Field[] fs = objeto.getClass().getDeclaredFields();
		char[] values = new char[fs.length];
		StringBuilder builderInterrogacoes = new StringBuilder();
		StringBuilder bulderFilers = new StringBuilder();

		for (int i = 1; i < values.length; ++i) {
			builderInterrogacoes.append("?");
			builderInterrogacoes.append(",");
		}
		builderInterrogacoes.deleteCharAt(builderInterrogacoes.length() - 1);

		Object[] fildesValues = new Object[lenfilds];

		for (; indice < lenfilds; indice++) {
			Field f = objeto.getClass().getDeclaredFields()[indice];
			f.setAccessible(true);
			try {
				fildesValues[indice] = f.get(objeto);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			bulderFilers.append(f.getName());
			bulderFilers.append(",");
		}

		bulderFilers.deleteCharAt(bulderFilers.length() - 1);

		String sql = "insert into "
				+ objeto.getClass().getSimpleName().toLowerCase() + "s " + "("
				+ bulderFilers.toString() + ")" + " values " + "( "
				+ builderInterrogacoes.toString() + " )";

		try {
			stm = con.prepareStatement(sql,
					PreparedStatement.RETURN_GENERATED_KEYS);
			for (int i = 1; i < indice; i++) {
				stm.setObject(i, fildesValues[i]);
			}
			stm.execute();

			ResultSet rs = stm.getGeneratedKeys();
			rs.next();

			return (T) getById(objeto.getClass(), rs.getInt(1));

		} catch (Exception e) {
			throw new PompeuHibernado(e);
		}

	}

	@SuppressWarnings("unchecked")
	public <T> T marge(final T objeto) {

		int indice = 1;
		Field[] fs = objeto.getClass().getDeclaredFields();
		fs[0].setAccessible(true);
		Integer id = null;

		try {
			id = (Integer) fs[0].get(objeto);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		int lenfilds = fs.length;

		StringBuilder bulderFilers = new StringBuilder();

		Object[] fildesValues = new Object[lenfilds];

		for (; indice < lenfilds; indice++) {
			Field f = objeto.getClass().getDeclaredFields()[indice];
			f.setAccessible(true);
			try {
				fildesValues[indice] = f.get(objeto);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			bulderFilers.append(f.getName().concat(" = ?,"));
		}
		bulderFilers.deleteCharAt(bulderFilers.length() - 1);

		String sql = "update "
				+ objeto.getClass().getSimpleName().toLowerCase() + "s set "
				+ bulderFilers.toString() + " where id = ?";

		try {

			stm = con.prepareStatement(sql);

			stm.setLong(indice, id);

			for (int i = 1; i < indice; i++) {
				stm.setObject(i, fildesValues[i]);
			}

			stm.execute();

			return (T) getById(objeto.getClass(), id);

		} catch (Exception e) {
			throw new PompeuHibernado(e);
		}

	}

	public <T> T getById(final Class<T> type, final Integer id) {

		try {
			T newInstance = type.newInstance();

			stm = con.prepareStatement("select * from "
					+ newInstance.getClass().getSimpleName().toLowerCase()
					+ "s where id=?");

			stm.setInt(1, id);

			ResultSet rs = stm.executeQuery();

			if (rs.next()) {
				for (int i = 0; i < newInstance.getClass().getDeclaredFields().length; i++) {
					Field f = newInstance.getClass().getDeclaredFields()[i];
					f.setAccessible(true);
					f.set(newInstance, rs.getObject(f.getName()));
				}
				return newInstance;
			}

		} catch (Exception e) {
			throw new PompeuHibernado(e);
		}
		return null;

	}

	public <T> List<T> getAll(final Class<T> type) {
		List<T> results = new ArrayList<>();
		try {

			stm = con.prepareStatement("select * from "
					+ type.getSimpleName().toLowerCase() + "s");

			ResultSet rs = stm.executeQuery();
			while (rs.next()) {

				T newInstance = type.newInstance();

				for (int i = 0; i < newInstance.getClass().getDeclaredFields().length; i++) {
					Field f = newInstance.getClass().getDeclaredFields()[i];
					f.setAccessible(true);
					f.set(newInstance, rs.getObject(f.getName()));
				}
				results.add(newInstance);
			}

			return results;
		} catch (Exception e) {
			throw new PompeuHibernado(e);
		}
	}

	public <T> Boolean delete(T object) {

		try {

			Field field = object.getClass().getDeclaredField("id");
			field.setAccessible(true);
			Integer id = (Integer) field.get(object);

			if (getById(object.getClass(), id) != null) {
				stm = con.prepareStatement("delete from "
						+ object.getClass().getSimpleName().toLowerCase()
						+ "s " + "where id = ?");

				stm.setLong(1, id);
				stm.execute();

				return Boolean.TRUE;
			}

		} catch (Exception e) {
			throw new PompeuHibernado(e);
		}
		return Boolean.FALSE;
	}
}
