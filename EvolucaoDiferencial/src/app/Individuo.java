package app;

public class Individuo {
	private int[] valores;
	private int evaluated;
	private int generation;
	
	public Individuo(int size, int generation) {
		this.valores = new int[size];
		this.evaluated = -1;
		this.generation = generation;
	}
	
	public void setEvaluated(int evaluated) {
		this.evaluated = evaluated;
	}

	public int getEvaluated() {
		return this.evaluated;
	}

	public void setElemValores(int index, int value) {
		valores[index] = value;
	}

	public int getElemValores(int index) {
		return valores[index];
	}
	
	public void printIndividuo() {
		int size = valores.length;
		System.out.println("Geração: " + generation);
		for (int i = 0; i < size; i++) {
			System.out.print(i + 1 + " ");
		}
		System.out.println();
		for (int i = 0; i < size; i++) {
			System.out.print(getElemValores(i) + 1 + " ");
		}
		System.out.println();
		System.out.println(evaluated);
		System.out.println();
	}
	
	
}