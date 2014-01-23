package br.com.nozella.teste.queue;

import br.com.nozella.teste.exception.FullQueueException;
import br.com.nozella.teste.to.PrintJob;

public interface Queue {

	void addBack(PrintJob job) throws FullQueueException;
	
	PrintJob removeFront();
	
	boolean isEmpty();
	
	int getNumberOfJobs();
}
