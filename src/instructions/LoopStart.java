package instructions;

import java.awt.Robot;
import java.util.ArrayList;

public class LoopStart implements Instruction {

	private ArrayList<Instruction> instructions = new ArrayList<Instruction>();
	private int loops;
	
	@Override
	public void execute(Robot robot) {
		for(int i = 0; i < loops; i++) {
			for(Instruction instruction : instructions) {
				instruction.execute(robot);
			}
		}
	}
	
	@Override
	public String getText() {
		return "Loop " + loops + " time(s)";
	}
	
	public void setLoops(int loops) {
		this.loops = loops;
	}
	
	public void addInstruction(Instruction i) {
		instructions.add(i);
	}
	
	public void resetInstructions() {
		instructions.clear();
	}

}
