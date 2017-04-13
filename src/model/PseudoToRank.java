package model;

public class PseudoToRank {

	private String pseudo;
	
	private int rank;
	
	public PseudoToRank(String pseudo, int rank){
		this.pseudo = pseudo;
		this.rank = rank;
	}
	
	public String getPseudo(){
		return this.pseudo;
	}
	
	public int getRank(){
		return this.rank;
	}
	
}
