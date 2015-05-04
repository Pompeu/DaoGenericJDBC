package models.dao;

public class User {

	@Id
	private Integer id;
	private String nome;
	private String cpf;
	private String email;
	private String password;
	private Nivel nivel;
	
	public User() {
		
	}
	
	public User(String nome, String cpf, String email, String password,
			Nivel nivel) {
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.password = password;
		this.nivel = nivel;

	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Nivel getNivel() {
		return nivel;
	}
}
