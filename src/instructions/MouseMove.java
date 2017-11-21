package instructions;

import java.awt.Robot;

public class MouseMove implements Instruction {

	private int screenX;
	private int screenY;
	
	@Override
	public void execute(Robot robot) {
		robot.mouseMove(screenX, screenY);
	}
	
	@Override
	public String getText() {
		return "Move mouse to (" + screenX + ", " + screenY + ")";
	}
	
	public void setCoord(int x, int y) { 
		screenX = x;
		screenY = y;
	}
}
