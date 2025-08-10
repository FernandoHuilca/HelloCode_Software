package GestionAprendizaje_Modulo.Controladores;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

import java.awt.Desktop;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

public class WebController {

    @FXML private WebView webView;
    @FXML private Label titleLabel;
    @FXML private Button backButton;
    @FXML private Button forwardButton;
    @FXML private Button reloadButton;
    @FXML private Button externalButton;
    @FXML private ProgressBar progressBar;

    private String currentUrl = "";

    @FXML
    private void initialize() {
        WebEngine engine = webView.getEngine();

        engine.titleProperty().addListener((obs, oldV, newV) -> {
            if (newV != null && !newV.isBlank()) {
                titleLabel.setText(newV);
            } else if (currentUrl != null) {
                titleLabel.setText(currentUrl);
            }
        });

        progressBar.progressProperty().bind(engine.getLoadWorker().progressProperty());
        engine.getLoadWorker().runningProperty().addListener((obs, wasRunning, isRunning) -> {
            progressBar.setVisible(isRunning);
        });

        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> updateNavButtons());
        webView.getEngine().getHistory().getEntries().addListener(
                (javafx.collections.ListChangeListener<WebHistory.Entry>) change -> updateNavButtons()
        );

        updateNavButtons();
    }

    private void updateNavButtons() {
        Platform.runLater(() -> {
            WebHistory history = webView.getEngine().getHistory();
            int index = history.getCurrentIndex();
            backButton.setDisable(index <= 0);
            forwardButton.setDisable(index >= history.getEntries().size() - 1);
        });
    }

    public void loadUrl(String url) {
        String normalized = normalizeUrl(url);
        currentUrl = normalized;

        if (isPdf(normalized)) {
            String encoded = encode(normalized);
            String gview = "https://docs.google.com/gview?embedded=true&url=" + encoded;
            webView.getEngine().load(gview);
        } else {
            webView.getEngine().load(normalized);
        }
    }

    public void setWindowTitleFallback(String title) {
        if (titleLabel != null && (titleLabel.getText() == null || titleLabel.getText().isBlank())) {
            titleLabel.setText(title);
        }
    }

    private String normalizeUrl(String url) {
        if (url == null) return "";
        String trimmed = url.trim();
        if (!Pattern.compile("^(?i)(https?|ftp)://").matcher(trimmed).find()) {
            trimmed = "https://" + trimmed;
        }
        return trimmed;
    }

    private boolean isPdf(String url) {
        return url.toLowerCase().contains(".pdf");
    }

    private String encode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }

    @FXML
    private void onBack() {
        WebHistory history = webView.getEngine().getHistory();
        if (history.getCurrentIndex() > 0) {
            history.go(-1);
        }
    }

    @FXML
    private void onForward() {
        WebHistory history = webView.getEngine().getHistory();
        if (history.getCurrentIndex() < history.getEntries().size() - 1) {
            history.go(1);
        }
    }

    @FXML
    private void onReload() {
        webView.getEngine().reload();
    }

    @FXML
    private void onOpenExternal() {
        try {
            String url = webView.getEngine().getLocation();
            if (url == null || url.isBlank()) url = currentUrl;
            if (url != null && !url.isBlank() && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new java.net.URI(url));
            }
        } catch (Exception ignored) {
        }
    }

    @FXML
    private void onCloseWindow() {
        javafx.stage.Stage stage = (javafx.stage.Stage) webView.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }
}