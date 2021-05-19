package Classes;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


import tela.Principal;



public class ClienteTeste2 extends Thread{
	//Atributos
	private Socket conexao;
	private ObjectOutputStream saida;
	private ClienteTeste2 clienteRecebe;
	private boolean escutando;
	private Principal principal;
	private Jogador jogador;
	
	
	//Construtores
	
	public ClienteTeste2(Principal principal, Jogador jogador) {
		this.principal = principal;
		this.jogador = jogador;
	}
	
	private ClienteTeste2(Socket conexao, Principal principal) {
		this.conexao = conexao;
		this.principal = principal;
		this.escutando = true;
	}
	
	public void run() {
		
	}
	
	public void conectar(String host) {
		
		try {
			this.conexao = new Socket(host, 12345);
			this.saida = new ObjectOutputStream(this.conexao.getOutputStream());
			this.saida.writeObject("");
			this.saida.writeObject(this.jogador);
			this.clienteRecebe = new ClienteTeste2(conexao, this.principal);
			this.clienteRecebe.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void enviarMensagem(String mensagem) {
		try {
			this.saida.writeObject(mensagem);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
