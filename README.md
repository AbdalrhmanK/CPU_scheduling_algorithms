The purpose of this project is to write a java program that simulates the CPU Scheduler (also known as Short-Term Scheduler) of an operating system.
The program implements the following CPU scheduling algorithms. (1) First-Come-First-Serve (FCFS)
(2) Shortest-Job-First (SJF)
(3) Round-Robin with time slice = 3 (RR-3)
(4) Round-Robin with time slice = 5 (RR-5)
When a process is created, a Process Control Block (PCB) is expected to be created with the following information:
• Process ID: Contains the process ID.
• Process state: Contains the state of the Process (New, Ready, Running, Waiting, Terminated)
• Burst Time (in ms)
• Memory Required in MB
The program will read process information from a file (job.txt) - File reading performed in an independent thread that creates the PCBs and put them in the job queue.
Loading the jobs to ready queue performed in an independent thread that continuously checks the available space in memory to load the next job from the job queue to the ready queue.
the jobs can be loaded to ready queue only if enough space for the job is available in memory.
The main thread will perform the scheduling algorithms. the program let the user to choose the scheduling algorithm to be applied.
