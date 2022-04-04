package com.library.BookFunctions;

import com.library.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UpdateBook implements Initializable {
    Controller controller=new Controller();
    @FXML
    TextField updateId,newValueUpdate;
    @FXML
    ChoiceBox updateChoice;

    public UpdateBook() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
updateChoice.getItems().setAll("bookId","bookName","authorName","publicationYear","totalCopies","currentCopies");
updateId.setOnKeyPressed(keyEvent -> {
    if(keyEvent.getCode().equals(KeyCode.ENTER)){
        newValueUpdate.requestFocus();
    }
});
newValueUpdate.setOnKeyPressed(keyEvent -> {
    if(keyEvent.getCode().equals(KeyCode.ENTER)){
        updateChoice.requestFocus();
    }
});
updateChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
    @Override
    public void changed(ObservableValue observableValue, Object o, Object t1) {
        if(updateId.getText().isEmpty()||newValueUpdate.getText().isEmpty()){
            controller.getAlert("Error","Empty","ID column is Empty please provide valid Id");
        }else{
            try {
                String id=updateId.getText();
                String valueToUpdate=newValueUpdate.getText();
                String check="select bookId from university.librarybooks where bookId="+id;
                String sql="update university.librarybooks set "+t1+"="+"'"+valueToUpdate+"'"+" where bookId="+id;
                if(controller.serverConnection()!=null){

                    Statement statement =controller.serverConnection().createStatement();
                    ResultSet resultSet=statement.executeQuery(check);
                    if(resultSet.next()){
                        statement.executeUpdate(sql);
                        updateId.clear();
                        newValueUpdate.clear();

                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("User Updated");
                        alert.setHeaderText("Successfully");
                        alert.setContentText("User with "+id+" is successfully Updated");
                        alert.show();
                    }else{
                        controller.getAlert("Not Found","Try Again","User with this Id not found");
                        updateId.clear();
                        newValueUpdate.clear();
                    }
                    controller.serverConnection().close();
                }
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }

        }
    }
});
    }
}
