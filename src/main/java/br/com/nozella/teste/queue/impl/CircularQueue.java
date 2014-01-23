package br.com.nozella.teste.queue.impl;

import br.com.nozella.teste.exception.FullQueueException;
import br.com.nozella.teste.queue.Queue;
import br.com.nozella.teste.to.PrintJob;

public class CircularQueue implements Queue {

    private PrintJob[] printJobArray;

    public CircularQueue(int capacity) {
        this.printJobArray = new PrintJob[capacity];
    }

    public void addBack(PrintJob job) throws FullQueueException {
        synchronized (this) {
            int count = 0;
            for (PrintJob printJob : this.printJobArray) {
                if (printJob == null) {
                    printJob = job;
                    break;
                } else if (++count == this.printJobArray.length) {
                    throw new FullQueueException();
                }
            }
        }
    }

    public PrintJob removeFront() {
        synchronized (this) {
            PrintJob printJob = this.printJobArray[0];
            for (int i = 0; i < this.printJobArray.length - 1; i++) {
                this.printJobArray[i] = this.printJobArray[i + 1];
            }
            this.printJobArray[this.printJobArray.length - 1] = null;
            return printJob;
        }
    }

    public boolean isEmpty() {
        synchronized (this) {
            return this.printJobArray[0] == null;
        }
    }

    public int getNumberOfJobs() {
        synchronized (this) {
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
}