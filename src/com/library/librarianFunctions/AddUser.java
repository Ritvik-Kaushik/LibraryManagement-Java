package com.library.librarianFunctions;

import com.library.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ResourceBundle;

public class AddUser implements Initializable {
    Controller controller=new Controller();
    @FXML
    public TextField addUserId,userName,userLastName,userEmail,userPhone,userAddress,userAddPassword;
    @FXML
    public DatePicker userDate;

    public AddUser() throws IOException {
    }

    @FXML
    public void addUserButton(ActionEvent event) throws SQLException, ParseException, IOException {
        addUserFunction();
      }
      public void addUserFunction() throws SQLException, IOException, ParseException {
          if(addUserId.getText().isEmpty()||userName.getText().isEmpty()|userLastName.getText().isEmpty()||userEmail.getText().isEmpty()||userPhone.getText().isEmpty()||userAddress.getText().isEmpty()||userDate.getEditor().getText().isEmpty()||userAddPassword.getText().isEmpty()){  //This condition ensure that user fill each and every column Error.Fixed
              controller.getAlert("Error","Empty Column","Please ensure that you have filled each every column");
//              AnchorPane pane= FXMLLoader.load(getClass().getResource("../../AdminUserFunction/addUser.fxml"));
          }else{
              String id=addUserId.getText();
              String firstName=userName.getText();
              String lastName=userLastName.getText();
              String email=userEmail.getText();
              String phone=userPhone.getText();
              String address=userAddress.getText();
              String dob=controller.dateFormatter(userDate.getEditor().getText());
              String pass=userAddPassword.getText();
              String checkQuery="select UserId from university.users where UserId="+id;
              String sql = "insert into university.users " + "values(" + id + "," + "'" + firstName + "'" + "," + "'" + lastName + "'" + "," +"'"+ email +"'" + "," +"'"+phone+"'" + "," +"'"+address+"'" + ","+"'" +dob+"'" + "," +"'"+pass+"'"+ ")";
              if(controller.serverConnection()!=null){
                  Statement statement=controller.serverConnection().createStatement();
                  ResultSet resultSet=statement.executeQuery(checkQuery);
                  if(resultSet.next()){

                      controller.getAlert("Warning","User Id","User with this Id is alredy Present");

                  }else{
                      int update=statement.executeUpdate(sql);
                      System.out.println(update);
                      addUserId.clear();
                      userName.clear();
                      userLastName.clear();
                      userEmail.clear();
                      userPhone.clear();
                      userDate.getEditor().clear();
                      userAddPassword.clear();
                      userAddress.clear();
                      Alert alert=new Alert(Alert.AlertType.INFORMATION);
                      alert.setTitle("User Added");
                      alert.setHeaderText("Successfully");
                      alert.setContentText("User "+firstName+" is successfully added to database");
                      alert.show();
                      controller.serverConnection().close();}
              }else {
                  controller.getAlert("Not Found","Try Again","Id or Password may ne incorrect please check and then try again.");
              }
          }
      }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
addUserId.setOnKeyPressed(keyEvent -> {
    if(keyEvent.getCode().equals(KeyCode.ENTER)){
        userName.requestFocus();
    }
});
userName.setOnKeyPressed(keyEvent -> {
    if(keyEvent.getCode().equals(KeyCode.ENTER)){
        userLastName.requestFocus();
    }
});
userLastName.setOnKeyPressed(keyEvent -> {
    if(keyEvent.getCode().equals(KeyCode.ENTER)){
        userEmail.requestFocus();
    }
});
userEmail.setOnKeyPressed(keyEvent -> {
    if(keyEvent.getCode().equals(KeyCode.ENTER)){
        userPhone.requestFocus();
    }
});
userPhone.setOnKeyPressed(keyEvent -> {
    if(keyEvent.getCode().equals(KeyCode.ENTER)){
        userAddress.requestFocus();
    }
});
userAddress.setOnKeyPressed(keyEvent -> {
    if(keyEvent.getCode().equals(KeyCode.ENTER)){
        userDate.requestFocus();
    }
});
userDate.setOnKeyPressed(keyEvent -> {
    if(keyEvent.getCode().equals(KeyCode.ENTER)){
        userAddPassword.requestFocus();
    }
});
userAddPassword.setOnKeyPressed(keyEvent -> {
    if(keyEvent.getCode().equals(KeyCode.ENTER)){
        try {
            addUserFunction();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

});
    }
}
