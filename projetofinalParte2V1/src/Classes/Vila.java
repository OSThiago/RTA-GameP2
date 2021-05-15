package Classes;



import java.util.ArrayList;
import java.util.List;

import tela.Principal;

public class Vila {
	//Atributos
	private Principal principal;
	private Prefeitura prefeitura;
	private List<Aldeao>aldeoes = new ArrayList<Aldeao>();
	private List<Fazenda>fazendas = new ArrayList<Fazenda>();
	private List<Mina>minas = new ArrayList<Mina>();
	private Templo templo;
	private Maravilha maravilha;
	
	
	//Construtores
	public Vila(Principal principal) {
		this.principal = principal;
		this.prefeitura = new Prefeitura(this.principal,this);
		this.prefeitura.start();
		this.maravilha = new Maravilha(this, this.principal);
		iniciarVilla();
	}
	
	//Metodos
	
	
	
	/****************** lista aldeoes *************************/
	public int gerarIdAldeao() {
		//System.out.println("Novo id Aldeao:" + this.aldeoes.size() );
		return this.aldeoes.size();
	}
	
	public void addAldeoes(Aldeao aldeao) {
		this.aldeoes.add(aldeao);
	}
	
	public Aldeao getAldeao(int id) {
		return this.aldeoes.get(id);
	}
	
	public List<Aldeao> getListaAldeos() {
		return this.aldeoes;
	}
	
	/****************** lista fazendas *************************/
	public void addFazenda(Fazenda fazenda) {
		this.fazendas.add(fazenda);
	}
	
	public Fazenda getFazenda(int id) {
		return this.fazendas.get(id);
	}
	
	public int gerarIdFazenda() {
		return this.fazendas.size();
	}
	
	public List<Fazenda> getListaFazendas() {
		return this.fazendas;
	}
	
	/****************** lista Mina *************************/
	public void addMina(Mina mina) {
		this.minas.add(mina);
	}
	
	public Mina getMina(int id) {
		return this.minas.get(id);
	}
	
	public int gerarIdMina() {
		return this.minas.size();
	}
	
	public List<Mina> getListaMinas() {
		return this.minas;
	}
	
	/****************** templo *************************/
	public void setTemplo(Templo t){
		this.templo = t;
	}
	
	public Templo getTemplo(){
		return this.templo;
	}
	
	/****************** maravilha *************************/
	public void setMaravilha(Maravilha m){
		this.maravilha = m;
	}
	
	public Maravilha getMaravilha(){
		return this.maravilha;
	}
	
	/****************** prefeitura *************************/
	public Prefeitura getPrefeitura() {
		return this.prefeitura;
	}
	
	
	public boolean verificaEPaga(int comida, int ouro, int oferenda) {
		if(this.prefeitura.getComida() >= comida && this.prefeitura.getOuro() >= ouro && this.prefeitura.getOferenda() >= oferenda) {
			this.prefeitura.retirarComida(comida);
			this.prefeitura.retirarOuro(ouro);
			this.prefeitura.retirarOferenda(oferenda);
			this.principal.mostrarComida(this.prefeitura.getComida());
			this.principal.mostrarOuro(this.prefeitura.getOuro());
			this.principal.mostrarOferendaFe(this.prefeitura.getOferenda());
			return true;
		}
		return false;
	}
	
	
	private void iniciarVilla() {
		
		this.principal.mostrarComida(this.getPrefeitura().getComida());
		this.principal.mostrarOuro(this.getPrefeitura().getOuro());
        
        
        //5 aldeoes
        for(int i = 0; i < 5; i++) {
        	//comandoPrefeituraCriarAldeao();
        	Aldeao aldeao = new Aldeao(this, i, this.principal);
        	this.addAldeoes(aldeao);
        	aldeao.start();
    		this.principal.adicionarAldeao(String.valueOf(i+1), "fazendo nada");
    		this.principal.mostrarAldeao(i+1, "continua fazendo nada");
        }
        //1 fazenda
        Fazenda fazenda = new Fazenda(this, gerarIdFazenda(), this.principal);
        this.addFazenda(fazenda);
        this.principal.adicionarFazenda("1", "aldeoesA");
        this.principal.mostrarFazenda(1, fazenda.stringListaId());
        
        
        //1 mina de ouro
		Mina mina = new Mina(this, gerarIdMina(), this.principal);
        this.addMina(mina);
        this.principal.adicionarMinaOuro("1", "aldeoesA");
        this.principal.mostrarMinaOuro(1, mina.stringListaId());
		
	}
	
}
