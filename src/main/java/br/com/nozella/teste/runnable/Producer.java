package br.com.nozella.teste.runnable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import br.com.nozella.teste.exception.FullQueueException;
import br.com.nozella.teste.queue.Queue;
import br.com.nozella.teste.to.PrintJob;

public class Producer extends Thread implements Runnable {

    private Queue queue;
    private String dirPath;
    private boolean running;

    public Producer(String name, Queue queue) {
        super.setName(name);
        this.queue = queue;
    }

    public void run() {
        this.running = true;
        File dir = new File(this.dirPath);
        if (!dir.isDirectory()) {
            throw new RuntimeException("O caminho informado não é um diretorio valido.");
        }
        while (this.running) {
            this.consumeJobs(dir);
            this.waitForNewFiles();
        }
    }

    private synchronized void waitForNewFiles() {
        try {
            this.wait(60000);
        } catch (InterruptedException e) {
            this.running = false;
            e.printStackTrace();
        }
    }

    private synchronized void consumeJobs(File dir) {
        List<String> jobList = this.getJobList(dir);
        String[] jobArray = jobList.toArray(new String[jobList.size()]);
        for (int i = 0; i < jobArray.length; i++) {
            String fileName = jobArray[i].split("\\|")[0];
            Integer pageNumbers = Integer.valueOf(jobArray[i].split("\\|")[1]);
            try {
                this.queue.addBack(new PrintJob(fileName, pageNumbers));
                this.talk(String.format("produzindo arquivo '%s', número de páginas %s", fileName, pageNumbers));
                synchronized (this.queue) {
                    this.queue.notifyAll();
                }
                super.wait(this.getRandomTime());
            } catch (FullQueueException e) {
                this.waitJobEnd();
                --i;
            } catch (InterruptedException e) {
                this.running = false;
                e.printStackTrace();
            }
        }
    }

    /**
     * Random value between 1 and 5 seconds
     * 
     * @return value milliseconds
     */
    private long getRandomTime() {
        return (1L + (long) (Math.random() * ((5L - 1L) + 1L))) * 1000L;
    }

    private void waitJobEnd() {
        synchronized (this.queue) {
            try {
                this.talk("Fila cheia, aguardando espaço");
                this.queue.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> getJobList(File dir) {
        List<File> fileList = Arrays.asList(dir.listFiles(new FileFilter() {
            /**
             * Não ser um diretório e terminar em .job sem distingir maiuscula
             * ou minuscula, tal como: ".job", ".Job", ".jOb", ".JOB", etc...
             */
            public boolean accept(File file) {
                return !file.isDirectory() && file.getName().matches(".*\\.[jJ][oO][bB]");
            }
        }));
        List<String> jobList = new ArrayList<String>();
        for (File file : fileList) {
            jobList.addAll(this.openReadAndClose(file));
            file.renameTo(new File(file.getAbsolutePath().replaceAll("\\.[jJ][oO][bB]", ".queueded_" + new Date().getTime())));
        }
        return jobList;
    }

    /**
     * Particularmente eu usaria a biblioteca da Apache FileUtils, que faria
     * todo esse trabalho
     */
    private List<String> openReadAndClose(File file) {
        BufferedReader reader = null;
        List<String> list = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            while ((text = reader.readLine()) != null) {
                list.add(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public Producer setFilePath(String dirPath) {
        this.dirPath = dirPath;
        return this;
    }

    private void talk(String message) {
        System.out.println(String.format("#%s#: %s", super.getName(), message));
    }
}
