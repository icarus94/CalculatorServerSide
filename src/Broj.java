import java.io.Serializable;


public class Broj implements Serializable {
	private double broj;

	public Broj(double broj) {
		super();
		this.broj = broj;
	}

	public double getBroj() {
		return broj;
	}

	public void setBroj(double broj) {
		this.broj = broj;
	}
	
	
}