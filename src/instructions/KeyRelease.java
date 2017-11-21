package instructions;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class KeyRelease implements Instruction {

	private int key;
	
	@Override
	public void execute(Robot robot) {
		robot.keyRelease(key);
	}

	@Override
	public String getText() {
		return "Release " + KeyEvent.getKeyText(key);
	}
	
	public void setKey(int key) {
		this.key = key;
	}
}
