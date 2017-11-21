package instructions;

import java.awt.Robot;

//Dummy instruction to mark end of loop
public class LoopEnd implements Instruction {

	@Override
	public void execute(Robot robot) {
		
	}

	@Override
	public String getText() {
		return "End loop";
	}
	
}
