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

	private String farmId;
	
	public MyHandler(String farmId) {
		this.farmId = farmId;
	}

	@Override
	public void handle(ActionEvent arg0) {
			new FarmStage(farmId);
	}

}
