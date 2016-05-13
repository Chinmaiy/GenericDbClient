/**
 * 
 */
package com.genericdbclient.view;

import javafx.scene.control.Button;

/**
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 *
 */
public class CustomButton extends Button {
	
	private final int GROW_FACTOR = 7;
	private final int BORDER_FACTOR = 20;

	public CustomButton(String text) {
		super(text);
		
		this.setMinWidth(this.getText().length() * GROW_FACTOR + BORDER_FACTOR);
	}
}
