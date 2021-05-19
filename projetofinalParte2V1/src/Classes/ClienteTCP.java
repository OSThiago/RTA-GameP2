package Classes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import tela.Principal;

public class ClienteTCP extends Thread {
	//Atributos
	private Principal principal;
	private boolean ouvindo;
	private ClienteTCP clienteRecebe;
	private Jogador jogador;
	
	// Atributos conexao
	private Socket conexao;
	private ObjectOutputStream saida;
	private ObjectInputStream entrada;
	
	// Construtores
	public ClienteTCP(Principal principal, Jogador jogador) {
		this.principal = principal;
		this.jogador = jogador;
		this.ouvindo = true;
	}

	public ClienteTCP(Socket conexao, Principal principal, Jogador jogador, ObjectOutputStream saida, ObjectInputStream entrada) {
		this.conexao = conexao;
		this.principal = principal;
		this.ouvindo = true;
		this.jogador = jogador;
		this.saida = saida;
	}

	// Metodos
	public void run() {

		String mensagem;
		ObjectInputStream entrada = null;
		try {
			entrada = new ObjectInputStream(this.conexao.getInputStream());
			while (this.ouvindo) {
				mensagem = (String) entrada.readObject();
				System.out.println(mensagem);
				this.principal.adicionarMensagem(mensagem);

			}
			entrada.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	// Metodos conexao

	public boolean conectar(String host, Jogador jogador) {

		System.out.println("Entrou na funcao conectar");
		try {
			this.conexao = new Socket(host, 12345);
			this.saida = new ObjectOutputStream(conexao.getOutputStream());
			this.saida.writeObject("Conectar");
			this.saida.writeObject(jogador);
			this.entrada = new ObjectInputStream(this.conexao.getInputStream());
			this.clienteRecebe = new ClienteTCP(conexao, this.principal, this.jogador, this.saida, this.entrada);
			this.clienteRecebe.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public void criarServidor(String ip) {
		System.out.println("entrou na funcao criarServidor");
		
		try {
			this.conexao = new Socket(ip, 12345);
			
			this.entrada = new ObjectInputStream(this.conexao.getInputStream());
			this.saida = new ObjectOutputStream(this.conexao.getOutputStream());
			
			saida.writeObject("Criar");
			saida.writeObject(this.jogador);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Mostrar na tela
		this.principal.adicionarJogador(this.jogador.getNomeJogador(), this.jogador.getCivilizacao(), this.jogador.getIpJogador(), "Aguardando jogadores teste");
		this.clienteRecebe = new ClienteTCP(this.conexao, this.principal, this.jogador, this.saida, this.entrada);
		this.clienteRecebe.start();
		
	}
	
	
	
	public void enviarMensagem(String mensagem) {
		try {
			this.saida.writeObject(mensagem);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean estaOuvindo() {
		return ouvindo;
	}

	public void setOuvindo(boolean ouvindo) {
		this.ouvindo = ouvindo;
	}

}
