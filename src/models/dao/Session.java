package models.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;
import models.dao.exeptions.PompeuHibernado;

public class Session {

	private Connection con;
	private PreparedStatement stm;
	
	public Session(Connection con) {
		this.con = con;
	}

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
			System.out.println(rs.getInt(1));

		} catch (SQLException | SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		return objeto;
	}

	public <T> T getById(final Class<T> type, final Integer id) {

		ProxyFactory factory = new ProxyFactory();
		factory.setSuperclass(type);

		Class<?> magica = factory.createClass();

		try {

			@SuppressWarnings("unchecked")
			final T instance = (T) magica.newInstance();

			MethodHandler handler = new MethodHandler() {
				@Override
				public Object invoke(Object self, Method thisMethod,
						Method proceed, Object[] args) throws Throwable {

					stm = con
							.prepareStatement("select * from "
									+ type.getSimpleName().toLowerCase()
									+ "s where id=?");

					stm.setInt(1, id);

					ResultSet rs = stm.executeQuery();
					rs.next();

					for (int i = 0; i < type.getDeclaredFields().length; i++) {
						Field f = type.getDeclaredFields()[i];
						f.setAccessible(true);
						f.set(instance, rs.getObject(f.getName()));
					}

					return proceed.invoke(self, args);
				}
			};

			((Proxy) instance).setHandler(handler);

			return instance;
		} catch (Exception e) {
			throw new PompeuHibernado(e);

		}

	}

}
