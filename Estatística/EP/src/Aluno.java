

import java.lang.Math;
import java.util.*;
public class Aluno {
    private double c; //habilidade do aluno
    private Prova p;
    private double [] prob = new double[100]; // Guarda a probabilidade de acertar cada uma das 100 questões
    public Aluno(double c){
        this.c = c; 
    }
    public Aluno(Prova p) {
    	this.p = p;
    }
    
    public void calcula(ArrayList<Double> a, ArrayList<Double> b){
        double [] resposta = new double [100];
        for(int i=0; i<100; i++){
            resposta[i] = (Math.pow(Math.E,(a.get(i)*(this.c-b.get(i))))/(1+Math.pow(Math.E,a.get(i)*(this.c-b.get(i))))); //Formula tri
        
        }
        this.setProb(resposta);
    }


	public int fazAProva(int[] prova) {
		Random r = new Random();
		int certas = 0;
		for(int i=0; i<prova.length; i++) {
			double aleatoria = r.nextDouble();
			if( aleatoria > (1- this.getProb()[prova[i]])) {
				certas++;
			}
		}
		
		return certas;
	}

	public int[] fazAProvaRespostas(int[] prova) {
		Random r = new Random();
		int[] respostas = new int[prova.length];
		for(int i=0; i<prova.length; i++) {
			double aleatoria = r.nextDouble();
			if( aleatoria > (1- this.getProb()[prova[i]])) 
				respostas[i] = 1;
			else 
				respostas[i] = 0;
		}
		
		return respostas;
	}
	public double [] getProb() {
		return prob;
	}


	public void setProb(double [] prob) {
		this.prob = prob;
	}
	public Prova getP() {
		return p;
	}
	public void setP(Prova p) {
		this.p = p;
	}
	public double getC() {
		return c;
	}
	public void setC(double c) {
		this.c = c;
	}
	public double calculaTheta(ArrayList<Double> a, ArrayList<Double> b) {
		
		/** TODO
		 * Metodo que calcula o theta de um dado aluno
		 * f'(x) = xi * ( ai - (ai*e^(ai*theta)/e^(a*b) + e^(ai*theta)) + (f-xi) (ai*e^(ai*theta)/e^(a*b) + e^(ai*theta)
		 * f''(x) = 
		 */
		double epsilon = 0.00001; //Grau de precisão
		double thetaK = 0.5;//
		double theta;
		double f;
		double flinha;
		int[] prova = this.getP().getRespostas();
		int tProva = this.getP().getRespostas().length;
		double diferenca;
		do {
			f=0;
			flinha=0;
			theta = thetaK;
			for (int i = 0; i < tProva; i++) {
				double ai = a.get(i);
				double bi = b.get(i);
				int xi = prova[i];
				double eatb = Math.pow(Math.E, ai*(theta-bi));
				double cima = ai*eatb;
				double baixo= 1+eatb;
				double cimaLinha = (ai*ai)*eatb;
				double baixoLinha = baixo*baixo;
				f+= xi*(ai-(cima/baixo))+(1-xi)*(-(cima/baixo));
				flinha += -(cimaLinha/baixoLinha);
			}
			
			thetaK = theta-(f/flinha);
			
			//System.out.println("f = "+ f +" flinha = "+ flinha);
			//System.out.println(thetaK+" = "+theta );
			if((thetaK-theta)>0) diferenca = thetaK-theta;
			else diferenca = theta - thetaK;
		}while(diferenca>epsilon);
		
		return theta;
	}
	/*
	public double secante(ArrayList<Double> a, ArrayList<Double> b) {
		
		// TODO Calcular theta
		double epsilon = 0.0000001; //Grau de precisão
		double thetaProx = 0;
		double thetaAnt = -0.5;
		double theta = 0.5;
		double fAtual;
		double fAnt;
		int[] prova = this.getP().getRespostas();
		do {
			thetaAnt = theta;
			theta = thetaProx;
			fAtual= this.calculaFuncao(a, b, theta, prova);
			fAnt = this.calculaFuncao(a, b, thetaAnt, prova);
			thetaProx = theta - (fAnt*((theta- thetaAnt)/(fAtual-fAnt)));
			
			
		}while((thetaProx-theta)>0 ? thetaProx-theta >= epsilon : (thetaProx-theta) <= epsilon);
		
		
		return theta;
	}
	
	
	private double calculaFuncao(ArrayList<Double> a, ArrayList<Double> b, double theta, int[] prova) {
		// TODO Auto-generated method stub
		double f=0;
		for(int i=0; i< prova.length; i++) {
			
			double ai = a.get(i);
			double bi = b.get(i);
			int xi = prova[i];
			//System.out.println(ai + " "+ bi + " "+ xi);
			double eatheta = Math.pow(Math.E, (ai*theta)); //e^(ai*theta)
			double eab = Math.pow(Math.E, (ai*bi));//e^(ai*bi)
			double eabtheta = Math.pow(Math.E, (ai*(theta-bi)));
			double q = (ai*eabtheta)/(1 + eabtheta);//(ai*e^(ai*theta)/e^(a*b) + e^(ai*theta)
			f += xi*(ai - q)+ (1-xi) * -q;
		}
		//System.out.println("f: "+f);
		return f;
	}
	
	*/
	public double[] guardarNotas(int[] prova, int quantidade) {
    	double[] notas = new double[quantidade];
    	
    	for(int i=0; i<quantidade;i++) {
	    	notas[i] = this.fazAProva(prova);
	    	
    	}

    	for (int i = 0; i < notas.length; i++) {
			notas[i] = Math.round((notas[i]/prova.length)*100);
			//System.out.println(notas[i]);
		}

    	
    	return notas;
    }
	
	public double[] calculaMediaVariancia(int[] prova) {
		// TODO Calcula Media e Variancia da provas
		double media=0;
		double variancia=0;
		for (int i = 0; i < prova.length; i++) {
			double p = this.getProb()[prova[i]];
			media+= p;
			variancia+= p*(1-p);
		}
		double[] r= {media, variancia};
		return r;
	}
	public double[] calculaIntervaloDeConfianca(int[] prova, double[] mV, int qntTestes) {
		// TODO 
		double soma = 0;
		double[] notas = this.guardarNotas(prova, qntTestes);
		for (int i = 0; i < notas.length; i++) {
			soma+=notas[i];
		}
		//System.out.println(soma);
		double media = soma/qntTestes;
		System.out.println(media);
		
		double sigma = Math.pow(mV[1], 0.5);
		double[] intervalo = new double[2];
		double pivotal = (soma- qntTestes*mV[0])/sigma*Math.pow(qntTestes, 0.5);
		double aux=0;
		for (int i = 0; i < notas.length; i++) {
			aux+= (notas[i] - media)*(notas[i] - media);
		}
		double desvio = Math.pow(aux/qntTestes, 0.5);
		intervalo[0] = media-(1.645*desvio);
		intervalo[1] = media+(1.645*desvio);
		//System.out.println(intervalo[0]+ " "+ intervalo[1]);
		System.out.println((1.645*desvio));
		return intervalo;
	}

}
