package models.dao;

public class User {

	private Integer id;
	private String nome;
	private String cpf;
	private String email;
	private String password;

	User() {
	}

	public User(String nome, String cpf, String email, String password) {
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.password = password;
	}

	public User(Integer id, String nome, String cpf, String email,
			String password) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.password = password;
	}

	public User(String email) {
		this.email = email;
	}

	public void setId(Integer id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		User other = (User) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + "	 nome=" + nome + ", cpf=" + cpf
				+ ", email=" + email + ", password=" + password + "]";
	}

}
