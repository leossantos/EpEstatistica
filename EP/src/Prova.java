public class Prova {
	private int tamanho;
	private int[] respostas;
	private int certas;
	public Prova(int tamanho, int[] respostas) {
		this.tamanho = tamanho;
		this.respostas = respostas;
		setCertas(respostas);
	}
	private void setCertas(int[] respostas) {
		// TODO Auto-generated method stub
		int certas=0;
		for (int i = 0; i < respostas.length; i++) {
			if(respostas[i] == 1) certas++;
		}
		this.certas = certas;
	}
	public int getCertas() {
		return this.certas;
	}
	public int getTamanho() {
		return tamanho;
	}
	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}
	public int[] getRespostas() {
		return respostas;
	}
	public void setRespostas(int[] prova) {
		this.respostas = prova;
	}
}
