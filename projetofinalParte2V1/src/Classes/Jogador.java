package Classes;

import tela.Principal;

public class Jogador {
	// Atributos
	private Principal principal;
	private String nomeJogador;
	private String civilizacao;
	private String ipJogador;
	private Vila vila;
	

	// Construtores
	public Jogador(String nome, String civilizacao, String ip, Principal principal) {
		this.principal = principal;
		this.nomeJogador = nome;
		this.ipJogador = ip;
		this.civilizacao = civilizacao;
		this.vila = new Vila(principal);
	}

	// Metodos
	public Vila getVila() {
		return this.vila;
	}

	public String getNomeJogador() {
		return nomeJogador;
	}

	public String getCivilizacao() {
		return civilizacao;
	}
	
	public String getIpJogador() {
		return ipJogador;
	}
	
	
}
