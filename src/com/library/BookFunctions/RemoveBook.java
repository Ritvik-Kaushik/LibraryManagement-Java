package com.library.BookFunctions;

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

public class RemoveBook implements Initializable {
    Controller controller=new Controller();
    @FXML
   public  TextField bookIdToRemove;

    public RemoveBook() throws IOException {
    }

    public void removeBookFunction() throws SQLException, IOException {
        if(bookIdToRemove.getText().isEmpty()){
            controller.getAlert("Error","Empty","ID column is Empty please provide valid Id");
        }else{
            if(controller.serverConnection()!=null){
                String id=bookIdToRemove.getText();;
                String query="select bookId from university.librarybooks where bookId="+id;
                String query2="delete from university.librarybooks where bookId="+id;
                if(controller.serverConnection()!=null){
                    Statement statement =controller.serverConnection().createStatement();
                    ResultSet resultSet=statement.executeQuery(query);
                    if(resultSet.next()){
                        statement.executeUpdate(query2);
                        Alert alert =new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Deleted");
                        alert.setContentText("Book with id "+id+" is successfully removed from the Database");
                        bookIdToRemove.clear();
                        alert.show();
                    }else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Retry");
                        alert.setHeaderText("Not found");
                        alert.setContentText("Book with id  " + id + " is not found in the Database");
                        alert.show();
                        System.out.println("not found");
                    }
                    controller.serverConnection().close();
                }
            }else{
                controller.getAlert("Connection Lost","Try Again","Ensure you are connected to Internet");
            }
        }
        controller.serverConnection().close();
    }
    public void removeBookDatabase(ActionEvent event) throws SQLException, IOException {
           removeBookFunction();

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        bookIdToRemove.setOnKeyPressed(keyEvent -> {
            bookIdToRemove.requestFocus();
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                try {
                    removeBookFunction();
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
