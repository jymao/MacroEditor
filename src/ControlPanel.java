import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import instructions.*;

public class ControlPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 2L;
	private JPanel instructionPanel;
	private JPanel navigationPanel;
	private JPanel cardPanel;
	
	private JTextField waitField;
	private JTextField loopField;
	private JTextField mouseXField;
	private JTextField mouseYField;
	private JComboBox mouseButtonComboBox;
	private JComboBox mouseToggleBox;
	private JComboBox keyComboBox;
	private JComboBox keyToggleBox;
	
	private final String[] INSTRUCTION_OPTIONS = {"Wait", "Press/Release key",
			"Move mouse", "Press/Release mouse button", "Loop"};
	
	private final String[] MOUSE_BUTTON_OPTIONS = {"Left", "Right", "Middle"};
	private final String[] TOGGLE_OPTIONS = {"Press", "Release"};
	private final String[] KEY_OPTIONS;
	
	private int currentMode = 0;
	private final int WAIT_MODE = 0;
	private final int KEY_MODE = 1;
	private final int MOUSEMOVE_MODE = 2;
	private final int MOUSEBUTTON_MODE = 3;
	private final int LOOP_MODE = 4;
	
	public ControlPanel() {
		KEY_OPTIONS = new String[KeyValues.KEY_CODES.length];
		for(int i = 0; i < KEY_OPTIONS.length; i++) {
			KEY_OPTIONS[i] = KeyEvent.getKeyText(KeyValues.KEY_CODES[i]);
		}

		init();
	}
	
	private void init() {
		//size of panel
		setPreferredSize(new Dimension(350, 500));
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		initInstructionPanel();
		initNavigationPanel();
		add(instructionPanel);
		add(navigationPanel);
	}
	
	private void initInstructionPanel() {
		instructionPanel = new JPanel();
		instructionPanel.setPreferredSize(new Dimension(300, 250));
		instructionPanel.setLayout(new BoxLayout(instructionPanel, BoxLayout.Y_AXIS));
		
		Border outer = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border inner = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		instructionPanel.setBorder(BorderFactory.createCompoundBorder(outer, inner));
		
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JLabel comboBoxTitle = new JLabel("Macro Instructions");
		titlePanel.add(comboBoxTitle);
		instructionPanel.add(titlePanel);
		
		JComboBox comboBox = new JComboBox(INSTRUCTION_OPTIONS);
		comboBox.setEditable(false);
		comboBox.addActionListener(this);
		comboBox.setActionCommand("instruction");
		comboBox.setBorder(BorderFactory.createEmptyBorder(0, 10, 10 , 10));
		instructionPanel.add(comboBox);
		
		initCardPanel();
		instructionPanel.add(cardPanel);
		
		JPanel editPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		editPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10 , 5));
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener(this);
		addButton.setActionCommand("add");
		editPanel.add(addButton);
		
		JButton removeButton = new JButton("Remove");
		removeButton.addActionListener(this);
		removeButton.setActionCommand("remove");
		editPanel.add(removeButton);
		instructionPanel.add(editPanel);
	}
	
	private void initCardPanel() {
		cardPanel = new JPanel();
		cardPanel.setLayout(new CardLayout());
		
		cardPanel.add(createWaitCard(), INSTRUCTION_OPTIONS[0]);
		cardPanel.add(createKeyCard(), INSTRUCTION_OPTIONS[1]);
		cardPanel.add(createMouseMoveCard(), INSTRUCTION_OPTIONS[2]);
		cardPanel.add(createMouseButtonCard(), INSTRUCTION_OPTIONS[3]);
		cardPanel.add(createLoopCard(), INSTRUCTION_OPTIONS[4]);
	}
	
	private JPanel createWaitCard() {
		JPanel card = new JPanel();
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
		
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		titlePanel.add(new JLabel("Time to Wait"));
		card.add(titlePanel);
		
		JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		optionPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0 , 0));
		waitField = new JTextField(10);
		optionPanel.add(waitField);
		card.add(optionPanel);
		card.add(Box.createVerticalGlue());
		
		return card;
	}
	
	private JPanel createKeyCard() {
		JPanel card = new JPanel();
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		titlePanel.add(new JLabel("Keyboard Button"));
		card.add(titlePanel);
		
		JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		optionPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0 , 0));
		
		keyComboBox = new JComboBox(KEY_OPTIONS);
		optionPanel.add(keyComboBox);
		
		keyToggleBox = new JComboBox(TOGGLE_OPTIONS);
		optionPanel.add(keyToggleBox);
		
		card.add(optionPanel);
		card.add(Box.createVerticalGlue());
		
		return card;
	}
	
	private JPanel createMouseMoveCard() {
		JPanel card = new JPanel();
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		titlePanel.add(new JLabel("Screen Coordinates (X, Y)"));
		card.add(titlePanel);
		
		JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		optionPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0 , 0));
		mouseXField = new JTextField(10);
		mouseYField = new JTextField(10);
		optionPanel.add(mouseXField);
		optionPanel.add(mouseYField);
		card.add(optionPanel);
		card.add(Box.createVerticalGlue());
		
		return card;
	}
	
	private JPanel createMouseButtonCard() {
		JPanel card = new JPanel();
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		titlePanel.add(new JLabel("Mouse Button"));
		card.add(titlePanel);
		
		JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		optionPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0 , 0));
		
		mouseButtonComboBox = new JComboBox(MOUSE_BUTTON_OPTIONS);
		optionPanel.add(mouseButtonComboBox);
		
		mouseToggleBox = new JComboBox(TOGGLE_OPTIONS);
		optionPanel.add(mouseToggleBox);
		
		card.add(optionPanel);
		card.add(Box.createVerticalGlue());
		
		return card;
	}
	
	private JPanel createLoopCard() {
		JPanel card = new JPanel();
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		titlePanel.add(new JLabel("Loop Cycles"));
		card.add(titlePanel);
		
		JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		optionPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0 , 0));
		loopField = new JTextField(10);
		optionPanel.add(loopField);
		card.add(optionPanel);
		card.add(Box.createVerticalGlue());
		
		return card;
	}
	
	private void initNavigationPanel() {
		navigationPanel = new JPanel();
		navigationPanel.setPreferredSize(new Dimension(300, 300));
		
		Border outer = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border inner = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		navigationPanel.setBorder(BorderFactory.createCompoundBorder(outer, inner));
		
		JButton prevButton = new JButton("Prev");
		prevButton.addActionListener(this);
		prevButton.setActionCommand("prev");
		navigationPanel.add(prevButton);
		
		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(this);
		nextButton.setActionCommand("next");
		navigationPanel.add(nextButton);
		
		JButton playButton = new JButton("Play");
		playButton.addActionListener(this);
		playButton.setActionCommand("play");
		navigationPanel.add(playButton);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "instruction") {
			JComboBox comboBox = (JComboBox)e.getSource();
			CardLayout cl = (CardLayout)cardPanel.getLayout();
			cl.show(cardPanel, (String)comboBox.getSelectedItem());
			setMode((String)comboBox.getSelectedItem());
		}
		else if(e.getActionCommand() == "add") {
			switch(currentMode) {
				case WAIT_MODE:
					String field = waitField.getText();
					
					if(isPosInt(field)) {
						int time = Integer.parseInt(field);
						if(time >= 0 && time <= 60000) {
							Delay instruction = new Delay();
							instruction.setTime(time);
							Main.bot.addInstruction(instruction);
							Main.bot.displayInstructions();
						}
						else {
							JOptionPane.showMessageDialog(Main.viewPanel, "Must be a number between 0 and 60000.");
						}
					}
					else {
						JOptionPane.showMessageDialog(Main.viewPanel, "Must be a number between 0 and 60000.");
					}
					
					break;
				case KEY_MODE:
					int keyCode = KeyValues.KEY_CODES[keyComboBox.getSelectedIndex()];
					String toggleField = (String)keyToggleBox.getSelectedItem();
					
					if(toggleField.equals("Press")) {
						KeyPress instruction = new KeyPress();
						instruction.setKey(keyCode);
						ButtonPresses.setKeyPress(keyCode, true);
						Main.bot.addInstruction(instruction);
					}
					else {
						KeyRelease instruction = new KeyRelease();
						instruction.setKey(keyCode);
						ButtonPresses.setKeyPress(keyCode, false);
						Main.bot.addInstruction(instruction);
					}
					
					Main.bot.displayInstructions();
					
					break;
				case MOUSEMOVE_MODE:
					String xField = mouseXField.getText();
					String yField = mouseYField.getText();
					
					if(isPosInt(xField) && isPosInt(yField)) {
						MouseMove instruction = new MouseMove();
						instruction.setCoord(Integer.parseInt(xField), Integer.parseInt(yField));
						Main.bot.addInstruction(instruction);
						Main.bot.displayInstructions();
					}
					else {
						JOptionPane.showMessageDialog(Main.viewPanel, "Invalid coordinates.");
					}
					
					break;
				case MOUSEBUTTON_MODE:
					String buttonField = (String)mouseButtonComboBox.getSelectedItem();
					String mouseToggleField = (String)mouseToggleBox.getSelectedItem();
					
					int buttonMask = InputEvent.BUTTON1_DOWN_MASK;
					
					if(buttonField.equals("Right")) {
						buttonMask = InputEvent.BUTTON2_DOWN_MASK;
					}
					else if(buttonField.equals("Middle")) {
						buttonMask = InputEvent.BUTTON3_DOWN_MASK;
					}
					
					if(mouseToggleField.equals("Press")) {
						MousePress instruction = new MousePress();
						instruction.setButtons(buttonMask);
						ButtonPresses.setMousePress(buttonMask, true);
						Main.bot.addInstruction(instruction);
					}
					else {
						MouseRelease instruction = new MouseRelease();
						instruction.setButtons(buttonMask);
						ButtonPresses.setMousePress(buttonMask, false);
						Main.bot.addInstruction(instruction);
					}
					
					Main.bot.displayInstructions();
					
					break;
				case LOOP_MODE:
					String loops = loopField.getText();
					
					if(isPosInt(loops)) {
						LoopStart instruction = new LoopStart();
						instruction.setLoops(Integer.parseInt(loops));
						Main.bot.addInstruction(instruction);
						Main.bot.addInstruction(new LoopEnd());
						Main.bot.displayInstructions();
					}
					else {
						JOptionPane.showMessageDialog(Main.viewPanel, "Invalid number.");
					}
					
					break;
				default:
					break;
			}
		}
		else if(e.getActionCommand() == "remove") {
			Main.bot.removeInstruction();
			Main.bot.displayInstructions();
		}
		else if(e.getActionCommand() == "prev") {
			Main.bot.prevInstruction();
		}
		else if(e.getActionCommand() == "next") {
			Main.bot.nextInstruction();
		}
		else if(e.getActionCommand() == "play") {
			Main.bot.execute();
		}
	}

	private void setMode(String instruction) {
		for(int i = 0; i < INSTRUCTION_OPTIONS.length; i++) {
			if(instruction.equals(INSTRUCTION_OPTIONS[i])) {
				currentMode = i;
			}
		}
	}

	private boolean isPosInt(String s) {
		if(s.isEmpty()) {
			return false;
		}
		
		for(int i = 0; i < s.length(); i++) {
			if(!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}
		
		return true;
	}
	
}
