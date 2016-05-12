package com.genericdbclient.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CustomVBox extends VBox {

	public CustomVBox() {
		
		this.setAlignment(Pos.CENTER);
		/*this.setPrefWidth(140);
		this.setMinWidth(140);
		this.setMaxWidth(200);*/
		this.setSpacing(20);
		this.setPadding(new Insets(10,5,0,10));
		this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
	}
}
