package instructions;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class KeyPress implements Instruction{

	private int key;
	
	@Override
	public void execute(Robot robot) {
		robot.keyPress(key);
	}
	
	@Override
	public String getText() {
		return "Press " + KeyEvent.getKeyText(key);
	}
	
	public void setKey(int key) {
		this.key = key;
	}

}
