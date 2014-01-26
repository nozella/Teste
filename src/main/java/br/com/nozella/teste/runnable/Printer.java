package br.com.nozella.teste.runnable;

import br.com.nozella.teste.queue.Queue;
import br.com.nozella.teste.to.PrintJob;

public class Printer extends Thread implements Runnable {

    private static final long MILLES_PER_PAGE = 500L;
    private Queue queue;
    private boolean running;
    private boolean working;

    public Printer(String name, Queue queue) {
        this.queue = queue;
        super.setName(name);
    }

    public void run() {
        this.talk("Ligando...");
        this.running = true;
        try {
            while (this.running) {
                this.printProcess();
            }
        } catch (InterruptedException e) {
        }
        this.talk("Impressora desligada");
    }

    private synchronized void printProcess() throws InterruptedException {
        if (this.queue.isEmpty()) {
            this.talk("Esperanado por trabalho de impressão...");
            synchronized (queue) {
                this.queue.wait();
            }
        } else {
            this.working = true;
            PrintJob job = queue.removeFront();
            this.talk(String.format("Imprimindo '%s'", job.getJobName()));
            super.wait(job.getNumberOfPages() * MILLES_PER_PAGE);
            this.talk(String.format("'%s' ok.", job.getJobName()));
            this.working = false;
        }
    }

    public void halt() {
        this.running = false;
        if (!this.working) {
            super.interrupt();
        }
    }

    private void talk(String message) {
        System.out.println(String.format("[%s]: %s", super.getName(), message));
    }
}
