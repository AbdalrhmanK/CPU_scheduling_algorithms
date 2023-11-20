import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.util.*;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		Queue<PCB> jobQueue = new LinkedList();
		Queue<PCB> readyQueue = new LinkedList();

		int memorySize = 8192;

		ReadJob read = new ReadJob(jobQueue);
		LoadJob load = new LoadJob(jobQueue, readyQueue, memorySize);

		read.start();
		read.join();
		load.start();

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
			Scheduler.FCFS(readyQueue);
			break;
		case 2:
			System.out.println("Your choice : Shortest-Job-First (SJF)");
			Scheduler.SJF(readyQueue);
			break;
		case 3:
			System.out.println("Your choice :  Round-Robin (RR-3)");
			Scheduler.RR(readyQueue, 3);

			break;
		case 4:
			System.out.println("Your choice :  Round-Robin (RR-5)");
			Scheduler.RR(readyQueue, 5);

			break;
		default:
			System.out.println("Invalid selection.");
			break;
		}

//		PCB pcb = jobQueue.poll();
//		System.out.print(pcb.ProcessID);
//		System.out.print(pcb.BurstTime);
//		System.out.print(pcb.Memory_Required);
//		System.out.print(pcb.PS);
//		
//		lt.start();

//		System.out.print(10);
	}

}

class ReadJob extends Thread {
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
				String data = R.nextLine();
				String[] m1 = data.split(",");

				if (m1.length == 3) {
					int pid = Integer.parseInt(m1[0].trim());
					int BrustTime = Integer.parseInt(m1[1].trim());
					int Memory = Integer.parseInt(m1[2].trim());
					PCB p = new PCB(pid, BrustTime, Memory);
					System.out.println("Jop" + p.ProcessID);
					System.out.println(p.ProcessID + "-" + p.BurstTime + "-" + p.Memory_Required);
					jobQueue.add(p);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.print("Please add a file path for (job.txt) ! ");
		}

	}

}

class LoadJob extends Thread {

	private Queue<PCB> jobQueue;
	private Queue<PCB> readyQueue;
	private int memorySize;

	public LoadJob(Queue<PCB> jobQueue, Queue<PCB> readyQueue, int memorySize) {
		this.jobQueue = jobQueue;
		this.readyQueue = readyQueue;
		this.memorySize = memorySize;
	}

	@Override
	public void run() {
		while (true) {
			if (!jobQueue.isEmpty()) {
				int memory_process = jobQueue.peek().Memory_Required;
				if (memorySize >= memory_process) {
					readyQueue.add(jobQueue.poll());
					memorySize = memorySize - memory_process;
				}

			}
		}

	}

}
