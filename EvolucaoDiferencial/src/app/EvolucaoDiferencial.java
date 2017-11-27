package app;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EvolucaoDiferencial {
	
	private final float F = 0.1f; //fator de mutação
	private final float CRmax = 0.8f; //taxa de cruzamento máxima
	private final float CRmin = 0.1f; //taxa de cruzamento mínima
	private final int k = 4; //usado na progressão da taxa de cruzamento
	private final int NP = 75; //número de indivíduos da população
	private final int GenMax = 1000; //máximo de gerações (iterações)
	
	private final Random RND = new Random();
	
	private PQA matrizes;
	
	public EvolucaoDiferencial(PQA matrizes) {
		super();
		this.matrizes = matrizes;
	}
	
	private Individuo getBest(List<Individuo> populacao, boolean start) {
		Individuo bestIndividuo = null;
		if (start) {
			//Avalia todas as soluções inicialmente
			for (int i = 0; i < NP; i++) {
				Individuo individuo = populacao.get(i);
				evaluate(individuo);
			}			
		}
		for (int i = 0; i < NP; i++) {
			Individuo individuo = populacao.get(i);
			if (bestIndividuo == null) {
				bestIndividuo = individuo;
			}
			else if (individuo.getEvaluated() <= bestIndividuo.getEvaluated()) {
				bestIndividuo = individuo;
			}
		}
		return bestIndividuo;
	}
	
	private void gerarIndividuos(List<Individuo> populacao, int geracao) {
		int size = matrizes.getSize();
		for (int i = 0; i < NP; i++) {
			Individuo novoIndividuo = new Individuo(size, geracao);
			for (int j = 0; j < size; j++) {
				int val = Math.round(RND.nextFloat() * size);
				novoIndividuo.setElemValores(j, val);
			}
			corrigirValores(novoIndividuo);
			populacao.add(novoIndividuo);
		}
	}
	
	private void corrigirValores(Individuo ind) {
		List<Integer> disponiveis = new LinkedList<Integer>();
		List<Integer> usados = new LinkedList<Integer>();
		
		int size = matrizes.getSize();
		
		//Preenche a lista de valores disponíveis
		for (int i = 0; i < size; i++) {
			disponiveis.add(i);
		}
		
		//Verifica se os valores estão dentro dos limites		
		for (int i = 0; i < size; i++) {
			int valorAtual = ind.getElemValores(i);
			if (valorAtual < 0) {
				ind.setElemValores(i, 0);
			} else if (valorAtual > (size - 1)) { 
				ind.setElemValores(i, size - 1);
			}
		}
		//Corrige os valores para evitar repetições
		for (int i = 0; i < size; i++) {
			int valorAtual = ind.getElemValores(i);
			if (usados.indexOf(valorAtual) != -1) {
				int novoValor = disponiveis.remove(RND.nextInt(disponiveis.size()));
				ind.setElemValores(i, novoValor);
				usados.add(novoValor);
			}
			else {
				//Valor já está correto
				int novoValor = disponiveis.remove(disponiveis.indexOf(valorAtual));
				usados.add(novoValor);				
			}
		}
	}
	
	private int evaluate(Individuo ind) {
		if (ind.getEvaluated() != -1)
			return ind.getEvaluated();	//Evita cálculos repetidos para o mesmo indivíduo
		int retorno = 0;
		int size = matrizes.getSize();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i != j) {
					int d1 = ind.getElemValores(i);
					int d2 = ind.getElemValores(j);
					retorno += matrizes.getElemFluxo(d1, d2) * matrizes.getElemDist(i, j);
				}
			}
		}
		ind.setEvaluated(retorno);
		return retorno; 
	}
	
	public void loop() {
		List<Individuo> populacao = new LinkedList<Individuo>();
		int currentGen = 0;
		gerarIndividuos(populacao, currentGen);
		Individuo bestIndividuo = getBest(populacao, true);
		while (currentGen < GenMax) {
			//Otimização de CR (valores baixos demoram a convergir, valores altos convergem muito rápido)
			float CR = (float) (CRmax + (CRmin - CRmax) * Math.pow((1 - currentGen/GenMax), k));
			for (int i = 0; i < NP; i++) {	
				int r1, r2, r3;
				do {
					r1 = RND.nextInt(NP);
				}
				while (r1 == i);
				do {
					r2 = RND.nextInt(NP);					
				}
				while(r1 == r2 || r2 == i);
				do {
					r3 = RND.nextInt(NP);
				}
				while (r1 == r3 || r2 == r3 || r3 == i);
				
				Individuo ind1 =  populacao.get(r1);
				Individuo ind2 =  populacao.get(r2);
				Individuo ind3 =  populacao.get(r3);
				
				Individuo currentIndividuo = populacao.get(i);
				Individuo novoIndividuo = new Individuo(matrizes.getSize(), currentGen);
				
				int jRand = RND.nextInt(matrizes.getSize());
				for (int j = 0; j < matrizes.getSize(); j++) {
					if (RND.nextFloat() <= CR || jRand == j) {
						int valorMutado = Math.round(ind1.getElemValores(j) + F * (ind2.getElemValores(j) - ind3.getElemValores(j)));
						novoIndividuo.setElemValores(j, valorMutado);
					}
					else {
						int valor = currentIndividuo.getElemValores(j);
						novoIndividuo.setElemValores(j, valor);
					}
				}
				corrigirValores(novoIndividuo);
				if (evaluate(novoIndividuo) < evaluate(currentIndividuo)) {
					populacao.set(i, novoIndividuo);
				}
			}
			bestIndividuo = getBest(populacao, false);
			currentGen++;
		}
		System.out.println("------ BEST OF ALL ------");
		bestIndividuo.printIndividuo();
	}
}
