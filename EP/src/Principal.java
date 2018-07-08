

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.*;

public class Principal{
// arrays com as questÃµes e suas respectivas dificuldades
    static ArrayList <Double> q = new ArrayList<>();
    static ArrayList <Double> dificuldade = new ArrayList<>();  
    static ArrayList <Aluno> alunos = new ArrayList<>(); 
//alunos
    static Aluno a1 = new Aluno(-1.0);
    static Aluno a2 = new Aluno(-0.5);
    static Aluno a3 = new Aluno(0.0);
    static Aluno a4 = new Aluno(0.5);
    static Aluno a5 = new Aluno(1);
		

//metodo para ler o arquivo e atribuis os valores aos arrays
    static public void letxt()throws FileNotFoundException{
        Scanner sc = new Scanner(new FileReader("questoes.txt"));
        while(sc.hasNext()) {
            q.add(Double.parseDouble(sc.next()));
            dificuldade.add(Double.parseDouble(sc.next()));
        }
	sc.close();
    }
    static public void leRespostatxt() throws FileNotFoundException {
    	Scanner sc = new Scanner(new FileReader("respostas.txt"));
    	int[][] leitura = new int[100][2000];
    	int i = 0;
    	while(sc.hasNext()) {
    		for(int j=0; j<2000; j++) {
    			leitura[i][j] = Integer.parseInt(sc.next());
    		}
    		i++;
    	}
    
	    int[][] respostas = new int[2000][100];
	    for (int j = 0; j < respostas.length; j++) {
			for (int j2 = 0; j2 < respostas[0].length; j2++) {
				respostas[j][j2] = leitura[j2][j];
			}
		}
	 
	    for (int j = 0; j < respostas.length; j++) {
			Prova p = new Prova(100, respostas[j]);
			Aluno a = new Aluno(p);
			alunos.add(a);
		}

    	sc.close();
    }
    
    
    /////////////////////////////////////////////////////////////////
    // Parte 2.1
    /* 
     * Recebe o tamanho da prova e quantas provas devem ser testadas 
     * Gera provas aletorias do tamanho pedido e na quantidade pedida
     * Cada aluno faz essas provas
     * o resultado as provas dos 4 primeiros aluno é comparado com o aluno 5
     * O método retorna a probabilidade de o aluno 5 ter tirado nota mais alta ques os outros 
     */
    
    
    public static double[] provas(int tamanho, int quantidade){
    	int[] prova = new int[tamanho];
    	int[] notas = new int[5];
    	int[] provasComparadas = new int[4];
    	double[] probabilidades = new double[4];
    	List<Integer> numeros = new ArrayList<Integer>();
    	for (int i = 0; i < 100; i++) {
    	    numeros.add(i);
    	}
    	for(int i=0; i<quantidade;i++) {
    		Collections.shuffle(numeros);
	    	for (int j = 0; j < tamanho; j++) {
	    	   prova[j] = numeros.get(j);
	    	}
	    	notas[0] = a1.fazAProva(prova);
	    	notas[1] = a2.fazAProva(prova);
	    	notas[2] = a3.fazAProva(prova);
	    	notas[3] = a4.fazAProva(prova);
	    	notas[4] = a5.fazAProva(prova);
	    	
	    	for(int j=0; j<4; j++) {
	    		if(notas[j]<= notas[4]) {
	    			provasComparadas[j]++;
	    		}
	    	}
    	}
    	
    	
    	for(int i=0; i<4; i++) {
    		System.out.println("Prob["+i+"]: "+provasComparadas[i]);
    	}
    	System.out.println(quantidade);
    	for(int i=0; i<4; i++) {
    		
    		probabilidades[i] = (double)(provasComparadas[i])/quantidade;
    		
    	}
    	
    	return probabilidades;
    	
	    	
    }
    ///////////////////////////////////////////////////////////////////
    // Parte2.2
    /* 
     * Recebe dois alunos 
     * E retorna um array de questoes ordenado pela probabilidade
     * de o aluno b ser melhor que o aluno a
     */
    
    private static int[] compararProbabilidade(Aluno a, Aluno b) {
		double[] diferencas = new double[100];
		for (int i = 0; i < diferencas.length; i++) {
			diferencas[i] = b.getProb()[i] - a.getProb()[i];
		}
		
		 HashMap<Double, Integer> difMap = new HashMap<Double, Integer>();
		 for (int i = 0; i < diferencas.length; i++) {
			difMap.put(diferencas[i], i);
		}
		 
		Arrays.sort(diferencas);
		int[] questoes = new int[100];
		for (int i = 0, j=questoes.length-1; i < questoes.length && j>-1; i++,j--) {
			questoes[i] = difMap.get(diferencas[j]);
			
		}
		
		return questoes; 
	}
    
    /*
     * Recebe dois aluno e o tamanho da prova desejada
     * Chama o método comparaProbabilidae() e guarda o seu 
     * retorno na variavel questoes
     * pegamos as n primeira questoes
     * onde n se refere a variavel tamanho
     * e retorna a melhor do tamanho pedido
     */
    
    private static int[] melhorProva(Aluno a , Aluno b, int tamanho) {
    	int[] prova = new int[tamanho];
    	int[] questoes = compararProbabilidade(a, b);
    	for (int i = 0; i < prova.length; i++) {
			prova[i] = questoes[i];
		}
    	
		return prova;
    	
    }
    
    /*
     * Muito parecido com o metodo provas() a diferença é
     * que esse já recebe a prova como parametro
     * logo não prcisa gerar provas aletorias
     * de resto faz exatamente a mesma coisa
     */
    
    private static double[] compararProvas(int[] prova, int quantidade) {

    	int[] notas = new int[5];
    	int[] provasComparadas = new int[4];
    	double[] probabilidades = new double[4];
    	
    	for(int i=0; i<quantidade;i++) {
	    	notas[0] = a1.fazAProva(prova);
	    	notas[1] = a2.fazAProva(prova);
	    	notas[2] = a3.fazAProva(prova);
	    	notas[3] = a4.fazAProva(prova);
	    	notas[4] = a5.fazAProva(prova);
	    	
	    	for(int j=0; j<4; j++) {
	    		if(notas[j]<= notas[4]) {
	    			provasComparadas[j]++;
	    		}
	    	}
    	}
    	
    	
    	for(int i=0; i<4; i++) {
    		System.out.println("Prob["+i+"]: "+provasComparadas[i]);
    	}
    	System.out.println(quantidade);
    	for(int i=0; i<4; i++) {
    		
    		probabilidades[i] = (double)(provasComparadas[i])/quantidade;
    		
    	}
    	
    	return probabilidades;
    	
    }
    ////////////////////////////////////////////////////////////////////////
    // Parte 2.3
    /*
     * Muito parecido com o comparar provas
     * mas este método armazena as n notas (n=quantidade)
     * depois as ordena e transforma em porcentagem
     * e retorna uma matriz com as notas dos 5 alunos
     */
    private static double[][] guardarNotas(int[] prova, int quantidade) {
    	double[][] notas = new double[5][quantidade];
    	
    	for(int i=0; i<quantidade;i++) {
	    	notas[0][i] = a1.fazAProva(prova);
	    	notas[1][i] = a2.fazAProva(prova);
	    	notas[2][i] = a3.fazAProva(prova);
	    	notas[3][i] = a4.fazAProva(prova);
	    	notas[4][i] = a5.fazAProva(prova);
    	}
    	
    	for (int i = 0; i < notas.length; i++) {
			Arrays.sort(notas[i]);
		}
    	
    	for (int i = 0; i < notas.length; i++) {
			for (int j = 0; j < notas[i].length; j++) {
				notas[i][j] = ((notas[i][j]*100)/prova.length);
				//System.out.println(notas[i][j]);
			}
		}
    	return notas;
    }
    
    /*
     * Recebe uma matriz de notas e o coeficiente de confianca
     * retorna o intervalo de confianca
     */
    
    private static double[][] intervaloDeConfianca(double[][] notas, double coeficiente){
    	double[][] resul = new double[5][2];
		int fimIntervalo = (int)(notas[0].length-(notas[0].length*coeficiente));
    	
    	
    	for (int i = 0; i < notas.length; i++) {
    		double diferenca = 10000000;
    		resul[i][0] = notas[i][0];
    		resul[i][1] = notas[i][fimIntervalo];
			for (int j = 0, k = fimIntervalo; k < notas[i].length; j++, k++) {
				//System.out.println(notas[i][k]-notas[i][j]+ " "+ j + " "+ k);
				if(notas[i][k]-notas[i][j] < diferenca) {
					resul[i][0] = notas[i][j];
					resul[i][1] = notas[i][k];
					diferenca = notas[i][k]-notas[i][j];
				}
			}
		}
    	
    	return resul;
    	
    	
    	
    }
    
    /////////////////////////////////////////////////////////////////////////
    /**
     * 3.2
     * @param args
     * @throws FileNotFoundException
     */
    private static double[] compararThetas(int[] prova, int quantidade) throws FileNotFoundException {
    	
    	int[][] notas = new int[5][prova.length];
    	double[] probabilidades = new double[4];
    	int[] thetasComparados = new int[4];
    	double[] thetas = new double[5];
    	PrintStream ps = new PrintStream("thetas1.txt");
    	//System.out.println(prova.length);
    	for(int i=0; i<quantidade;i++) {
    		
	    	notas[0] = a1.fazAProvaRespostas(prova);
	    	notas[1] = a2.fazAProvaRespostas(prova);
	    	notas[2] = a3.fazAProvaRespostas(prova);
	    	notas[3] = a4.fazAProvaRespostas(prova);
	    	notas[4] = a5.fazAProvaRespostas(prova);
	    	//System.out.println(i);
	    	Prova p1 = new Prova(prova.length, notas[0]);
	    	Prova p2 = new Prova(prova.length, notas[1]);
	    	Prova p3 = new Prova(prova.length, notas[2]);
	    	Prova p4 = new Prova(prova.length, notas[3]);
	    	Prova p5 = new Prova(prova.length, notas[4]);
	    	a1.setP(p1);
	    	ps.print(a1.getC()+" ");
	    	a2.setP(p2);
	    	ps.print(a2.getC()+" ");
	    	a3.setP(p3);
	    	ps.print(a3.getC()+" ");
	    	a4.setP(p4);
	    	ps.print(a4.getC()+" ");
	    	a5.setP(p5);
	    	ps.print(a5.getC()+" ");
	    	a1.setC(a1.calculaTheta(q, dificuldade));
	    	//System.out.println("Aqui1");
	    	a2.setC(a2.calculaTheta(q, dificuldade));
	    	//System.out.println("Aqui2");
	    	a3.setC(a3.calculaTheta(q, dificuldade));
	    	//System.out.println("Aqui3");
	    	a4.setC(a4.calculaTheta(q, dificuldade));
	    	//System.out.println("Aqui4");
	    	//System.out.println(Arrays.toString(notas[4]));
	    	a5.setC(a5.calculaTheta(q, dificuldade));
	    	//System.out.println("Aqui5");
	    	thetas[0] = a1.getC();
	    	thetas[1] = a2.getC();
	    	thetas[2] = a3.getC();
	    	thetas[3] = a4.getC();
	    	thetas[4] = a5.getC();
	    	for(int j=0; j<4; j++) {
	    		if(thetas[j] < thetas[4])
	    			thetasComparados[j]++;
	    	}
	    	ps.println();;
    	}
    	ps.close();
    	for(int i=0; i<4; i++) {
    		System.out.println("Prob["+i+"]: "+thetasComparados[i]);
    	}
    	System.out.println(quantidade);
    	for(int i=0; i<4; i++) {
    		
    		probabilidades[i] = (thetasComparados[i])*1.0/quantidade;
    		
    	}
    	System.out.println("Cheguei");
    	return probabilidades;
    	
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //3.3
    static void bubbleSort(double arr[])
    {
        int n = arr.length;
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (arr[j] > arr[j+1])
                {
                    // swap temp and arr[i]
                    double temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
    }
    private static double[][] guardarThetas(int[] prova, int quantidade) throws FileNotFoundException {
    	
    	int[][] notas = new int[5][prova.length];
    	double[][] thetas = new double[5][quantidade];
    	//System.out.println(prova.length);
    	PrintStream ps = new PrintStream("thetas.txt");
    	for(int i=0; i<quantidade;i++) {
    		
	    	notas[0] = a1.fazAProvaRespostas(prova);
	    	notas[1] = a2.fazAProvaRespostas(prova);
	    	notas[2] = a3.fazAProvaRespostas(prova);
	    	notas[3] = a4.fazAProvaRespostas(prova);
	    	notas[4] = a5.fazAProvaRespostas(prova);
	    	//System.out.println(i);
	    	Prova p1 = new Prova(prova.length, notas[0]);
	    	Prova p2 = new Prova(prova.length, notas[1]);
	    	Prova p3 = new Prova(prova.length, notas[2]);
	    	Prova p4 = new Prova(prova.length, notas[3]);
	    	Prova p5 = new Prova(prova.length, notas[4]);
	    	a1.setP(p1);
	    	ps.print(a1.getC()+" ");
	    	a2.setP(p2);
	    	ps.print(a2.getC()+" ");
	    	a3.setP(p3);
	    	ps.print(a3.getC()+" ");
	    	a4.setP(p4);
	    	ps.print(a4.getC()+" ");
	    	a5.setP(p5);
	    	ps.print(a5.getC()+" ");
	    	a1.setC(a1.calculaTheta(q, dificuldade));
	    	//System.out.println("Aqui1");
	    	a2.setC(a2.calculaTheta(q, dificuldade));
	    	//System.out.println("Aqui2");
	    	a3.setC(a3.calculaTheta(q, dificuldade));
	    	//System.out.println("Aqui3");
	    	a4.setC(a4.calculaTheta(q, dificuldade));
	    	//System.out.println("Aqui4");
	    	//System.out.println(Arrays.toString(notas[4]));
	    	a5.setC(a5.calculaTheta(q, dificuldade));
	    	//System.out.println("Aqui5");
	    	thetas[0][i] = a1.getC();
	    	thetas[1][i] = a2.getC();
	    	thetas[2][i] = a3.getC();
	    	thetas[3][i] = a4.getC();
	    	thetas[4][i] = a5.getC();
	    	ps.println();
    	}
    	ps.close();
    	
    	for (int i = 0; i < notas.length; i++) {
			//Arrays.sort(thetas[i]);
			bubbleSort(thetas[i]);
		}
    	
    	
    	ps.close();
    	return thetas;
    	
    }
    
    
    
    
    
    
    public static void main(String[] args) throws FileNotFoundException {
    	///////////////////////////////////////////////////////
    	int qntTestes = 100000;
    	//Parte 1
        letxt();
        
        //metodos para atribuir aos alunos as suas respectivas chances de acertar cada questÃ£o
        a1.calcula(q,dificuldade);
        a2.calcula(q,dificuldade);
        a3.calcula(q,dificuldade);
        a4.calcula(q,dificuldade);
        a5.calcula(q,dificuldade);
        System.out.println("Chance de cada aluno acertar cada questÃ£o.");
        System.out.println("\nAlunos    A1                  A2                 A3                 A4                  A5");
        for(int i=0; i<100; i++){
            System.out.print("Questao " + (i+1));
            System.out.print(" " + a1.getProb()[i] + " ");
            System.out.print(" " + a2.getProb()[i] + " ");
            System.out.print(" " + a3.getProb()[i] + " ");
            System.out.print(" " + a4.getProb()[i] + " ");
            System.out.print(" " + a5.getProb()[i] + " ");
            System.out.println();
        }
        ////////////////////////////////////////////////////////////
        //Parte 2.1
        int[]questoes = new int [4];
        questoes[0]=10;
        questoes[1]=20;
        questoes[2]=50;
        questoes[3]=100;
        PrintStream ps = new PrintStream("I1.txt");
	    for(int i = 0; i<questoes.length; i++) { 
	    	double[] prob = provas(questoes[i], qntTestes);
		    for(int j=0; j<prob.length; j++) {
	        	System.out.println("Probalidade de que o Aluno 5 tenha uma notas maior que o Aluno "+(j+1) +": "+ prob[j]);
	        	ps.print(prob[j]+" ");
	        }
		    ps.println("");
	   	}
       
        ps.close();  
        /////////////////////////////////////////////////////////////
        //Parte 2.2
        ArrayList<int[]> melhoresProvas = new ArrayList<>();
        ps = new PrintStream("I2.txt");
        for(int i = 0; i<questoes.length-1; i++) { 
        	melhoresProvas.add(melhorProva(a4, a5, questoes[i]));
        	System.out.println( "Melhor Prova de "+(questoes[i]));
        	for(int j=0; j<melhoresProvas.get(i).length; j++) {
        		System.out.print( melhoresProvas.get(i)[j]+ " ");
	        	ps.print(melhoresProvas.get(i)[j]+" ");
	        }
        	System.out.println();
        	ps.println("");
        	double[] prob = compararProvas(melhoresProvas.get(i), qntTestes);
		    for(int j=0; j<prob.length; j++) {
	        	System.out.println("Probalidade de que o Aluno 5 tenha uma notas maior que o Aluno "+(j+1) +": "+ prob[j]);
	        	ps.print(prob[j]+" ");
	        }
		    ps.println("");
	   	}
        
        
        
        
        ps.close();
        
        ///////////////////////////////////////////////////////////////
        //Parte 2.3
        HashMap<Integer, double[][]> notas = new HashMap<Integer, double[][]>();
	    for(int i = 0; i<questoes.length-1; i++) { 
	    	notas.put(questoes[i], guardarNotas(melhoresProvas.get(i), qntTestes));
	   	}
	    int[] aux = new int[100];
	    for (int i = 0; i < aux.length; i++) {
			aux[i] = i;
		}
	    notas.put(100, guardarNotas(aux,qntTestes));
 
	    ps = new PrintStream("I3.txt");
	    for(int i = 0; i<questoes.length; i++) {
		    double[][] intervalos = intervaloDeConfianca(notas.get(questoes[i]), 0.1);
	    	System.out.println("Questoes: "+ questoes[i]);
	    	for(int j=0; j<intervalos.length; j++) {
	        	System.out.println("Intervalo Confianca(Aluno "+(j+1) +"): "+ intervalos[j][0]+ "  "+ intervalos[j][1]+ " ");
	        	ps.print(intervalos[j][0]+ " "+ intervalos[j][1]+ "  ");
	        }
		    ps.println("");
	   	}
       
        ps.close();  
        /**
         * Parte 3
         */
        leRespostatxt();
        /**
         * 3.1
         * Calculo do theta dos alunos
         * Primeiro o arquivo respostas foi lido, nele continha respostas de 2000 alunos as 100 questões
         * Essa respostas foram guardadas na ArrayList alunos
         * Utilizando o estimador de maxima verossimilhança chagamos a formula 
         * log(L(f(x|theta) = produtorio(xi *formulatrie + (1-xi)*formulatrie) te explico depois isso
         * essa formula foi derivada duas vezes 
         * usamos as duas derivadas para aplicar o metodo de Newton para achar o theta de cada aluno
         */
        ps = new PrintStream("II1.txt");
        for (int i = 0; i < alunos.size(); i++) {
        	Aluno a = alunos.get(i);
			a.setC(a.calculaTheta(q, dificuldade));
			System.out.println(a.getC() + " "+ a.getP().getCertas());
			ps.println(a.getC());
		}
        ps.close();
        /**
         * 3.2
         * Calcular theta dos 5 alunos
         * Utilizando a mesma funcão da parte anterior
         * Mas só que dessa vez fazendo os alunos a1, a2, a3 ,a4 e a5 fazerem um milhão de provas
         * essa provas são as mesmas escolhidas na parte 2.2
         * foi estimado um theta ´para cada aluno em cada uma das provas
         * e com isso foi calculado a probabilidade de o theta do a5 ser maior do q os dos outros alunos
         * 
        */
        melhoresProvas.add(aux);
        ps = new PrintStream("II2.txt");
        for(int i = 0; i<questoes.length; i++) { 
        	
        	double[] prob = compararThetas(melhoresProvas.get(i), qntTestes);
        	
		    for(int j=0; j<prob.length; j++) {
		    	
	        	System.out.println("Probalidade de que o Aluno 5 tenha uma notas maior que o Aluno "+(j+1) +": "+ prob[j]);
	        	ps.print(prob[j]+" ");
	        }
		    ps.println("");
	   	}
        ps.close();
        
        /**
         * 3.3
         */
        Map<Integer, double[][]> thetas = new HashMap<Integer, double[][]>();
        for (int i = 0; i < questoes.length; i++) {
			thetas.put(questoes[i], guardarThetas(melhoresProvas.get(i), qntTestes));
		}
        
        ps = new PrintStream("saida.txt");
        for (int i = 0; i < thetas.get(10)[0].length; i++) {
			ps.println(thetas.get(10)[0][i]+ " ");
		}
		
        ps.close();
        
        ps = new PrintStream("II3.txt");
	    for(int i = 0; i<questoes.length; i++) {
		    double[][] intervalos = intervaloDeConfianca(thetas.get(questoes[i]), 0.1);
	    	System.out.println("Questoes: "+ questoes[i]);
	    	for(int j=0; j<intervalos.length; j++) {
	        	System.out.println("Intervalo Confianca(Aluno "+(j+1) +"): "+ intervalos[j][0]+ "  "+ intervalos[j][1]+ " ");
	        	ps.print(intervalos[j][0]+ " "+ intervalos[j][1]+ "  ");
	        }
		    ps.println("");
	   	}
       
        ps.close();
        
        
    }

	
}

