module br.jaulipo.controleestoque {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens br.jaulipo.controleestoque to javafx.fxml;
    exports br.jaulipo.controleestoque;
    exports br.jaulipo.factory;
}