import java.util.HashMap;
import java.util.Set;

//Keep track of what buttons remain pressed so they can be released when the program exits before they are released.
public class ButtonPresses {

	private static HashMap<Integer, Boolean> mousePresses = new HashMap<Integer, Boolean>();
	private static HashMap<Integer, Boolean> keyPresses = new HashMap<Integer, Boolean>();
	
	public static void setMousePress(int button, boolean pressed) {
		mousePresses.put(button, pressed);
	}
	
	public static Set<Integer> getMousePresses() {
		return mousePresses.keySet();
	}
	
	public static void setKeyPress(int key, boolean pressed) {
		keyPresses.put(key, pressed);
	}
	
	public static Set<Integer> getKeyPresses() {
		return keyPresses.keySet();
	}
}
