package br.com.nozella.teste.main;

import br.com.nozella.teste.queue.Queue;
import br.com.nozella.teste.queue.impl.CircularQueue;
import br.com.nozella.teste.runnable.Printer;
import br.com.nozella.teste.runnable.Producer;

public class PrinterApp {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Eh necessario:\n primeiro argumento: a capacidade da fila de impressao\n segundo argumento e demais: path completo dos arquivos onde constam a lista de documentos a imprimir");
		}
		try {
			Queue queue = new CircularQueue(Integer.valueOf(args[0]));
			new Printer("Printer", queue).run();
			for (int i = 1; i < args.length; i++) {
				new Producer("Producer" + i, queue).setFilePath(args[i]).run();
			}
		} catch (NumberFormatException e) {
			System.out.println("Eh necessario que o primeiro argumento seja um numero inteiro para definir a capacidade da fila de impressao");
		}

	}
}
