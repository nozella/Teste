package br.com.nozella.teste.runnable;

import br.com.nozella.teste.queue.Queue;

public class Producer extends Thread implements Runnable {

	private String name;
	private Queue queue;
	private String filePath;

	public Producer(String name, Queue queue) {
		this.name = name;
		this.queue = queue;
	}

	public Producer setFilePath(String filePath) {
		this.filePath = filePath;
		return this;
	}

	public void run() {
		System.out.println(this);
	}

	@Override
	public String toString() {
		return String.format("Producer [name=%s, queue=%s, filePath=%s]", name, queue, filePath);
	}

}
