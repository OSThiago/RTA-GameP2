package Classes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import tela.Principal;

public class ChatJogador extends Thread{
	//Atributos conexao
	private Socket conexao;
	private ObjectOutputStream saida;
	private Principal princpal;
	private ChatJogador recebeChat;
	private boolean escutando;
			
	//Construtores
	public ChatJogador(Socket conexao, Principal principal) {
		this.conexao = conexao;
		this.princpal = principal;
		this.escutando = true;
		
	}
		
	//Metodos conexao
	
	public void run() {
		String mensagem;
		ObjectInputStream entrada = null;
		
		try {
			entrada = new ObjectInputStream(this.conexao.getInputStream());
			while(this.escutando) {
				mensagem = (String) entrada.readObject();
				this.princpal.adicionarMensagem(mensagem);
			}
			entrada.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	
	public boolean conectar(String host, String nome) {
		
		try {
			this.conexao = new Socket(host, 12345);
			this.saida = new ObjectOutputStream(conexao.getOutputStream());
			this.saida.writeObject(nome);
			this.recebeChat = new ChatJogador(conexao,this.princpal);
			this.recebeChat.start();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void desconectar() {
		try {
			this.saida.writeObject("CMD|DESCONECTAR");
			this.recebeChat.escutando = false;
			this.saida.close();
			this.conexao.close();
		} catch (IOException e) {
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
