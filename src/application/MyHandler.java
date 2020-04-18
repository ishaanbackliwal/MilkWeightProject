package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * This class is used to handle events on the GUI
 * @author mason batchelor
 *
 */
public class MyHandler implements EventHandler<ActionEvent> {

	private Button b1;
	
	public MyHandler(Button b1) {
		this.b1 = b1;
	}

	@Override
	public void handle(ActionEvent arg0) {
			new FarmStage();
	}

}
