package ee.tkasekamp.vickywaranalyzer;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ee.tkasekamp.vickywaranalyzer.controller.ErrorController;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// start is called on the FX Application Thread,
		// so Thread.currentThread() is the FX application thread:
//		Thread.setDefaultUncaughtExceptionHandler(Main::showError);

		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(
				"Main.fxml"));

		stage.setTitle("Victoria II war analyzer");
		stage.setScene(new Scene(root));
		stage.show();
		/* Cause I'm Estonian, thats why */
		stage.getIcons().add(new Image("/flags/EST.png"));

	}

	private static void showError(Thread t, Throwable e) {
		System.err.println("***Default exception handler***");
		if (Platform.isFxApplicationThread()) {
			showErrorDialog(e);
		} else {
			System.err.println("An unexpected error occurred in " + t);

		}
	}

	private static void showErrorDialog(Throwable e) {
		StringWriter errorMsg = new StringWriter();
		e.getCause().getCause().printStackTrace(new PrintWriter(errorMsg));
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader(Main.class.getClassLoader()
				.getResource("Error.fxml"));
		try {
			Parent root = loader.load();
			((ErrorController) loader.getController()).setErrorText(errorMsg
					.toString());
			dialog.setScene(new Scene(root, 250, 400));
			dialog.show();
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}

}
