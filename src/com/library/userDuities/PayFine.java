package com.library.userDuities;

import com.library.Controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class PayFine implements Initializable {
 Controller controller=new Controller();
    @FXML
    TextField payFine;

    public PayFine() throws IOException {
    }

    public void checkFine(ActionEvent event) throws SQLException {


    }
    public void payFineFunction() throws SQLException, IOException {
        if(payFine.getText().isEmpty()){
            controller.getAlert("Error","Empty Column","Enter valid student id to check fine.");
        }else{
            String id=payFine.getText();
            String checkFine="select studentId from university.fined where studentId="+id+";";

            if(controller.serverConnection()!=null){
                Statement statement=controller.serverConnection().createStatement();
                ResultSet resultSet=statement.executeQuery(checkFine);
                if(resultSet.next()){
                    String getFine="select cost from university.fined where studentId= "+id;
//                    select studentId from university.fined where studentId=344;
                    resultSet=statement.executeQuery(getFine);
                    if(resultSet.next()){
                        String fine=resultSet.getString("cost");
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Pending");
                        alert.setTitle("You Have to pay");
                        alert.setContentText("You have to pay "+fine);
                        ButtonType Pay=new ButtonType("Pay");
                        ButtonType No=new ButtonType("No");

                        alert.getButtonTypes().setAll(Pay,No);
                        Optional<ButtonType> option=alert.showAndWait();
                        if(option.isPresent() && option.get()==Pay){
                            if(controller.serverConnection()!=null){
                                String clearFine="delete from university.fined where studentId="+id;
                                statement.executeUpdate(clearFine);
                                String transaction="update university.transactions set tDate="+"'"+controller.presentDate()+"'"+","+"Paid="+"'"+"Paid"+"'"+" where studentId="+id;
                                statement.executeUpdate(transaction);
                                Alert alert1=new Alert(Alert.AlertType.INFORMATION);
                                alert1.setHeaderText("successfully");
                                alert1.setTitle("Paid");
                                alert1.setContentText("Transaction successfull");
                                alert1.show();
                            }
                        }else{
                            System.out.println("not paying");
                        }
                        payFine.clear();
                    }
                }else{
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("clear");
                    alert.setTitle("No Fine");
                    alert.setContentText("There is no fine on this id "+id);
                    alert.show();

                }
            }else{
                controller.getAlert("Error","Connection Problem","Please ensure you are connected to server");
            }
        }
        controller.serverConnection().close();

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
payFine.setOnKeyPressed(keyEvent -> {
    try {
        payFineFunction();
    } catch (SQLException | IOException throwables) {
        throwables.printStackTrace();
    }
});
    }
}
