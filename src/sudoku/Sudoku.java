package sudoku;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Sudoku extends Application {
	private int counter = 0;
	private static Label lbl = new Label("");
	private static GridPane raster = new GridPane();	

	@Override
	public void start(Stage primaryStage) {
		lbl.setId("eigenes-label");

		StackPane stack = new StackPane();
		BorderPane bp = new BorderPane();
		bp.setStyle("-fx-background-color: #001825");

		raster.getStyleClass().add("border-verlauf");
		raster.setPrefWidth(500);
		raster.setPrefHeight(500);
		raster.setMaxSize(550, 550);

		raster.setPadding(new Insets(10));
		raster.setHgap(10);
		raster.setVgap(10);

		PseudoClass right = PseudoClass.getPseudoClass("right");
		PseudoClass bottom = PseudoClass.getPseudoClass("bottom");

		TextField[] tf = new TextField[81];

		// "raster" mit Textfeldern füllen  !!!!!! //Add(node, Spaltenindex, Zeilenindex)
		for (int zeile = 0; zeile <= 8; zeile++) {
			for (int spalte = 0; spalte <= 8; spalte++) {

				raster.add(tf[counter] = new TextField(), spalte, zeile);	//erst spalte dann Zeile!!!				
				counter++;						
			}
		}

		// RowConstraints in GriDPane eintragen
		RowConstraints rc1 = new RowConstraints();
		rc1.setPercentHeight(12);
		RowConstraints rc2 = new RowConstraints();
		rc2.setPercentHeight(12);
		RowConstraints rc3 = new RowConstraints();
		rc3.setPercentHeight(12);
		RowConstraints rc4 = new RowConstraints();
		rc4.setPercentHeight(12);
		RowConstraints rc5 = new RowConstraints();
		rc5.setPercentHeight(12);
		RowConstraints rc6 = new RowConstraints();
		rc6.setPercentHeight(12);
		RowConstraints rc7 = new RowConstraints();
		rc7.setPercentHeight(12);
		RowConstraints rc8 = new RowConstraints();
		rc8.setPercentHeight(12);
		RowConstraints rc9 = new RowConstraints();
		rc9.setPercentHeight(12);
		raster.getRowConstraints().addAll(rc1, rc2, rc3, rc4, rc5, rc6, rc7, rc8, rc9);

		// ColumnConstraints in GriDPane eintragen
		ColumnConstraints cc1 = new ColumnConstraints();
		cc1.setPercentWidth(12);
		ColumnConstraints cc2 = new ColumnConstraints();
		cc2.setPercentWidth(12);
		ColumnConstraints cc3 = new ColumnConstraints();
		cc3.setPercentWidth(12);
		ColumnConstraints cc4 = new ColumnConstraints();
		cc4.setPercentWidth(12);
		ColumnConstraints cc5 = new ColumnConstraints();
		cc5.setPercentWidth(12);
		ColumnConstraints cc6 = new ColumnConstraints();
		cc6.setPercentWidth(12);
		ColumnConstraints cc7 = new ColumnConstraints();
		cc7.setPercentWidth(12);
		ColumnConstraints cc8 = new ColumnConstraints();
		cc8.setPercentWidth(12);
		ColumnConstraints cc9 = new ColumnConstraints();
		cc9.setPercentWidth(12);
		raster.getColumnConstraints().addAll(cc1, cc2, cc3, cc4, cc5, cc6, cc7, cc8, cc9);

		// Buttons
		Button buttonLaden = new Button("DATEI LADEN");
		buttonLaden.setPrefWidth(120);
		buttonLaden.setId("eigener-button");

		Button buttonLos = new Button("START");
		buttonLos.setPrefWidth(120);
		buttonLos.setId("eigener-button");

		Button buttonLoesung = new Button("LÖSUNG");
		buttonLoesung.setPrefWidth(120);
		buttonLoesung.setId("eigener-button");

		Button buttonNeu = new Button("NEUES SPIEL");
		buttonNeu.setPrefWidth(120);
		buttonNeu.setId("eigener-button");		
		
		VBox vb = new VBox(10, buttonLaden, buttonLos, buttonLoesung, buttonNeu, lbl);
		vb.setPadding(new Insets(10));

		Rectangle rv1 = new Rectangle(178, 0, 2, 550);
		rv1.setManaged(false);
		rv1.getStyleClass().add("verlauf");

		Rectangle rv2 = new Rectangle(350, 0, 2, 550);
		rv2.setManaged(false);
		rv2.getStyleClass().add("verlauf");


		Rectangle rh3 = new Rectangle(0, 184, 529, 2);
		rh3.setManaged(false);
		rh3.getStyleClass().add("verlauf");		

		Rectangle rh4 = new Rectangle(0, 363, 529, 2);
		rh4.setManaged(false);
		rh4.getStyleClass().add("verlauf");		

		stack.getChildren().addAll(raster,rv1,rv2,rh3,rh4);
		//stack.setAlignment(Pos.CENTER);     // Right-justify nodes in stack
		//StackPane.setMargin(helpText, new Insets(0, 10, 0, 0)); // Center "?"	

		bp.setRight(vb);
		bp.setCenter(stack);
		BorderPane.setAlignment(raster, Pos.CENTER_LEFT);

		Scene scene = new Scene(bp, 670, 550);
		scene.getStylesheets().add("style.css");

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("FX Sudoku");
		primaryStage.show();

		// Zahlen aus generator.txt auslesen und in sudoku textfelder einfügen.
		buttonLaden.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int counter = 0;
				try {
					// bufferReader anlegen
					Path taFile = Paths.get("generator.txt");
					System.out.println(taFile.toAbsolutePath());
					BufferedReader br = Files.newBufferedReader(taFile);
					String[] leser = new String[81];

					for (int i = 0; i < leser.length; i++) {
						leser[i] = br.readLine();
					}

					for (int zeile = 0; zeile <= 8; zeile++) {
						for (int spalte = 0; spalte <= 8; spalte++) {

							tf[counter].setText(leser[counter]);

							if(tf[counter].getText().equals(" ")) {
								tf[counter].setEditable(true);									
							} 
							else {
								tf[counter].setEditable(false);								
							}							
							counter++;
						}
					}
					lbl.setText("DATEI GELESEN!");
					br.close();		

					//for(String eineZ : leser) {
					//	System.out.println(eineZ);
					//}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		buttonLoesung.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				istNrInZeile(9, 0);

				//TextField tf = (TextField) getNode(2, 3, raster);				
				//System.out.println(tf.getText());
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

	
	public static Node getNode(final int zeile,final int spalte,GridPane raster) {
		Node result = null;
		ObservableList<Node> childrens = raster.getChildren();
		for(Node node : childrens) {
			if(raster.getRowIndex(node) == zeile && raster.getColumnIndex(node) == spalte) {
				result = node;
				break;
			}
		}   
		return result;
	}

	public static boolean istNrInZeile(int nummer, int zeile) {
		String n = Integer.toString(nummer);
		for (int spalte = 0; spalte <= 8; spalte++) {
			TextField tf = (TextField) getNode(zeile, spalte, raster);	
			
			if(tf.getText().equals(n)) {
				lbl.setText("true");
				return true;					
			}			
		}
		lbl.setText("false");
		return false;		
	}
	
}
