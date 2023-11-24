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
		} catch (Exception e) {
			System.out.println("Please add a number between 1-4 please ! ");
		}
	}

}

class ReadJob extends Thread {
	private Queue<PCB> jobQueue;
	int arrivalTime = 0;

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
		        	return ;
		        }
				String data = R.nextLine();

				String[] m1 = data.split(",");

				if (m1.length != 3) {
					System.out.println(
							"all jobs should be given as follows (Process ID, burst time in ms, memory required in MB) ");
					jobQueue.clear();
					return;

				}
//				/Users/AK/Desktop/job.txt

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
						PCB p = new PCB(pid, BrustTime, Memory);
						p.arrivalTime = arrivalTime;
//						System.out.println("Jop" + p.ProcessID);
//						System.out.println(p.ProcessID + "-" + p.BurstTime + "-" + p.Memory_Required+'-'+p.arrivalTime);
						jobQueue.add(p);
						arrivalTime++;
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
					PCB pcb = jobQueue.poll();
					pcb.state = ProcessState.Ready;
					readyQueue.add(pcb);
					memorySize = memorySize - memory_process;
				} else {
					jobQueue.poll();
				}

			}
		}

	}

}
