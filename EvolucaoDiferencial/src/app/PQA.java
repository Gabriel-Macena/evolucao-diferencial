package app;

public class PQA {
	private int[][] matrizFluxos;
	private int[][] matrizDist;
	private int size;
	
	public int getElemFluxo(int i, int j) {
		return this.matrizFluxos[i][j];
	}
	
	public void setElemFluxo(int i, int j, int value) {
		this.matrizFluxos[i][j] = value;
	}
	
	public int getElemDist(int i, int j) {
		return this.matrizDist[i][j];
	}

	public void setElemDist(int i, int j, int value) {
		this.matrizDist[i][j] = value;
	}

	public int getSize() {
		return size;
	}

	public PQA(int size) {
		super();
		this.size = size;
		this.matrizFluxos = new int[size][size];
		this.matrizDist = new int[size][size];
	}
	
	public void printMatrizFluxos() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(getElemFluxo(i, j) + " ");
			}
			System.out.println();
		}
	}
	
	public void printMatrizDist() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(getElemDist(i, j) + " ");
			}
			System.out.println();
		}
	}
	
}
