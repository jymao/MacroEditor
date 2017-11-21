import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class ViewPanel extends JPanel implements WindowListener{

	private static final long serialVersionUID = 1L;
	private ControlPanel controlPanel;
	private JTextArea textArea;
	
	public ViewPanel() {
		super(new BorderLayout());
		init();
	}
	
	private void init() {
		//size of panel
		setPreferredSize(new Dimension(700, 500));
		setBackground(Color.WHITE);
		
		controlPanel = new ControlPanel();
		add(controlPanel, BorderLayout.LINE_END);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Calibri", Font.PLAIN, 16));
		textArea.setTabSize(2);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		Border outer = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border inner = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(outer, inner));
		
		add(scrollPane, BorderLayout.CENTER);
	}
	
	public void setInstructionView(String instructions) {
		textArea.setText(instructions);
	}
	
	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		Main.bot.undoPresses();
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowOpened(WindowEvent e) {}

}
