/*******************************************************************************
 * Copyright (c) 2012 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tom Schindl<tom.schindl@bestsolution.at> - initial API and implementation
 *******************************************************************************/
package at.bestsolution.fx43.apps.media;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.media.MediaView;
import javafx.util.Callback;

public class MediaControl {
	private BorderPane box;
	
	public MediaControl() {
		box = new BorderPane();
		box.setStyle("-fx-background-color: white");
		
		ListView<Media> mediaList = new ListView<>();
		mediaList.setCellFactory(new Callback<ListView<Media>, ListCell<Media>>() {
			
			@Override
			public ListCell<Media> call(ListView<Media> param) {
				return new ListCell<Media>() {
					@Override
					protected void updateItem(Media item, boolean empty) {
						super.updateItem(item, empty);
						if( item != null ) {
							setText(item.title);
						}
					}
				};
			}
		});
		mediaList.setItems(createContentList());
		box.setLeft(mediaList);
		
		BorderPane p = new BorderPane();
		p.setCenter(new MediaView());
		box.setCenter(p);
	}
	
	public Node getNode() {
		return box;
	}
	
	private ObservableList<Media> createContentList() {
		ObservableList<Media> rv = FXCollections.observableArrayList();
		rv.add(new Media("Java vs .net", new File("/Users/tomschindl/Documents/e4_workspaces/efxclipse/at.bestsolution.fx43.apps.media/movies/java_vs_net.mp4")));
		rv.add(new Media("The Bourne Legacy", new File("/Users/tomschindl/Documents/e4_workspaces/efxclipse/at.bestsolution.fx43.apps.media/movies/bourne4.mp4")));
		return rv;
	}
	
	static class Media {
		private String title;
		private File file;
		
		public Media(String title, File file) {
			this.title = title;
			this.file = file;
		}
	}
}