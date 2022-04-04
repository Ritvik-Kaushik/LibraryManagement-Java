package com.library.BookFunctions;

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

public class AddBook implements Initializable {
    Controller controller=new Controller();
    @FXML
    TextField bookId,bookTitle,authorName;
    @FXML
    DatePicker  publishDate;

    public AddBook() throws IOException {
    }

    public void addFunction() throws SQLException, ParseException, IOException {
        if(bookId.getText().isEmpty()||bookTitle.getText().isEmpty()||authorName.getText().isEmpty()||publishDate.getEditor().getText().isEmpty()){
            controller.getAlert("Error","Empty Field","Please fill each and every Field to continue.");
        }else{
            String id=bookId.getText();
            String name=bookTitle.getText();
            String authName=authorName.getText();
            String pubDate=Controller.dateFormatter(publishDate.getEditor().getText());
            String check="select bookId from university.librarybooks where bookId="+id;
            String sql = "insert into university.librarybooks " + "values(" + id + "," + "'" + name + "'" + "," + "'" + authName + "'" + "," +"'"+ pubDate +"'" + "," +1+");";
            if(controller.serverConnection()!=null){
                Statement statement=controller.serverConnection().createStatement();
                ResultSet resultSet=statement.executeQuery(check);
                if(resultSet.next()){
                    controller.getAlert("Warning","Book Id","Book is already present with this Id");
                }else{
                    int update=statement.executeUpdate(sql);
                    bookId.clear();
                    bookTitle.clear();
                    bookTitle.clear();
                    authorName.clear();
                    publishDate.getEditor().clear();
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Book Added");
                    alert.setHeaderText("Successfully");
                    alert.setContentText("Book "+name+" is successfully Added");
                    alert.show();
                    controller.serverConnection().close();
                }
            }else{
                controller.getAlert("Not Found","Try Again","Database connection error");
            }
        }
        controller.serverConnection().close();
    }
    public void addBookToDatabase(ActionEvent event) throws SQLException, ParseException, IOException {
        addFunction();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookId.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                bookTitle.requestFocus();
            }
        });
        bookTitle.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                authorName.requestFocus();
            }
        });
        authorName.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                publishDate.requestFocus();
            }
        });
        publishDate.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                try {
                    addFunction();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
