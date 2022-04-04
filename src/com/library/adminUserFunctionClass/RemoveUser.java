package com.library.adminUserFunctionClass;

import com.library.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RemoveUser implements Initializable {
    Controller controller=new Controller();
    @FXML
    public TextField userIdToRemove;

    public RemoveUser() throws IOException {
    }

    @FXML
    public void removeUserFromDatabase(ActionEvent event) throws SQLException, IOException {
        removeUserFunction();
       }
       public void removeUserFunction() throws SQLException, IOException {
           if(userIdToRemove.getText().isEmpty()){
               controller.getAlert("Error","Empty","ID column is Empty please provide valid Id");
           }else{
               if(controller.serverConnection()!=null){
                   String id=userIdToRemove.getText();
                   String query="select UserId from university.users where UserId="+id;
                   String query2="delete from university.users where UserId="+id;
                   if(controller.serverConnection()!=null){
                       Statement statement =controller.serverConnection().createStatement();
                       ResultSet resultSet=statement.executeQuery(query);
                       if(resultSet.next()){
                           statement.executeUpdate(query2);
                           Alert alert =new Alert(Alert.AlertType.INFORMATION);
                           alert.setTitle("Deleted");
                           alert.setContentText("User with id "+id+" is successfully removed from the Database");
                           userIdToRemove.clear();
                           alert.show();
                       }else {
                           Alert alert = new Alert(Alert.AlertType.WARNING);
                           alert.setTitle("Retry");
                           alert.setHeaderText("Not found");
                           alert.setContentText("User with id  " + id + " is not found in the Database");
                           alert.show();
                           System.out.println("not found");
                       }
                       controller.serverConnection().close();
                   }
               }else{
                   controller.getAlert("Connection Lost","Try Again","Ensure you are connected to Internet");
               }
           }
       }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userIdToRemove.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                try {
                    removeUserFunction();
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
