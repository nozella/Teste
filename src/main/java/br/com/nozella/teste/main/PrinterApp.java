package br.com.nozella.teste.main;

import br.com.nozella.teste.queue.Queue;
import br.com.nozella.teste.queue.impl.CircularQueue;
import br.com.nozella.teste.runnable.Printer;
import br.com.nozella.teste.runnable.Producer;

public class PrinterApp extends Thread {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Eh necessario:\n "
            		+ "primeiro argumento: a capacidade da fila de impressao\n "
                    + "segundo argumento e demais: path completo dos arquivos onde constam a lista de documentos a imprimir");
            return;
        }
        Queue queue = new CircularQueue(Integer.valueOf(args[0]));
        String[] pathArray = new String[args.length -1];
        for (int i = 1; i < args.length; i++) {
            pathArray[i - 1] = args[i];
        }
        new PrinterApp(queue, pathArray).start();
    }

    private Queue queue;
    private String[] pathArray;

    public PrinterApp(Queue queue, String[] pathArray) {
        super.setName("PrinterApp");
        this.queue = queue;
        this.pathArray = pathArray;
    }

    public void run() {
        new Printer("Printer", queue).start();
        for(int i = 0; i < this.pathArray.length; i++) {
            new Producer("Producer" + (i+1), this.queue).setFilePath(this.pathArray[i]) .start();;
        }
    }
}
