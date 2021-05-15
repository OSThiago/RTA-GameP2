package Classes;

import tela.Principal;

public class Prefeitura extends Thread{
	//Atributos
	private Principal principal;
	private Vila vila;
	private int comida;
	private int ouro;
	private int oferendasDeFe;
	private AcaoPrefeitura acaoPrefeitura;
	private String evoluirQual;
	
	//Construtores
	public Prefeitura(Principal principal, Vila vila) {
		this.principal = principal;
		this.vila = vila;
		this.comida = 150;
		this.ouro = 100;
		this.oferendasDeFe = 0;
		this.acaoPrefeitura = AcaoPrefeitura.PARADO;
	}
	
	//Metodos
	
	public void run() {
		
		while(true) {
			switch(acaoPrefeitura) {
			case PARADO:
				parar();
				break;
			case CRIANDO:
				criarAldeao();
				break;
			case EVOLUINDO:
				evoluir(this.evoluirQual);
				break;
			default:
				break;
			
			}
		}
	}
	
	public void setFuncaoPrefeitura(AcaoPrefeitura acao) {
		this.acaoPrefeitura = acao;
		synchronized (this) {
			notify();
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
	
	
	private void criarAldeao() {
		 
		if(this.vila.verificaEPaga(100, 0, 0)) {
			
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			Aldeao novoAldeao = new Aldeao(this.vila,this.vila.gerarIdAldeao(), this.principal);
			this.vila.addAldeoes(novoAldeao);
			novoAldeao.start();
	
			//System.out.println("Novo aldeao criado");
			int numero = novoAldeao.getNumero();
			String funcao = novoAldeao.getFuncao();
			//System.out.println(numero +" "+funcao);

			principal.adicionarAldeao(String.valueOf(numero + 1), funcao);
			principal.mostrarAldeao(numero+1, "Sem funcao");
		}else {
			System.out.println("Não tem recurso suficiente");
		}
		setFuncaoPrefeitura(AcaoPrefeitura.PARADO);
	}
	
	
	/************************* Comida *****************************/
	public void receberComida(int quantidade) {
		this.comida += quantidade;
	}
	
	public void retirarComida(int quantidade) {
		this.comida -= quantidade;
	}
	
	public int getComida() {
		return this.comida;
	}
	
	/************************* Ouro *****************************/
	
	public void receberOuro(int quantidade) {
		this.ouro += quantidade;
	}
	
	public void retirarOuro(int quantidade) {
		this.ouro -= quantidade;
	}
	
	public int getOuro() {
		return this.ouro;
	}
	
	/************************* Oferendas de fé *****************************/
	
	public void receberOferenda(int quantidade) {
		this.oferendasDeFe += quantidade;
	}
	
	public void retirarOferenda(int quantidade) {
		this.oferendasDeFe -= quantidade;
	}
	
	public int getOferenda() {
		return this.oferendasDeFe;
	}
	
	/************************* Evoluir *****************************/
	public void qualEvoluir(String qual) {
		this.evoluirQual = qual;
	}
	
	private void evoluir(String qual) {
		
		if(qual.equals("Evolução de aldeão")) {
			System.out.println("Evoluir aldeao");
			evoluirAldeao();
		}else if(qual.equals("Evolução de fazenda")) {
			System.out.println("Evoluir fazenda");
			evoluirFazenda();
			
		}else if(qual.equals("Evolução de mina de ouro")) {
			System.out.println("Evoluir mina");
			evoluirMina();
		}
		setFuncaoPrefeitura(AcaoPrefeitura.PARADO);
	}
	
	private void evoluirAldeao() {
		int precoComida = 5000;
		int precoOuro = 5000;
		int precoOferenda = 0;
		
		if(!podePagar(precoComida,precoOuro,precoOferenda)) {
			System.out.println("não tem recurso suficiente");
			return;
		}
		
		/*Primeiro verifica se todos os aldeos já são evoluidos,
		caso ja estejam evoluidos ele não paga de novo
		*/
		
		if(!verificaEvolucao("Evolução de aldeão")) {
			return;
		}
		
		try {
			retirarComida(precoComida);
			retirarOuro(precoOuro);
			Thread.sleep(10000);
			for (Aldeao aldeao : this.vila.getListaAldeos()) {
				aldeao.evoluir();
		}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private void evoluirFazenda() {
		int precoComida = 500;
		int precoOuro = 5000;
		int precoOferenda = 0;
		
		if(!podePagar(precoComida,precoOuro,precoOferenda)) {
			System.out.println("não tem recurso suficiente");
			return;
		}
		
		if(!verificaEvolucao("Evolução de fazenda")) {
			System.out.println("ja estao todos evoluidos");
			return;
		}
		
		try {
			retirarComida(precoComida);
			retirarOuro(precoOuro);
			Thread.sleep(100000);
			for (Fazenda fazenda: this.vila.getListaFazendas()) {
				if(fazenda.getNivel() == 1) {
					fazenda.evoluir();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void evoluirMina() {
		int precoComida = 2000;
		int precoOuro = 1000;
		int precoOferenda = 0;
		
		if(!podePagar(precoComida,precoOuro,precoOferenda)) {
			System.out.println("não tem recurso suficiente");
			return;
		}
		
		if(!verificaEvolucao("Evolução de fazenda")) {
			System.out.println("ja estao todos evoluidos");
			return;
		}
		
		try {
			retirarComida(precoComida);
			retirarOuro(precoOuro);
			Thread.sleep(100000);
			for (Mina mina: this.vila.getListaMinas()) {
				if(mina.getNivel() == 1) {
					mina.evoluir();
				}
			}
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}
		
		
	}
	
	
	private boolean podePagar(int comida, int ouro, int oferenda) {
		if(this.getComida() >= comida && this.getOuro() >= ouro && this.getOferenda() >= oferenda) {
			this.retirarComida(comida);
			this.retirarOuro(ouro);
			this.retirarOferenda(oferenda);
			return true;
		}
		return false;
	}
	
	
	private boolean verificaEvolucao(String qual) {
		if(qual.equals("Evolução de aldeão")) {
			System.out.println("Verifica Evoluir aldeao");
			for (Aldeao aldeao : this.vila.getListaAldeos()) {
				if(aldeao.getNivel() == 1) {
					return true;
				}
			}
		}else if(qual.equals("Evolução de fazenda")) {
			System.out.println("Verifica Evoluir fazenda");
			for (Fazenda fazenda : this.vila.getListaFazendas()) {
				if(fazenda.getNivel() == 1) {
					return true;
				}
			}
		}else if(qual.equals("Evolução de mina de ouro")) {
			System.out.println("Verifica Evoluir mina");
			for (Mina mina : this.vila.getListaMinas()) {
				if(mina.getIdMina() == 1) {
					return true;
				}
			}
		}
		return false;
	}
	
}
