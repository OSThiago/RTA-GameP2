package Classes;

import java.util.ArrayList;
import java.util.List;
import tela.Principal;

public class Mina {
	//Atributos
 	private Principal principal;
	private Vila vila;
	private int id;
	private int qtdAldeoes;
	private int ouro;
	private int nivel;
	private List<Integer> idAldeoes;
	
	//Construtor
	public Mina(Vila vila, int id, Principal principal){
		this.vila = vila;
		this.id = id;
		this.principal = principal;
		this.qtdAldeoes = 0;
		this.ouro = 0;
		this.nivel = 1;
		idAldeoes = new ArrayList<Integer>();
	}
	
	//Metodos
	
	public void minerar(int nivelAldeao){
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.ouro += 5 * nivelAldeao;
		System.out.println("Produziu " + (nivelAldeao*5) + " unidades de ouro!" );
		
	}
	
	public void transportar(int nivelAldeao){
		if(this.ouro >= 5*nivelAldeao) {
			try {
				Thread.sleep(3000/nivelAldeao);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
				
			this.ouro -= 5*nivelAldeao;
			this.vila.getPrefeitura().receberOuro(5*nivelAldeao);
			this.principal.mostrarOuro(this.vila.getPrefeitura().getOuro());
			System.out.println("Transportou " + (nivelAldeao*5) + " unidades de ouro!" );
				
		}else {
			System.out.println("Ouro insuficinte");
		}
	}
	
	public void evoluir(){
		if(this.nivel == 1) {
				this.nivel = 2;
			} else {
				System.out.println("Ja esta evoluido");
			}
	}
	
	/******************** Lista de aldãos ***********************/
	
	public void adionarNaLista(int id) {
		if(this.idAldeoes.isEmpty()) {
			this.idAldeoes.add(id+1);
			this.qtdAldeoes++;
			this.principal.mostrarMinaOuro(this.id + 1, stringListaId());
		}else{
			if(this.idAldeoes.size() <= 5*this.nivel) {
				this.idAldeoes.add(id+1);
				this.qtdAldeoes++;
				this.principal.mostrarMinaOuro(this.id + 1, stringListaId());
			}else {
				System.out.println("Está cheio");
			}
		}
	}
	
	public void retirarDaLista(int id) {
		int posicao = 0;
		int i = 0;
		boolean podeRemover = false;
		
		for (Integer integer : idAldeoes) {
			if(integer == id+1) {
				posicao = i;
				podeRemover = true;
			}
			i++;
	}
		System.out.println("Vamos remover o: " + posicao);
		if(podeRemover) {
			this.idAldeoes.remove(posicao);
			this.qtdAldeoes--;
			this.principal.mostrarMinaOuro(this.id + 1, stringListaId());
		}	
	}
	
	public String stringListaId() {
		String stringLista = "";
		
		if(this.idAldeoes.isEmpty() ){
			return "Não tem aldeão!";
		}else{
			//Gera um string com lista de todos os id dos aldoes
			for (Integer id : idAldeoes) {
				stringLista += " "+id;
			}
		}
		return stringLista;
	}
	
	
	public boolean verificaSeEstaNaMina(int id) {
		for (Integer idNaLista : idAldeoes) {
			if(idNaLista == id+1)
				return true;
		}
		return false;
	}
	
	
	
	/******************** GET ***********************/
	
	public int getQtdAldeoes() {
		return this.qtdAldeoes;
	}
	
	public int getOuro() {
		return this.ouro;
	}
	
	public int getIdMina(){
		return this.id;
	}
	
	public int getNivel(){
		return this.nivel;
	}
	
}
