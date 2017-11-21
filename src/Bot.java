import java.awt.Robot;
import java.util.ArrayList;
import java.util.Stack;

import instructions.*;

public class Bot {

	private Robot robot;
	private ArrayList<Instruction> instructions;
	private int currentIndex = 0;
	
	public Bot() {
		try {
			robot = new Robot();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		instructions = new ArrayList<Instruction>();
	}
	
	public void execute() {
		ArrayList<Instruction> processedInstructions = handleLoops();
		for(Instruction instruction : processedInstructions) {
			instruction.execute(robot);
		}
		
		undoPresses();
	}
	
	public void addInstruction(Instruction i) {
		if(instructions.isEmpty()) {
			instructions.add(i);
		}
		else {
			//end of list, append
			if(currentIndex == instructions.size() - 1) {
				instructions.add(i);
			}
			//middle of list, insert after current
			else {
				instructions.add(currentIndex+1, i);
			}
			currentIndex++;
		}
	}
	
	public void removeInstruction() {
		if(!instructions.isEmpty()) {
			
			//remove all instructions between loop start and end inclusive
			if(instructions.get(currentIndex) instanceof LoopStart) {
				
				int level = -1; //start at -1 since current index is a loop start
				boolean done = false;
				
				while(currentIndex < instructions.size()) {
					
					if(instructions.get(currentIndex) instanceof LoopStart) {
						level++;
					}
					if(instructions.get(currentIndex) instanceof LoopEnd) {
						if(level > 0) {
							level--;
						}
						else {
							done = true;
						}
					}
					
					instructions.remove(currentIndex);
					if(done) {
						break;
					}
				}
				
				if(currentIndex > 0) {
					currentIndex--;
				}
			}
			//remove all instruction between loop end and start inclusive
			else if(instructions.get(currentIndex) instanceof LoopEnd) {
				
				int level = -1; //start at -1 since current index is a loop end
				boolean done = false;
				
				while(currentIndex >= 0) {
					
					if(instructions.get(currentIndex) instanceof LoopEnd) {
						level++;
					}
					if(instructions.get(currentIndex) instanceof LoopStart) {
						if(level > 0) {
							level--;
						}
						else {
							done = true;
						}
					}
					
					instructions.remove(currentIndex);
					if(currentIndex > 0) {
						currentIndex--;
					}
					
					if(done) {
						break;
					}
				}
				
			}
			//normal deletion
			else {
				if(currentIndex == instructions.size() - 1 && currentIndex > 0) {
					instructions.remove(currentIndex);
					currentIndex--;
				}
				else {
					instructions.remove(currentIndex);
				}
			}
			
		}
	}
	
	public void prevInstruction() {
		if(currentIndex > 0) {
			currentIndex--;
			displayInstructions();
		}
	}
	
	public void nextInstruction() {
		if(currentIndex < instructions.size() - 1) {
			currentIndex++;
			displayInstructions();
		}
	}
	
	private ArrayList<Instruction> handleLoops() {
		ArrayList<Instruction> processedInstructions = new ArrayList<Instruction>();
		Stack<LoopStart> loopLevels = new Stack<LoopStart>();
		
		for(Instruction instruction : instructions) {
			//new loop level
			if(instruction instanceof LoopStart) {
				loopLevels.push((LoopStart)instruction);
				((LoopStart) instruction).resetInstructions();
			}
			//end of loop level
			else if(instruction instanceof LoopEnd) {
				LoopStart loop = loopLevels.pop();
				
				//outermost loop
				if(loopLevels.isEmpty()) {
					processedInstructions.add(loop);					
				}
				//loop within a loop
				else {
					LoopStart outerLoop = loopLevels.pop();
					outerLoop.addInstruction(loop);
				}
			}
			//normal instruction
			else {
				//instruction not in loop
				if(loopLevels.isEmpty()) {
					processedInstructions.add(instruction);
				}
				//instruction in loop
				else {
					LoopStart loop = loopLevels.peek();
					loop.addInstruction(instruction);
				}
			}
		}
		
		return processedInstructions;
	}
	
	public void undoPresses() {
		for(int button : ButtonPresses.getMousePresses()) {
			robot.mouseRelease(button);
		}
		
		for(int key : ButtonPresses.getKeyPresses()) {
			robot.keyRelease(key);
		}
	}
	
	public void displayInstructions() {
		String result = "";
		
		int level = 0;
		
		for(int i = 0; i < instructions.size(); i++) {
			Instruction instruction = instructions.get(i);
			
			if(instruction instanceof LoopEnd) {
				level--;
			}
			
			for(int j = 0; j < level; j++) {
				result += "\t";
			}
			
			if(i == currentIndex) {
				result += ">";
			}
			
			result += instruction.getText() + "\n";
			
			if(instruction instanceof LoopStart) {
				level++;
			}
		}
		
		Main.viewPanel.setInstructionView(result);
	}
}
