
public class usedMemory {

	public int memoryUsed = 0;

	public usedMemory(int memory) {
		memoryUsed = memory;
	}

	public void setPositiveMemory(int memory) {

		memoryUsed += memory;
	}

	public void setNegativeMemory(int memory) {

		memoryUsed -= memory;
	}

	public int getMemory() {
		
    return memoryUsed;
    		
	}

}
