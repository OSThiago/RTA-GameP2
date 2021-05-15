package Classes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import tela.Principal;

public class Jogador extends Thread{
	//Atributos
	private Principal principal;
	private String nomeJogador;
	private String civilizacao;
	private boolean vivo;
	private Vila vila;
	private Jogador jogadorRecebe;
	
	
	//Atributos conexao
	private Socket conexao;
	private ObjectOutputStream saida;
	
	
	//Construtores
	public Jogador(String nome, String civilizacao, Principal principal) {
		this.principal = principal;
		this.nomeJogador = nome;
		this.civilizacao = civilizacao;
		this.vivo = true;
		this.vila = new Vila(principal);
	}
	 
	public Jogador(Socket conexao, Principal principal) {
		this.conexao = conexao;
		this.principal = principal;
		this.vivo = true;
	}
	
	
	
	//Metodos
	public void run() {
		
		
		
		while(this.vivo) {

		}
		
	}
	
	
	//Metodos conexao
	
	public boolean conectar(String host, String nome) {
		try {
			this.conexao = new Socket(host, 12345);
			this.saida = new ObjectOutputStream(conexao.getOutputStream());
			this.saida.writeObject(nome);
			this.jogadorRecebe = new Jogador(conexao, this.principal);
			this.jogadorRecebe.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void enviarMensagem(String mensagem) {
		try {
			this.saida.writeObject(mensagem);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public Vila getVila() {
		return this.vila;
	}
	
	
	public String getNomeJogador() {
		return nomeJogador;
	}
	
	

	public String getCivilizacao() {
		return civilizacao;
	}

	public boolean isVivo() {
		return vivo;
	}

	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}
	
	
	
}
