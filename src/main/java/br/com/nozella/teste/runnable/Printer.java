package br.com.nozella.teste.runnable;

import br.com.nozella.teste.queue.Queue;

public class Printer extends Thread implements Runnable {
	
	private String name;
	private Queue queue;
	
	public Printer(String name, Queue queue) {
		this.name = name;
		this.queue = queue;
	}

	public void run() {
		System.out.println(this);
	}

	@Override
	public String toString() {
		return String.format("Printer [name=%s, queue=%s]", name, queue);
	}
}
