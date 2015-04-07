# DaoGenericJDBC
Micro Lib for abstract jdbc api.


##To use
```java
  Connection connection = JdbcCon.getConnection(
	"jdbc:postgresql://localhost:5432/YOUR_DATABASE", "POSTGRES_USER", "POSTGRES_PASSWORD");
	GenericDaoFactory dao = dao = new GenericDaoFactory(connection);
```
  

##The API

how use
```java
dao.persist(T objeto)
	
dao.marge(T objeto)	
	
dao.delete(T object)

dao.getById(Class<T> type, Integer id)
	
dao.getAll(Class<T> type)

```
#Exemple

```java
  User user = new User("Maria", "12345678959", "maria@net.com", "552525");
  User userReturned  = dao.persist(user);
```

