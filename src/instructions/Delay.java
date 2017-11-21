package instructions;

import java.awt.Robot;

public class Delay implements Instruction {

	private int time;
	
	@Override
	public void execute(Robot robot) {
		robot.delay(time);
	}
	
	@Override
	public String getText() {
		return "Wait for " + time + " milliseconds";
	}
	
	public void setTime(int ms) {
		time = ms;
	}

}
