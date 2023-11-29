import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.util.*;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		Queue<PCB> jobQueue = new LinkedList();
		Queue<PCB> readyQueue = new LinkedList();
		usedMemory memory = new usedMemory(8192);

//		int memorySize = 8192;

		ReadJob read = new ReadJob(jobQueue);
		LoadJob load = new LoadJob(jobQueue, readyQueue, memory);

		read.start();
		read.join();
		load.start();

		try {
			Scanner input = new Scanner(System.in);
			System.out.println("Select a scheduling algorithm: ");
			System.out.println("1. First-Come-First-Serve (FCFS)");
			System.out.println("2. Shortest-Job-First (SJF)");
			System.out.println("3. Round-Robin (RR-3)");
			System.out.println("4. Round-Robin (RR-5)");
			int algorithm = input.nextInt();

			switch (algorithm) {
			case 1:
				System.out.println("Your choice : First-Come-First-Serve (FCFS) ");
				Scheduler.FCFS(readyQueue , memory);
				break;
			case 2:
				System.out.println("Your choice : Shortest-Job-First (SJF)");
				Scheduler.SJF(readyQueue ,  memory);
				break;
			case 3:
				System.out.println("Your choice :  Round-Robin (RR-3)");
				Scheduler.RR(readyQueue, 3 , memory);

				break;
			case 4:
				System.out.println("Your choice :  Round-Robin (RR-5)");
				Scheduler.RR(readyQueue, 5, memory);

				break;
			default:
				System.out.println("Invalid selection.");
				break;
			}
		} catch (Exception e) {
			System.out.println("Please add a number between 1-4 please ! ");
		}
	}

}

class ReadJob extends Thread {
	int arrivelTime = 0;
	private Queue<PCB> jobQueue;

	public ReadJob(Queue<PCB> jobQueue) {
		this.jobQueue = jobQueue;
	}

	@Override
	public void run() {
		Scanner s = new Scanner(System.in);
		System.out.print("Enter the file path (job.txt) : ");
		String Path = s.next();
		File F = new File(Path);

		Scanner R;
		try {
			R = new Scanner(F);
			while (R.hasNextLine()) {
				String jobName = R.nextLine();
				if (!jobName.contains("Job")) {
					System.out.println("Please make sure the structer of job.txt is ( job then data) ! ");
					jobQueue.clear();
					return;
				}
				String data = R.nextLine();

				String[] m1 = data.split(",");

				if (m1.length != 3) {
					System.out.println(
							"all jobs should be given as follows (Process ID, burst time in ms, memory required in MB) ");
					jobQueue.clear();
					return;

				}
				if (m1.length == 3) {
					try {
						int pid = Integer.parseInt(m1[0].trim());
						int BrustTime = Integer.parseInt(m1[1].trim());
						int Memory = Integer.parseInt(m1[2].trim());
						if (pid < 0 || BrustTime <= 0 || Memory < 0) {
							System.out.println("Error in job.txt file , all jobs should be only positive integers ");
							jobQueue.clear();
							return;

						}
						PCB p = new PCB(pid, BrustTime, Memory , jobName);
						p.arrivalTime = arrivelTime;
						jobQueue.add(p);
						arrivelTime++;
					} catch (Exception e) {
						System.out.println("Error in job.txt file , all jobs should be only positive integers ");
						jobQueue.clear();
						return;
					}

				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Please add a file path for (job.txt) ! ");
		}

	}
}

class LoadJob extends Thread {

	private Queue<PCB> jobQueue;
	private Queue<PCB> readyQueue;
	usedMemory memory;

	public LoadJob(Queue<PCB> jobQueue, Queue<PCB> readyQueue, usedMemory memory) {
        this.jobQueue = jobQueue;
		this.readyQueue = readyQueue;
		this.memory = memory;
	}

	public void run() {

		while (!jobQueue.isEmpty()) {
			int memoryRequired = jobQueue.peek().Memory_Required;
			if (memory.getMemory() >= memoryRequired) {
				PCB pcb = jobQueue.poll();
				readyQueue.add(pcb);
				pcb.state = ProcessState.Ready;
				memory.setNegativeMemory(memoryRequired);
			} else {
				if (jobQueue.peek().Memory_Required > 8192) {
					jobQueue.poll();
				} else {
					jobQueue.add(jobQueue.poll());
				}
			}

		}
	}

}
