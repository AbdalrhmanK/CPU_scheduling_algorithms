import java.util.*;

public class Scheduler {

	public static void FCFS(Queue<PCB> readyQueue) {

		int startBT = 0;
		int stopBT = 0;
		int waitingTime = 0;
		Queue<PCB> copyQueue = new LinkedList<>(readyQueue);

		while (!readyQueue.isEmpty()) {

			PCB pcb = readyQueue.poll();

			pcb.state = ProcessState.Running;

			stopBT = pcb.BurstTime;

			GanttChart(startBT, startBT + stopBT, pcb);

			pcb.CompletionTime = startBT + stopBT;
			pcb.WaitingTime = waitingTime;

			waitingTime = startBT + stopBT;
			pcb.BurstTime = 0;
			pcb.state = ProcessState.Terminated;

			startBT += stopBT;

		}
		Result(copyQueue);

	}

	public static void SJF(Queue<PCB> readyQueue) {

		int currentTime = 0;
		int waitingTime = 0;
		Queue<PCB> copyQueue = new LinkedList<>(readyQueue);

		while (!readyQueue.isEmpty()) {
			PCB pcb = getShortestJob(readyQueue);
			pcb.state = ProcessState.Running;

			int startBT = currentTime;
			int stopBT = pcb.BurstTime;

			GanttChart(startBT, startBT + stopBT, pcb);

			currentTime += stopBT;
			pcb.CompletionTime = currentTime;
			pcb.WaitingTime = waitingTime;

			waitingTime += stopBT;
			pcb.BurstTime = 0;
			pcb.state = ProcessState.Terminated;

			readyQueue.remove(pcb);
		}

		Result(copyQueue);
	}

	public static PCB getShortestJob(Queue<PCB> readyQueue) {
		int minBurstTime = Integer.MAX_VALUE;
		PCB minPCB = null;

		for (PCB p : readyQueue) {
			if (p.BurstTime < minBurstTime) {
				minBurstTime = p.BurstTime;
				minPCB = p;
			}
		}

		return minPCB;
	}

	public static void RR(Queue<PCB> readyQueue, int quantum) {
		int currentTime = 0;
		int startBT = 0;
		int stopBT = 0;

		Queue<PCB> copyQueue = new LinkedList<>(readyQueue);

		while (!readyQueue.isEmpty()) {
			PCB pcb = readyQueue.poll();
			pcb.state = ProcessState.Running;

			int burstTime = pcb.BurstTime;

			if (burstTime > quantum) {

				burstTime = quantum;
				pcb.BurstTime -= quantum;
	            readyQueue.add(pcb);
			} else {
				pcb.state = ProcessState.Terminated;
			}

			stopBT = startBT + burstTime; // Calculate stopBT

			GanttChart(startBT, stopBT, pcb); // Pass startBT and stopBT parameters

			currentTime += burstTime;
			startBT += burstTime;
             System.out.print(burstTime);
			if (pcb.state == ProcessState.Terminated) {
				updateCompletionAndWaitingTime(pcb, currentTime);
			} else {
				addProcessToQueueBasedOnArrivalTime(readyQueue, pcb, stopBT);
			}
		}

		Result(copyQueue);
	}

	public static void addProcessToQueueBasedOnArrivalTime(Queue<PCB> readyQueue , PCB pcb , int burstTime ) {
		
	    Queue<PCB> copyQueue = new LinkedList<>();
	    Queue<PCB> copyQueue1 = new LinkedList<>();

    	while (!readyQueue.isEmpty()) {
	        PCB pcb1 = readyQueue.poll();
            
	        if (pcb1.arrivalTime<= burstTime) {
	        	copyQueue.add(pcb1);
	        }else {
	        	copyQueue1.add(pcb1);
	        }
    		
    		
    	}
    	readyQueue.clear();
    	copyQueue.addAll(copyQueue1);
    	readyQueue.addAll(copyQueue);
    	
    }

	public static void updateCompletionAndWaitingTime(PCB pcb, int currentTime) {
		int completionTime = currentTime;
		int waitingTime = (completionTime - pcb.TotalBurstTime) - pcb.arrivalTime;
		pcb.CompletionTime = completionTime;
		pcb.WaitingTime = waitingTime;

	}

	public static void Result(Queue<PCB> readyQueue) {

		int totalWaitingTime = 0;
		int totalCompletionTime = 0;

		double avrWaitingTime = 0;
		double avrCompletionTime = 0;

		double Counter = 0;

		while (!readyQueue.isEmpty()) {
			PCB pcb = readyQueue.poll();

			if (pcb.state == ProcessState.Terminated) {
				Counter += 1;
				totalWaitingTime += pcb.WaitingTime;
				totalCompletionTime += pcb.CompletionTime;
			}
		}

		if (Counter != 0) {
			avrWaitingTime = (totalWaitingTime / Counter);
			avrCompletionTime = (totalCompletionTime / Counter);
		}

		System.out.println("___________________________________________________________________________");
		System.out.println((int) Counter + " Processes Completed");

		if (Counter <= 0) {
			System.out.println("The program stopped because there wasn't processes in the memory.");
			return;
		}

		System.out.println("Total Waiting Time : " + totalWaitingTime);
		System.out.println("Total Completion Time : " + totalCompletionTime);
		// --------------------------------------------------------------------------------------------------
		System.out.println("Average Waiting Time : " + avrWaitingTime);
		System.out.println("Average Completion Time : " + avrCompletionTime);
	}

	public static void GanttChart(int StartBT, int StopBT, PCB P) {
		System.out.println(" _____");
		System.out.println("| P" + P.ProcessID + " |");
		System.out.println(StartBT + "----" + StopBT);
	}

}
