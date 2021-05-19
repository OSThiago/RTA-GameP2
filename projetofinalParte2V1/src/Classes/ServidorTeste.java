package Classes;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServidorTeste extends Thread{
	//Atributos
	private Socket conexao;
	private List<ObjectOutputStream> saidas;
	private List<Jogador> jogadores;
	
	
	//Construtores
	public ServidorTeste() {
		List<ObjectOutputStream> saidas = new ArrayList<ObjectOutputStream>();
		List<Jogador> jogadores = new ArrayList<Jogador>();
		
		try {	
			ServerSocket servidor = new ServerSocket(12345);
			while(true) {
				Socket conexao = servidor.accept();
				(new ServidorTeste(conexao,saidas,jogadores)).start();
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	
	private ServidorTeste(Socket conexao, List<ObjectOutputStream> saidas, List<Jogador> jogadores) {
		this.conexao = conexao;
		this.saidas = saidas;
		this.jogadores = jogadores;
	}
	
	//Metodos
	
	public void run() {
		
		
		try {
			String funcao;
			ObjectInputStream entrada = new ObjectInputStream(this.conexao.getInputStream());
			ObjectOutputStream saida = new ObjectOutputStream(this.conexao.getOutputStream());
			
			
			try {
				while(!(funcao = (String) entrada.readObject()).equals("CMD| DESCONECTAR")) {
					
					switch(funcao) {
					case "Criar":
						criarJogo(entrada,saida);
						System.out.println("Criou um novo jogo");
						
						break;
					case "Conectar":
						conectarJogador(entrada, saida);
						System.out.println("conectou um jogador");
						break;
					
					case "mensagem":
						mandarMensagem(saida, entrada);
						
					}
					
					
				}
				
				
			}catch (Exception e) {
				synchronized(this.saidas){
					this.saidas.remove(saida);
				}
				saida.close();
				entrada.close();
				this.conexao.close();
			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		
		
	}
	
	private void criarJogo(ObjectInputStream entrada, ObjectOutputStream saida) {
			try {
				//Faz um cast para receber o jogador e adicionar na lista
				Jogador jogadorHost = (Jogador) entrada.readObject();
				System.out.println("Recebeu jogador: " + jogadorHost.getNomeJogador());
				synchronized (this.jogadores) {
					this.jogadores.add(jogadorHost);
				}
				synchronized (this.saidas) {
					this.saidas.add(saida);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
	}
	
	private void conectarJogador(ObjectInputStream entrada, ObjectOutputStream saida) {
		
		try {
			Jogador jogador = (Jogador) entrada.readObject();
			System.out.println("Recebeu jogador: " + jogador.getNomeJogador());
			synchronized (this.jogadores) {
				this.jogadores.add(jogador);
			}
			
			synchronized (this.saidas) {
				this.saidas.add(saida);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void mandarMensagem(ObjectOutputStream saida, ObjectInputStream entrada) {
		try {
			String mensagem = (String) entrada.readObject();
			//DateFormat horaMinuto = new SimpleDateFormat("HH:mm");
			
			for (ObjectOutputStream enviarMensagens : this.saidas) {
				enviarMensagens.writeObject(mensagem);
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	
}
