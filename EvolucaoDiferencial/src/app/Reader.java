package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reader {
	private String file;

	public Reader(String file) {
		super();
		this.file = file;
	}
	
	public PQA processFile() throws FileNotFoundException {
		Scanner s;
		PQA matrizes;
		s = new Scanner(new File(file));
		int size = s.nextInt();
		matrizes = new PQA(size);
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				matrizes.setElemDist(i, j, s.nextInt());
			}
		}
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
					matrizes.setElemFluxo(i, j, s.nextInt());
			}
		}
		
		s.close();

		return matrizes;
	}
	
}
