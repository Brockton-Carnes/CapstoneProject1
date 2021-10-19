module SceneBuilderApp {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.desktop;
	requires javafx.base;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
}
