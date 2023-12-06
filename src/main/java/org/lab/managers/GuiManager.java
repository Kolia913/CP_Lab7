package org.lab.managers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.lab.utils.ThreadTableRowData;

public class GuiManager {
    @FXML private static TableView<ThreadTableRowData> table;

    public static void setTable(TableView<ThreadTableRowData> table) {
        GuiManager.table = table;
    }

    public static TableView<ThreadTableRowData> getTable() {
        return table;
    }

    @FXML
    static void addTableRow(ThreadTableRowData threadTableRowData) {
        if (table == null) {
            return;
        }

        table.getItems().add(threadTableRowData);
    }

    @FXML
    static void updateTableRow(String threadName, Thread.State state) {
        if (table == null) {
            return;
        }

        table.getItems().stream().filter(threadTableRowData -> threadTableRowData.getName().equals(threadName)).forEach(threadTableRowData -> {
            threadTableRowData.setState(state);
            table.refresh();
        });
    }

}
