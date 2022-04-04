package com.library;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UserLogin implements Initializable {
    Controller controller=new Controller();
    @FXML
    TextField userId,userPassword;
    @FXML
    AnchorPane userLoginAnchor;

    public UserLogin() throws IOException {
    }

    public void  backUserPageToMain() throws IOException {
        controller.changeSceneAnchor(userLoginAnchor,"MainWindow/MainWindow.fxml");
    }
    public void buttonLogin( ) throws SQLException, IOException {//For user account Login
      userLoginFunction();
    }
    public void userLoginFunction() throws SQLException, IOException {
        if(controller.serverConnection()!=null){
            String user=userId.getText();
            String password=userPassword.getText();
            String query="select UserId,Passwords from university.users where Passwords='"+password+"';";
            Statement statement=controller.serverConnection().createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            if(resultSet.next()){
                String getUserId=resultSet.getString("UserId");
                if(getUserId.equals(user)){
                    //TODO
                    controller.changeSceneAnchor(userLoginAnchor,"UserMainWindow/userFunctionMainPage.fxml");
                }
            }
            else{
                controller.getAlert("Not Found","Try Again","Id or Password may ne incorrect please check and then try again.");
            }
        }else{
            controller.getAlert("Connection Lost","Try Again","Ensure you are connected to Internet");
        }
        controller.serverConnection().close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
userId.setOnKeyPressed(keyEvent -> {
    if(keyEvent.getCode().equals(KeyCode.ENTER)){
        userPassword.requestFocus();
    }
});
userPassword.setOnKeyPressed(keyEvent -> {
    if(keyEvent.getCode().equals(KeyCode.ENTER)){
        try {
            userLoginFunction();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

});
    }
}
