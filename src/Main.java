import javax.swing.JFrame;

public class Main {

	private static JFrame frame;
	public static ViewPanel viewPanel;
	public static Bot bot;
	
	public static void main(String[] args) {
		
		bot = new Bot();
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				showGUI();
			}
		});
		
	}

	private static void showGUI() {
		frame = new JFrame("MacroEditor");
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);
				
		viewPanel = new ViewPanel();
		frame.setContentPane(viewPanel);
		frame.addWindowListener(viewPanel);
		
		frame.pack();
		frame.setLocationRelativeTo(null); //center frame
		frame.setVisible(true);
	}
	
}
