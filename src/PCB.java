

enum ProcessState {New,Ready,Running,Waiting,Terminated}

public class PCB  {

		public int ProcessID;
        public int BurstTime ; 
        public int Memory_Required;
        public ProcessState state = ProcessState.New;
        
        public int WaitingTime;
        public int arrivalTime = 0 ;
        public int CompletionTime;
        public int Remaing_BurstTime;
        
        
        public PCB(int Pid , int BurstTime, int Memory) {
        	
        	ProcessID = Pid ;
        	this.BurstTime = BurstTime;
        	this.Memory_Required = Memory;
        	this.Remaing_BurstTime = BurstTime;
        	
        	
        }
	
}
