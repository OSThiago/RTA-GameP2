package Classes;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tela.Principal;
import tela.enumerador.SituacaoInicio;


public class ServidorTCP extends Thread{
	//Atributos
	private Socket conexao;
	private List<ObjectOutputStream> saidas;
	private SituacaoInicio situacao;
	private boolean vivo;
	private Principal principal;
	
	//Construtor
	public ServidorTCP(Socket conexao, List<ObjectOutputStream> saidas, Principal principal) {
		System.out.println("Cliente conectado: "+ conexao.getInetAddress().getHostAddress());
		this.conexao = conexao;
		this.saidas = saidas;
		this.vivo = true;
		this.situacao = SituacaoInicio.INICIAL_CRIAR;
		this.principal = principal;
	}
	
	public ServidorTCP(Principal principal) {
		this.vivo = true;
		this.situacao = SituacaoInicio.INICIAL_CRIAR;
		this.principal = principal;
	}
	
	//Metodos
	public void run() {
		
		while(this.vivo) {
			switch(this.situacao) {
			case INICIAL_CONECTAR:
				//System.out.println("entrou levantando");
				levantarServidor();
				break;
			case CONECTADO:
				
				conectado();
			default:
				break;
			}
		}
	}
	
	
	private void conectado() {
		System.out.println("entrou no na funcao conectado");
		DateFormat horaMinuto = new SimpleDateFormat("HH:mm");
		try {
			ObjectInputStream entrada = new ObjectInputStream(this.conexao.getInputStream());
			ObjectOutputStream saida = new ObjectOutputStream(this.conexao.getOutputStream());
			
			synchronized (saida) {
				this.saidas.add(saida);
			}
			String mensagem = (String)entrada.readObject();
			super.setName(mensagem);
			
			
			
			try{
				while(!(mensagem = (String) entrada.readObject()).equals("COMANDO SAIR SERVIDOR")){
					synchronized(this.saidas){
						String msg = super.getName()+"("+horaMinuto.format(new Date())+"): "+mensagem;
						this.principal.adicionarMensagem(msg);
						
						for(ObjectOutputStream enviarMsg : this.saidas){
							enviarMsg.writeObject(msg);
						}
					}
				}
			}catch(SocketException e){}
			synchronized(this.saidas){
				this.saidas.remove(saida);
			}
			saida.close();
			entrada.close();
			this.conexao.close();
			this.vivo = false;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public void levantarServidor() {
		System.out.println("Levantando servidor...");
		List<ObjectOutputStream> saidas = new ArrayList<ObjectOutputStream>();
		
		try {
			@SuppressWarnings("resource")
			//Instancia servidor ouvindo a porta 12345
			ServerSocket servidor = new ServerSocket(12345);
			while(true) {
				//Bloqueia até receber um pedido de conexao
				Socket conexao = servidor.accept();
				System.out.println("Servidor conectou");
				
				this.situacao = SituacaoInicio.CONECTADO;
				
				//---------->  mudei aqui pois precisava trocar a situacao do novo servidor
				ServidorTCP novoServidorTCP = new ServidorTCP(conexao,saidas,this.principal);
				novoServidorTCP.setSituacao(situacao);
				novoServidorTCP.start();
				
				//(new ServidorTCP(conexao, saidas)).start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void setSituacao(SituacaoInicio situacao) {
		this.situacao = situacao;
	}
	
	
}
