
enum ProcessState {
	New, Ready, Running, Waiting, Terminated
}

public class PCB {

	public String jobName;

	public int ProcessID;
	public int BurstTime;
	public int Memory_Required;
	public ProcessState state = ProcessState.New;
 
	public int WaitingTime;
	public int arrivalTime = 0;
	public int CompletionTime;
	public int TotalBurstTime;
    
	public PCB(int Pid, int BurstTime, int Memory ,  String jobName) {

		ProcessID = Pid;
		this.BurstTime = BurstTime;
		this.Memory_Required = Memory;
		this.TotalBurstTime = BurstTime;
		this.jobName = jobName;

	}

}
