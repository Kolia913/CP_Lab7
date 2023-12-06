package org.lab;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.lab.utils.ThreadTableRowData;
import org.lab.managers.GuiManager;
import org.lab.managers.ThreadManager;


public class Controller {

    @FXML private javafx.scene.control.TextField threadsInput;
    @FXML private javafx.scene.control.TableView<ThreadTableRowData> table;
    private ThreadManager threadManager;

    public void initialize() {
          table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
          table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("state"));
          table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("priority"));
          table.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("lastMessage"));
          table.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("lastStatusChangeTime"));

        GuiManager.setTable(table);
    }

    @FXML
    protected void onFieldKeyTyped() {
        if (!threadsInput.getText().isEmpty()) {
            try {
                int threads = Integer.parseInt(threadsInput.getText());
                if (threads < 1) {
                    threadsInput.setText("1");
                } else if (threads > 10) {
                    threadsInput.setText("10");
                }
            } catch (NumberFormatException e) {
                threadsInput.setText("1");
            }
        }
    }

    @FXML
    protected void onStartButtonClicked() {
        if (threadsInput.getText().isEmpty()) {
            threadsInput.setText("1");
        }

        if (threadManager == null) {
            threadManager = new ThreadManager(Integer.parseInt(threadsInput.getText()));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Parking simulator is already running");
            alert.showAndWait();
            return;
        }

        threadManager.start();
    }

    protected void alertNotStarted() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Parking simulator is not running");
        alert.setContentText("Please start the simulation first");
        alert.showAndWait();
    }

    @FXML
    protected void onPauseButtonClicked() {
        if (threadManager == null) {
            alertNotStarted();
            return;
        }

        // get selected row
        ThreadTableRowData selectedThread = table.getSelectionModel().getSelectedItem();
        if (selectedThread == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No thread selected");
            alert.setContentText("Please select a thread to pause");
            alert.showAndWait();
            return;
        }

        threadManager.pauseThread(selectedThread.getName());
    }

    @FXML
    protected void onResumeButtonClicked() {
        if (threadManager == null) {
            alertNotStarted();
            return;
        }

        ThreadTableRowData selectedThread = table.getSelectionModel().getSelectedItem();
        if (selectedThread == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No thread selected");
            alert.setContentText("Please select a thread to resume");
            alert.showAndWait();
            return;
        }

        threadManager.resumeThread(selectedThread.getName());
    }

}
