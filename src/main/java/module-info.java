module br.jaulipo.controleestoque {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens br.jaulipo.controleestoque to javafx.fxml;
    opens br.jaulipo.view to javafx.graphics, javafx.fxml;
    opens br.jaulipo.model to javafx.base;
    exports br.jaulipo.controleestoque;
    exports br.jaulipo.factory;
    exports br.jaulipo.view;
}
