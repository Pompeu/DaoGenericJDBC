package models.dao.exeptions;

public class PompeuHibernado extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PompeuHibernado(Exception e) {
		super(e);
	}
}
