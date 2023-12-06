module com.lab.lab7threads {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    exports org.lab;
    opens org.lab to javafx.fxml;
    opens org.lab.utils to javafx.base;
}