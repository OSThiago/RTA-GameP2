package Classes;

import tela.Principal;

public class Maravilha {
	
	//Atributos
	private Principal principal;
	private Vila vila;
	private int qtdAldeoes;
	private int tijolo;
	
	public Maravilha(Vila vila, Principal principal){
		this.vila = vila;
		this.principal = principal;
		this.qtdAldeoes = 0;
		this.tijolo = 0;
		this.principal.habilitarMaravilha();
		this.principal.mostrarMaravilha(this.tijolo);
	}
	
	public void produzirTijolo(){
		try {
			Thread.sleep(1000);
			this.tijolo += 1000;
			this.principal.mostrarMaravilha(this.tijolo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public int getTijolo(){
		return this.tijolo;
	}
	

}
