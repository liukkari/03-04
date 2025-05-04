import Datasource.TranslationQueries;

import java.util.HashMap;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {
    private static final HashMap<String, String> LANGUAGE_CODES = new HashMap<>();
    private final TranslationQueries queries = new TranslationQueries();

    private Label appTitle;
    private ComboBox<String> comboBox;
    private TextField titleTextField;
    private TextField translationTextField;
    private ListView<String> titleListView;
    private Button saveButton;
    private Button fetchButton;

    static {
        LANGUAGE_CODES.put("English", "en");
        LANGUAGE_CODES.put("French", "fr");
        LANGUAGE_CODES.put("Spanish", "es");
        LANGUAGE_CODES.put("Japanese", "ja");
    }

    @Override
    public void start(Stage stage) {
        initializeUIComponents();
        configureEventHandlers();

        VBox layout = new VBox(10, appTitle, comboBox, titleListView, titleTextField, translationTextField, saveButton, fetchButton);
        Scene scene = new Scene(layout, 400, 300);

        stage.setTitle("Maria Aalto");
        stage.setScene(scene);
        stage.show();
    }

    private void initializeUIComponents() {
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
    }

    private void configureEventHandlers() {
        saveButton.setOnAction(e -> saveTranslation());
        fetchButton.setOnAction(e -> fetchTranslations());
    }

    private void saveTranslation() {
        String selectedLanguageCode = LANGUAGE_CODES.get(comboBox.getValue());
        queries.saveTranslation(titleTextField.getText(), selectedLanguageCode, translationTextField.getText());
    }

    private void fetchTranslations() {
        String selectedLanguageCode = LANGUAGE_CODES.get(comboBox.getValue());
        HashMap<String, String> translations = queries.getTranslations(selectedLanguageCode);

        titleListView.getItems().clear();
        translations.forEach((key, value) -> titleListView.getItems().add(key + ": " + value));
    }
}