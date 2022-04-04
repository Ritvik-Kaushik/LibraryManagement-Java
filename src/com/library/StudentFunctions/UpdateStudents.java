package com.library.StudentFunctions;

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

public class UpdateStudents implements Initializable {
    Controller controller=new Controller();
    @FXML
    TextField studentUpdateId,studentUpdateValue;
    @FXML
    ChoiceBox studenUpdateChoice;

    public UpdateStudents() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
   studenUpdateChoice.getItems().setAll("studentId","studentFirstName","studentLastName","sex","dateOfBirth","Department","course","contactNumber","emailId");
   studentUpdateId.setOnKeyPressed(keyEvent -> {
       if(keyEvent.getCode().equals(KeyCode.ENTER)){
           studentUpdateValue.requestFocus();
       }
   });
   studentUpdateValue.setOnKeyPressed(keyEvent -> {
       studenUpdateChoice.requestFocus();
   });
   studenUpdateChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
       @Override
       public void changed(ObservableValue observableValue, Object o, Object t1) {
           if(studentUpdateId.getText().isEmpty()||studentUpdateValue.getText().isEmpty()){
             controller.getAlert("Error","Empty","ID or New value column is empty provide valid id ");
           }else{


               try {
                   String id=studentUpdateId.getText();
                   String valueToUpdate=studentUpdateValue.getText();
                   String check="select studentId from university.students where studentId="+id;
                   String query="update university.students set "+t1+"="+"'"+valueToUpdate+"'"+" where studentId="+id;
                   if(controller.serverConnection()!=null){
                       Statement statement =controller.serverConnection().createStatement();
                       ResultSet resultSet=statement.executeQuery(check);
                       if(resultSet.next()){
                           statement.executeUpdate(query);
                           studentUpdateValue.clear();
                           studentUpdateId.clear();
                           Alert alert=new Alert(Alert.AlertType.INFORMATION);
                           alert.setTitle("Student Updated");
                           alert.setHeaderText("Successfully");
                           alert.setContentText("Student with "+id+" is successfully updated");
                           alert.show();
                       }else{
                           controller.getAlert("Not found ","Try again","User with this id not found");
                           studentUpdateId.clear();
                           studentUpdateValue.clear();
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
