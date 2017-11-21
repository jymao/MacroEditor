package instructions;

import java.awt.Robot;

public interface Instruction {

	public void execute(Robot robot);
	
	public String getText();
}
