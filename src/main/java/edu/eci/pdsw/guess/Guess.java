package edu.eci.pdsw.guess;

//import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;

//@ApplicationScoped
@SessionScoped
@ManagedBean(name = "guessBean")
public class Guess {

	private int guess;
	private int num;
	private int tries;

	public Guess() {
            this.guess = (new java.util.Random()).nextInt(100)+1;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
		this.tries++;
	}

	public int getTries() {
            return tries;
	}
}
