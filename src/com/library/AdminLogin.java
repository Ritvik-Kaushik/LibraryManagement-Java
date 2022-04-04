package com.library;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminLogin implements Initializable {
    Controller controller=new Controller();
    @FXML
    AnchorPane adminLoginPage;
    @FXML
    TextField adminId,adminPassword;

    public AdminLogin() throws IOException {
    }

    public void adminLogin( ) throws SQLException, IOException {         //for admin account login
      adminLoginFunction();
    }

    public void adminLoginToMainPage() throws IOException {
        controller.changeSceneAnchor(adminLoginPage,"MainWindow/MainWindow.fxml");  //Back to Main Page from admin page
    }
    public void adminLoginFunction() throws SQLException, IOException {
        if(controller.serverConnection()!=null){
            String user=adminId.getText();
            String password=adminPassword.getText();
            String query="select adminId,passwords from university.admins where passwords='"+password+"';";
            Statement statement=controller.serverConnection().createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            if(resultSet.next()){
                String getUserId=resultSet.getString("adminId");
                if(getUserId.equals(user)){
                    controller.changeSceneAnchor(adminLoginPage,"AdminMainWindow/adminPage.fxml");
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
adminId.setOnKeyPressed(keyEvent -> {
    if(keyEvent.getCode().equals(KeyCode.ENTER)){
        adminPassword.requestFocus();
    }
});
adminPassword.setOnKeyPressed(keyEvent -> {
    if(keyEvent.getCode().equals(KeyCode.ENTER)){
        try {
            adminLoginFunction();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
});
    }
}
