import Datasource.TranslationQueries;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {
    private TranslationQueries queries = new TranslationQueries();
    private Label appTitle;
    private ComboBox<String> comboBox;
    private TextField titleTextField;
    private TextField translationTextField;
    private ListView<String> titleListView;
    private Button saveButton;
    private Button fetchButton;

    private static final HashMap<String, String> LANGUAGE_CODES = new HashMap<>();

    @Override
    public void start(Stage stage) {
        LANGUAGE_CODES.put("English", "en");
        LANGUAGE_CODES.put("French", "fr");
        LANGUAGE_CODES.put("Spanish", "es");
        LANGUAGE_CODES.put("Japanese", "ja");

        appTitle = new Label("Employee Job Titles");

        comboBox = new ComboBox<>(FXCollections.observableArrayList(LANGUAGE_CODES.keySet()));
        comboBox.getSelectionModel().selectFirst();

        titleTextField = new TextField();
        titleTextField.setPromptText("Enter Key Name");

        translationTextField = new TextField();
        translationTextField.setPromptText("Enter Translation");

        titleListView = new ListView<>();

        saveButton = new Button("Add/Update Translation");
        fetchButton = new Button("Fetch Translation");

        saveButton.setOnAction(e -> {
            String selectedLanguageCode = LANGUAGE_CODES.get(comboBox.getValue());
            queries.saveTranslation(titleTextField.getText(), selectedLanguageCode, translationTextField.getText());
        });

        fetchButton.setOnAction(e -> {
            String selectedLanguageCode = LANGUAGE_CODES.get(comboBox.getValue());
            HashMap<String, String> translations = queries.getTranslations(selectedLanguageCode);

            titleListView.getItems().clear();
            for (String key : translations.keySet()) {
                String formattedTranslation = key + ": " + translations.get(key);
                titleListView.getItems().add(formattedTranslation);
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(appTitle, comboBox, titleListView, titleTextField, translationTextField, saveButton, fetchButton);

        Scene scene = new Scene(layout, 400, 300);
        stage.setTitle("Maria Aalto");
        stage.setScene(scene);
        stage.show();
    }
}