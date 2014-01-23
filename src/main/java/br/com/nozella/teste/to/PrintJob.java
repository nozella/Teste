package br.com.nozella.teste.to;

public class PrintJob {

	private String jobName;
	private int numberOfPages;
	
	public PrintJob(String jobName, int numberOfPages) {
		this.jobName = jobName;
		this.numberOfPages = numberOfPages;
	}
	
	public String getJobName() {
		return jobName;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}
}
