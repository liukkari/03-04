package Datasource;

import java.sql.*;
import java.util.HashMap;

public class TranslationQueries {
    public void saveTranslation(String key, String language, String translation) {
        String query = "INSERT INTO translations (Key_name, Language_code, translation_text) VALUES (?, ?, ?)" +
                "ON DUPLICATE KEY UPDATE translation_text = VALUES(translation_text)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, key);
            ps.setString(2, language);
            ps.setString(3, translation);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> getTranslations(String language) {
        HashMap<String, String> translations = new HashMap<>();
        String query = "SELECT Key_name, translation_text FROM translations WHERE Language_code =?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, language);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String jobTitleKey = rs.getString("Key_name");
                String translationText = rs.getString("translation_text");
                translations.put(jobTitleKey, translationText);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return translations;
    }
}