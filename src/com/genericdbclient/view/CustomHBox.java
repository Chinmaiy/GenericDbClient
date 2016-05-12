/**
 * 
 */
package com.genericdbclient.view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 *
 */
public class CustomHBox extends HBox {

	public CustomHBox() {
		
		this.setSpacing(2);
		this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
	}
}
