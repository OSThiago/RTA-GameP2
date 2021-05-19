package Classes;

import java.util.ArrayList;
import java.util.List;
import tela.Principal;

public class Templo extends Thread{
	//Atributos
	private Principal principal;
	private Vila vila;
	private List<String> evolucoes;
	private AcaoTemplo acaoTemplo;
	private String qualEvolucao;
	
	//Construtor
	public Templo(Vila vila, Principal principal){
		this.vila = vila;
		this.principal = principal;
		this.evolucoes = new ArrayList<String>();
		this.acaoTemplo = AcaoTemplo.PARADO;
	}
	
	//Metodos
	
	public void run() {
		while(true) {
			switch(acaoTemplo) {
			case PARADO:
				parar();
				break;
			case EVOLUINDO:
				evolucao();
				break;
			}
		}
		
	}
	
	
	private void parar() {
		synchronized (this) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void produzirOferendaDeFe(){
		try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		this.vila.getPrefeitura().receberOferenda(2);
		this.principal.mostrarOferendaFe(this.vila.getPrefeitura().getOferenda());
		System.out.println("Transportou 2 unidades de oferendas de fé");
		
	}
	
	public void receberSacrificio(){	
			
			this.vila.getPrefeitura().receberOferenda(100);
			this.principal.mostrarOferendaFe(this.vila.getPrefeitura().getOferenda());
			System.out.println("O seacificio gerou 100 oferendas!");
		
	}
	
	//Evoluções
	public void evolucao(){
		if(this.qualEvolucao.equals("Nuvem de gafanhotos")){
			evoluirNuvem();
		}
		
		if(this.qualEvolucao.equals("Morte dos primogênitos")){
			evoluirMorte();
		}
		
		if(this.qualEvolucao.equals("Chuva de pedras")){
			evoluirChuva();
		}
		
		if(this.qualEvolucao.equals("Proteção contra nuvem de gafanhotos")){
			evoluirProtecaoNuvem();
		}
		
		if(this.qualEvolucao.equals("Proteção contra morte dos primogênitos")){
			evoluirProtecaoMorte();
		}
		
		if(this.qualEvolucao.equals("Proteção contra chuva de pedras")){
			evoluirProtecaoChuva();
		}
		this.setAcaoTemplo(AcaoTemplo.PARADO);
	}
	
	public void evoluirNuvem(){
		int precoOferenda = 0000;
		
		if(!verificaPreco(precoOferenda)) {
			return;
		}
		
		if(existeEvolucao("NUVEM_GAFANHOTOS")) {
			return;
		}
		
		try {
			this.vila.getPrefeitura().retirarOferenda(precoOferenda);
			Thread.sleep(0000);
			this.evolucoes.add("NUVEM_GAFANHOTOS");
			this.principal.mostrarAtaques(this.evolucoes);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void evoluirMorte(){
		int precoOferenda = 1500;
		
		if(!verificaPreco(precoOferenda)) {
			return;
		}
		
		if(existeEvolucao("MORTE_PRIMOGENITOS")) {
			return;
		}
			
		try {
			this.vila.getPrefeitura().retirarOferenda(precoOferenda);
			Thread.sleep(100000);
			this.evolucoes.add("MORTE_PRIMOGENITOS");
			this.principal.mostrarAtaques(this.evolucoes);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void evoluirChuva(){
		int precoOferenda = 2000;
		
		if(!verificaPreco(precoOferenda)) {
			return;
		}
		
		if(existeEvolucao("CHUVA_PEDRAS")) {
			return;
		}
		
		try {
			this.vila.getPrefeitura().retirarOferenda(precoOferenda);
			Thread.sleep(200000);
			this.evolucoes.add("CHUVA_PEDRAS");
			this.principal.mostrarAtaques(this.evolucoes);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	public void evoluirProtecaoNuvem(){
		int precoOferenda = 5000;
		
		if(!verificaPreco(precoOferenda)) {
			return;
		}
		
		if(existeEvolucao("PROTECAO_NUVEM_GAFANHOTOS")) {
			return;
		}
		
		try {
			this.vila.getPrefeitura().retirarOferenda(precoOferenda);
			Thread.sleep(500000);
			this.evolucoes.add("PROTECAO_NUVEM_GAFANHOTOS");
			this.principal.mostrarAtaques(this.evolucoes);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void evoluirProtecaoMorte(){
		int precoOferenda = 6000;
		
		if(!verificaPreco(precoOferenda)) {
			return;
		}
		
		if(existeEvolucao("PROTECAO_MORTE_PRIMOGENITOS")) {
			return;
		}
		
		try {
			this.vila.getPrefeitura().retirarOferenda(precoOferenda);
			Thread.sleep(600000);
			this.evolucoes.add("PROTECAO_MORTE_PRIMOGENITOS");
			this.principal.mostrarAtaques(this.evolucoes);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void evoluirProtecaoChuva(){
		int precoOferenda = 7000;
		
		if(!verificaPreco(precoOferenda)) {
			return;
		}
		
		if(existeEvolucao("PROTECAO_CHUVA_PEDRAS")) {
			return;
		}
		
		try {
			this.vila.getPrefeitura().retirarOferenda(precoOferenda);
			Thread.sleep(700000);
			this.evolucoes.add("PROTECAO_CHUVA_PEDRAS");
			this.principal.mostrarAtaques(this.evolucoes);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private boolean verificaPreco(int preco) {
		if(this.vila.getPrefeitura().getOferenda() >= preco) {
			return true;
		}	
		return false;
	}
	
	private boolean existeEvolucao(String evolucao) {
		if(this.evolucoes == null){
			return false;
		}
		for (String evolucaoExistente : evolucoes) {
			if(evolucaoExistente == evolucao) {
				return true;
			}
		}
		return false;
	}
	
	public void setAcaoTemplo(AcaoTemplo acao) {
		this.acaoTemplo = acao;
		
		synchronized (this) {
			notify();
		}
		
	}
	
	public void setQualEvolucao(String evolucao) {
		this.qualEvolucao = evolucao;
	}
	
	
	//-------------------------------------------------------
	public void lancarAtaque(String ataque) {
		if(ataque.equals("Nuvem de gafanhotos")){
			
		}
		
		if(ataque.equals("Morte dos primogênitos")){
		}
		
		if(ataque.equals("Chuva de pedras")){
		}
	}
	
	public void ataqueNuvem(){
		
	}
	
	public void ataqueMorte(){
		
	}
	
	public void ataqueChuva(){
		
	}
	
}
