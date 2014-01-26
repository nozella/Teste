package br.com.nozella.teste.queue.impl;

import br.com.nozella.teste.exception.FullQueueException;
import br.com.nozella.teste.queue.Queue;
import br.com.nozella.teste.to.PrintJob;

public class CircularQueue implements Queue {

    private PrintJob[] printJobArray;

    public CircularQueue(int capacity) {
        this.printJobArray = new PrintJob[capacity];
    }

    public synchronized void addBack(PrintJob job) throws FullQueueException {
        if (this.printJobArray[printJobArray.length -1] != null) {
            throw new FullQueueException();
        }
        for (int i = 0; i < this.printJobArray.length; i++) {
            if (this.printJobArray[i] == null) {
                this.printJobArray[i] = job;
                this.notifyAll();
                break;
            }
        }
    }

    public synchronized PrintJob removeFront() {
        PrintJob printJob = this.printJobArray[0];
        for (int i = 0; i < this.printJobArray.length - 1; i++) {
            this.printJobArray[i] = this.printJobArray[i + 1];
        }
        this.printJobArray[this.printJobArray.length - 1] = null;
        this.notifyAll();
        return printJob;
    }

    public boolean isEmpty() {
        return this.printJobArray[0] == null;
    }

    public int getNumberOfJobs() {
        int count = 0;
        for (PrintJob printJob : this.printJobArray) {
            if (printJob != null) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }
}