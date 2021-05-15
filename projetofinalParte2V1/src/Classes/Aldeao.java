package Classes;

import tela.Principal;

public class Aldeao extends Thread{
	//Atributos
	private Principal principal;
	private boolean vivo;
	private Vila vila;
	private int numero;
	private String funcao;
	private String construcao;
	private AcaoAldeao funcaoAtual;
	private Fazenda fazenda;
	private Mina mina;
	private Templo templo;
	private int nivel;
	
	//Construtores
	public Aldeao(Vila vila, int id, Principal principal) {
		this.principal = principal;
		this.vila = vila;
		this.numero = id;
		this.funcao = "parar"; // parar, construir, cultivar, minerar, orar
		this.funcaoAtual = AcaoAldeao.PARADO;
		this.vivo = true;
		this.nivel = 1;
	}
	
	//Metodos
	
	public void run() {
		while(this.vivo) {
			//System.out.println(funcao);
			switch(this.funcaoAtual) {
			case PARADO:
				this.funcao = "Parado";
				this.principal.mostrarAldeao(this.numero+1, this.funcao);
				parar();
				break;
			case CONSTRUINDO:
				this.funcao = "construindo " + this.construcao;
				this.principal.mostrarAldeao(this.numero+1, this.funcao);
				construir();
				break;
			case CULTIVANDO:
				this.funcao = "cultivando";
				this.principal.mostrarAldeao(this.numero+1, this.funcao);
				cultivar();
				break;
			case MINERANDO:
				this.funcao = "minerando";
				this.principal.mostrarAldeao(this.numero+1, this.funcao);
				minerar();
				break;
			case ORANDO:
				this.funcao = "orando";
				this.principal.mostrarAldeao(this.numero+1, this.funcao);
				orar();
				break;
			case SACRIFICAR:
				this.funcao = "sacrificado";
				this.principal.mostrarAldeao(this.numero+1, this.funcao);
				sacrificar();
				break;
			default:
				setFuncaoAtual(AcaoAldeao.PARADO);
				break;
			}
		}
	}
	
	private void parar() {
		
		System.out.println(this.numero +" Parado");
		synchronized (this) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
		
	private void cultivar() {
		System.out.println(this.fazenda.getQtdAldeoes());
		if(this.fazenda.getQtdAldeoes() > 5 * this.fazenda.getNivel()) {
			setFuncaoAtual(AcaoAldeao.PARADO);
		}else {
			this.fazenda.cultivar(this.nivel);
			this.fazenda.transportar(this.nivel);
		}	
	}
	
	private void minerar(){
		System.out.println(this.mina.getQtdAldeoes());
		if(this.mina.getQtdAldeoes() > 5 * this.mina.getNivel()){
			setFuncaoAtual(AcaoAldeao.PARADO);
		}else{
			this.mina.minerar(this.nivel);
			this.mina.transportar(this.nivel);
		}
	}
	
	private void orar(){
		if(this.vila.getTemplo() != null){
			System.out.println("Orando!");
			setTemplo(this.vila.getTemplo());
			this.templo.produzirOferendaDeFe();
		}else{
			System.out.println("Você não tem um templo pra orar!");
			setFuncaoAtual(AcaoAldeao.PARADO);
		}
	}
	
	private void sacrificar(){
		if(this.vila.getTemplo() != null){
			this.templo.receberSacrificio();
			this.vivo = false;
		}else{
			System.out.println("Você não tem um templo pra sacrificar!");
			setFuncaoAtual(AcaoAldeao.PARADO);
		}
	}
	
	public void evoluir() {
			if(this.nivel == 1) {
				this.nivel = 2;
			} else {
				System.out.println("Ja esta evoluido");
			}	
		}
	
	
	
	public void construir() {
		
		System.out.println(this.construcao + " < -----------------------");
		
		if(this.construcao == "Fazenda") {
			if(this.vila.verificaEPaga(100, 500, 0)){ // comida, ouro, oferenda
				construirFazenda();	
			}else {
				System.out.println("não tem recurso suficente");
			}
		}
		
		if(this.construcao == "Mina de ouro") {
			if(this.vila.verificaEPaga(1000, 0, 0)){// comida, ouro, oferenda
				construirMina();
			}
		}
		
		if(this.construcao == "Templo"){
			if(this.vila.verificaEPaga(2000, 2000, 0) && this.vila.getTemplo() == null)
			construirTemplo();
		}
		
		if(this.construcao == "Maravilha"){
			construirMaravilha();
		}
		
		System.out.println(this.numero + " Terminou de Construir");
		setFuncaoAtual(AcaoAldeao.PARADO);
	}
	
	/***************** Construcoes *******************/
	
	private void construirFazenda() {
		try {
			Thread.sleep(30000);
			Fazenda fazenda = new Fazenda(this.vila, this.vila.gerarIdFazenda(), this.principal);
			this.vila.addFazenda(fazenda);
			this.principal.adicionarFazenda(String.valueOf(this.vila.gerarIdFazenda() ),"");
			this.principal.mostrarFazenda(this.vila.gerarIdFazenda(), "" );
			System.out.println("construindo fazenda");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void construirMina() {
		try {
			Thread.sleep(40000);
			Mina mina = new Mina(this.vila, this.vila.gerarIdMina(), this.principal);
			this.vila.addMina(mina);
			this.principal.adicionarMinaOuro(String.valueOf(this.vila.gerarIdMina()), "AldeoesA");
			this.principal.mostrarMinaOuro(this.vila.gerarIdMina(), "AldeoesB");
			System.out.println("construindo Mina de Ouro");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void construirTemplo() {
		try {
			if(this.vila.getTemplo() == null){
				Thread.sleep(100000);
				System.out.println("Pode ser habilitado!");
				Templo templo = new Templo(this.vila, this.principal);
				this.vila.setTemplo(templo);
				this.principal.habilitarTemplo();
				this.principal.mostrarOferendaFe(this.vila.getPrefeitura().getOferenda());
				templo.start();
				System.out.println("construindo templo...");
			}else{
				System.out.println("O templo já está habilitado!");
		}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private void construirMaravilha(){
			while(this.funcaoAtual == AcaoAldeao.CONSTRUINDO && this.construcao == "Maravilha") {
				if(this.vila.verificaEPaga(1, 1, 0)) {
					this.vila.getMaravilha().produzirTijolo();
				}else {
					System.out.println("sem recurso suficiente");
					setFuncaoAtual(funcaoAtual.PARADO);
				}
				
			}
			
	}
	
	/***************** SET *******************/
	
	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public void setConstrucao(String construcao) {
		this.construcao = construcao;
	}
	
	public void setFuncaoAtual(AcaoAldeao acao) {
		this.funcaoAtual = acao;
		
		if(this.fazenda != null && acao != AcaoAldeao.CULTIVANDO) {
			System.out.println("chegou pra remover");
			this.fazenda.retirarDaLista(this.numero);
		}
		if(this.mina != null && acao != AcaoAldeao.MINERANDO) {
			System.out.println("chegou pra remover");
			this.mina.retirarDaLista(this.numero);
		}
		synchronized (this) {
			this.notify();
		}
	}
	
	
	
	
	public void trocaFazenda(int fazenda) {
		if(this.fazenda == null) {
			return;
		}
		if(this.fazenda.getIdFazenda() != fazenda) {
			System.out.println("esta trocando de fazenda");
			this.fazenda.retirarDaLista(this.numero);
		}	
	}
	
	public void trocaMina(int mina) {
		
		if(this.mina == null) {
			return;
		}
		if(this.mina.getIdMina() != mina) {
			System.out.println("esta trocando de mina");
			this.mina.retirarDaLista(this.numero);
		}
	}
	
	public void setFazenda(Fazenda fazenda) {
		this.fazenda = fazenda;
	}
	
	public void setMina(Mina mina) {
		this.mina = mina;
	}
	
	public void setTemplo(Templo t){
		this.templo = t;
	}
	
	
	/***************** GET *******************/
	public String getFuncao() {
		return this.funcao;
	}
	
	public boolean isVivo() {
		return this.vivo;
	}
	
	public int getNumero() {
		return this.numero;
	}
	
	public String getConstrucao() {
		return this.construcao;
	}
	
	public Fazenda getFazenda() {
		return this.fazenda;
	}
	
	public Mina getMina() {
		return this.mina;
	}
	
	public int getNivel() {
		return this.nivel;
	}
	
}
