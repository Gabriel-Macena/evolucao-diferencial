package app;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Reader reader = new Reader(args[0]);
		try {
			PQA matrizes = reader.processFile();
			EvolucaoDiferencial main = new EvolucaoDiferencial(matrizes);
			main.loop();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
