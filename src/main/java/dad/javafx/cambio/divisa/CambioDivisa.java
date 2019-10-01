package dad.javafx.cambio.divisa;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CambioDivisa extends Application {
	private TextField divisaPrimero, divisaSegundo;
	private ComboBox<String> divisasCombo1, divisasCombo2;
	private Button cambioButton;
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {

		divisaPrimero = new TextField();
		divisaPrimero.setPromptText("0");
		divisaPrimero.setMaxWidth(80);
		divisaPrimero.setMinWidth(80);

		divisaSegundo = new TextField();
		divisaSegundo.setPromptText("0");
		divisaSegundo.setMaxWidth(80);
		divisaSegundo.setMinWidth(80);
		divisaSegundo.setEditable(false);

		divisasCombo1 = new ComboBox<String>();
		divisasCombo1.getItems().addAll("Euro", "Dolar", "Libra", "Yen");
		divisasCombo1.getSelectionModel().selectFirst();
		divisasCombo1.setPromptText("Euro");

		divisasCombo2 = new ComboBox<String>();
		divisasCombo2.getItems().addAll("Dolar", "Euro", "Libra", "Yen");
		divisasCombo2.getSelectionModel().selectFirst();
		divisasCombo2.setPromptText("Dolar");

		cambioButton = new Button("Cambiar");
		cambioButton.setDefaultButton(true);
		cambioButton.setOnAction(e -> onButtonAction(e));

		HBox primeraBox = new HBox(5, divisaPrimero, divisasCombo1);
		primeraBox.setAlignment(Pos.CENTER);

		HBox segundaBox = new HBox(5, divisaSegundo, divisasCombo2);
		segundaBox.setAlignment(Pos.CENTER);

		VBox root = new VBox(5, primeraBox, segundaBox, cambioButton);
		root.setStyle("-fx-background-color:white");
		root.setAlignment(Pos.CENTER);

		Scene scene = new Scene(root, 320, 200);
		primaryStage.setTitle("Cambio divisa");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private Object onButtonAction(ActionEvent e) {

		String comboSelected1 = divisasCombo1.getSelectionModel().getSelectedItem();
		String comboSelected2 = divisasCombo2.getSelectionModel().getSelectedItem();
		String primerDato = divisaPrimero.getText();

		try {
			Double cantidad = Double.parseDouble(primerDato);

			Divisa euro = new Divisa("Euro", 1.0);
			Divisa libra = new Divisa("Libra", 0.8873);
			Divisa dolar = new Divisa("Dolar", 1.2007);
			Divisa yen = new Divisa("Yen", 133.59);
			//Valores por defecto
			Divisa origen = euro;
			Divisa destino = dolar;
			
			switch (comboSelected1) {

			case "Euro":
				origen = euro;
				break;
			case "Dolar":
				origen = dolar;
				break;
			case "Yen":
				origen = yen;
				break;
			case "Libra":
				origen = libra;
				break;
			}
			switch (comboSelected2) {

			case "Euro":
				destino = euro;
				break;
			case "Dolar":
				destino = dolar;
				break;
			case "Yen":
				destino = yen;
				break;
			case "Libra":
				destino = libra;
				break;
			}

			divisaPrimero.setPromptText(cantidad.toString());
			divisaSegundo.setPromptText(Divisa.fromTo(origen, destino, cantidad).toString());
		} catch (NumberFormatException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error al introducir datos");
			alert.setHeaderText("Error");
			alert.setContentText("Datos introducidos no válidos");
			alert.showAndWait();

		}

		return null;
	}

	public static void main(String[] args) {
		launch(args);

	}

}
