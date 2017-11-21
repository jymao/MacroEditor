package instructions;

import java.awt.Robot;
import java.awt.event.InputEvent;

public class MousePress implements Instruction {

	private int buttonMask;
	
	@Override
	public void execute(Robot robot) {
		robot.mousePress(buttonMask);
	}

	@Override
	public String getText() {
		String buttonText;
		
		if(buttonMask == InputEvent.BUTTON1_DOWN_MASK) {
			buttonText = "Left";
		}
		else if(buttonMask == InputEvent.BUTTON2_DOWN_MASK) {
			buttonText = "Right";
		}
		else {
			buttonText = "Middle";
		}
		
		
		return buttonText + " mouse click";
	}
	
	public void setButtons(int buttonMask) { this.buttonMask = buttonMask; }
}
