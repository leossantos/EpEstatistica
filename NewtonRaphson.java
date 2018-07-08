/*********************************************************************/
/**   <Leonardo Soares Santos>                   <10284782>         **/
/**                                                                 **/
/**   <15/05/2017>                                             		**/
/*********************************************************************/
class NewtonRaphson {
	/*
		Depósitos realizados, com saldo final
	*/
	static double[] depositos;
	
	/*
		Datas correspondentes aos depósitos feitos e saldo final
	*/
	static int[] datas;
	
	/*
		Você pode incluir métodos que ache necessários aqui. Contudo, apenas newton
		será executado diretamente. Então, para que seus métodos rodem, deve haver um
		caminho em que sejam rodados a partir de newton (ex: newton chama um método que
		chama outro. Nesse caso, os 2 acabarão sendo rodados via newton)
	*/
	

	/*
		Método para cálculo dos juros de aplicação, segundo Newton-Raphson
	*/
	static double newton(double epsilon) {
		// seu código vai aqui
		if (epsilon<=0||epsilon>=1) return(-1);//Caso o epsilon seja maior que 1 e menor que 0
		double j;//Taxa de Juros
		double jk=0.5;//Jk+1
		double f;//f(j)
		double flinha;//f'(j)
		int t;//Expoente
		
		do{	
		
			f=0;//zera a função 
			flinha=0;//	zera a derivada			
			j=jk;//atualiza valor de j 
		
			for (int i=0;i<depositos.length-1 ;i++ ) {//soma dos depósitos
		
				t=datas[datas.length-1]-datas[i];//tf-t[i]
				f+=depositos[i]*Math.pow(1+j,t);//f(j)=Di*(1+j)^t...
				flinha+=((t)*depositos[i])*(Math.pow(1+j,t-1));//f'(j)=t*Di*(1+j)^t-1...
		
			}
		
			f-=depositos[depositos.length-1];//f(j)=f(j)-Soma
			jk=j-(f/flinha);//definir próximo valor de j
		
		}while((jk-j)>0 ? jk-j>=epsilon : (jk-j)<=epsilon);//Teste se módulo de jk-j>=epsilon
		
		return j;//ele retorna o valor de j
	}
	
	
	
	
	/*
		Use isso apenas para seus testes. Ele pode até ser removido para entrega. Use-o para abastecer valores nos arranjos e então testar o método newton.
		
		O MAIN SERa COMPLETAMENTE IGNORADO.
	*/
	public static void main(String[] args) {
		/*
		depositos = new double[5];
	    depositos[0] = 2000;
	    depositos[1] = 123.5;
	    depositos[2] = 358.5;
	    depositos[3] = 23;
	    depositos[4] = 3500.68;
	    datas = new int[5];
	    datas[0] = 1;
	    datas[1] = 3;
	    datas[2] = 5;
	    datas[3] = 6;
	    datas[4] = 10;
		*/
		depositos = new double[6];
	    depositos[0] = 1000;
	    depositos[1] = 1200;
	    depositos[2] = 100;
	    depositos[3] = 1100;
	    depositos[4] = 900;
	    depositos[5] = 5000;
	    datas = new int[6];
	    datas[0] = 3;
	    datas[1] = 4;
	    datas[2] = 5;
	    datas[3] = 7;
	    datas[4] = 9;
	    datas[5] = 12;

		System.out.println(newton(0.001));
	}
}
