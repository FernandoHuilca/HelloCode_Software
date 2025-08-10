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

        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> actualizarBotonesNavegacion());
        webView.getEngine().getHistory().getEntries().addListener(
                (javafx.collections.ListChangeListener<WebHistory.Entry>) change -> actualizarBotonesNavegacion()
        );

        // Al ocultar/cerrar la ventana, detener medios y limpiar
        webView.sceneProperty().addListener((o, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.windowProperty().addListener((o2, oldWin, newWin) -> {
                    if (newWin != null) {
                        newWin.setOnHidden(ev -> {
                            detenerMedios();
                            cargarEnBlanco();
                        });
                    }
                });
            }
        });

        actualizarBotonesNavegacion();
    }

    private void actualizarBotonesNavegacion() {
        Platform.runLater(() -> {
            WebHistory history = webView.getEngine().getHistory();
            int index = history.getCurrentIndex();
            backButton.setDisable(index <= 0);
            forwardButton.setDisable(index >= history.getEntries().size() - 1);
        });
    }

    // Alias en español para compatibilidad con otras partes del código
    public void cargarUrl(String url) {
        loadUrl(url);
    }

    public void loadUrl(String url) {
        String normalized = normalizarUrl(url);
        currentUrl = normalized;

        if (esPdf(normalized)) {
            String encoded = codificar(normalized);
            String gview = "https://docs.google.com/gview?embedded=true&url=" + encoded;
            webView.getEngine().load(gview);
        } else {
            webView.getEngine().load(normalized);
        }
    }

    // Alias en español para compatibilidad con otras partes del código
    public void establecerTituloVentanaPorDefecto(String title) {
        setWindowTitleFallback(title);
    }

    public void setWindowTitleFallback(String title) {
        if (titleLabel != null && (titleLabel.getText() == null || titleLabel.getText().isBlank())) {
            titleLabel.setText(title);
        }
    }

    private String normalizarUrl(String url) {
        if (url == null) return "";
        String trimmed = url.trim();
        if (!Pattern.compile("^(?i)(https?|ftp)://").matcher(trimmed).find()) {
            trimmed = "https://" + trimmed;
        }
        return trimmed;
    }

    private boolean esPdf(String url) {
        return url.toLowerCase().contains(".pdf");
    }

    private String codificar(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }

    // Detiene audio/video en la página actual (y si es posible en iframes), y cancela cargas en curso
    private void detenerMedios() {
        try {
            webView.getEngine().executeScript(
                    "(function(){try{"
                            + "document.querySelectorAll('video').forEach(function(v){try{v.pause();v.src='';v.load();}catch(e){}});"
                            + "document.querySelectorAll('audio').forEach(function(a){try{a.pause();a.src='';a.load();}catch(e){}});"
                            + "document.querySelectorAll('iframe').forEach(function(f){try{var d=f.contentDocument||f.contentWindow.document;"
                            + "if(d){d.querySelectorAll('video,audio').forEach(function(m){try{m.pause();m.src='';m.load();}catch(e){}});}}catch(e){}});"
                            + "}catch(e){}})();"
            );
        } catch (Exception ignored) { }
        try {
            webView.getEngine().getLoadWorker().cancel();
        } catch (Exception ignored) { }
    }

    // Carga una página en blanco de forma segura para liberar medios
    private void cargarEnBlanco() {
        try {
            webView.getEngine().load("about:blank");
        } catch (Exception ignored) { }
    }

    @FXML
    private void irAtras() {
        detenerMedios();
        WebHistory history = webView.getEngine().getHistory();
        if (history.getCurrentIndex() > 0) {
            history.go(-1);
        } else {
            cargarEnBlanco();
        }
    }

    @FXML
    private void irAdelante() {
        detenerMedios();
        WebHistory history = webView.getEngine().getHistory();
        if (history.getCurrentIndex() < history.getEntries().size() - 1) {
            history.go(1);
        }
    }

    @FXML
    private void recargar() {
        detenerMedios();
        webView.getEngine().reload();
    }

    @FXML
    private void abrirEnNavegadorExterno() {
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
    private void cerrarVentana() {
        detenerMedios();
        cargarEnBlanco();
        javafx.stage.Stage stage = (javafx.stage.Stage) webView.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }
}