package com.library.StudentFunctions;

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

public class RemoveStudent implements Initializable {
    Controller controller=new Controller();
    @FXML
    TextField studentIdToRemove;

    public RemoveStudent() throws IOException {
    }

    public  void studentRemoveButoon(ActionEvent event) throws SQLException, IOException {
    removeStudentFunction();
   }
   public void removeStudentFunction() throws SQLException, IOException {
       if(studentIdToRemove.getText().isEmpty()){
           controller.getAlert("Error","Empty","Id column is empty please enter valid id to remove Student");
       }else{
           String id =studentIdToRemove.getText();
           String check="select studentId from university.students where studentId="+id;
           String query="delete from university.students where studentId="+id;
           if(controller.serverConnection()!=null){
               Statement statement=controller.serverConnection().createStatement();
               ResultSet resultSet=statement.executeQuery(check);
               if(resultSet.next()){
                   statement.executeUpdate(query);
                   Alert alert=new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("Deleted");
                   alert.setContentText("Student with id "+id+" is successfully removed from the Database");
                   alert.show();
               }else{
                   Alert alert =new Alert(Alert.AlertType.WARNING);
                   alert.setTitle("Retry");;
                   alert.setHeaderText("Not found");
                   alert.setContentText("Student with id "+id+" is not found in the Database");
                   alert.show();
               }
               controller.serverConnection().close();

           }else{
               controller.getAlert("Connection Lost","Try Again","Ensure you are connected to Internet");
           }
       }
   }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
studentIdToRemove.setOnKeyPressed(keyEvent -> {
    if(keyEvent.getCode().equals(KeyCode.ENTER)){
        try {
            removeStudentFunction();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
});
    }
}
