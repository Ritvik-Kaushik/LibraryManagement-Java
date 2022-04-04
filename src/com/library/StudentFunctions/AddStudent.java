package com.library.StudentFunctions;

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
import java.security.Key;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ResourceBundle;

public class AddStudent implements Initializable {
    Controller controller =new Controller();
    @FXML
    TextField addStudentId,addStudentFName,studentSex,studentDepartment,studentCourse,studentEmail,studentContact,addStudentLName;
    @FXML
    DatePicker studentDate;

    public AddStudent() throws IOException {
    }

    @FXML
    public void addStudentButton(ActionEvent event) throws SQLException, ParseException, IOException {
addStudentFunction();
    }
    public void addStudentFunction() throws SQLException, ParseException, IOException {
        //        addStudentId,addStudentIdField,studentSex,studentDepartment,studentCourse,studentDate,studentEmail,studentContact,addStudentLName;
        if(addStudentId.getText().isEmpty()||addStudentFName.getText().isEmpty()||studentSex.getText().isEmpty()||studentDepartment.getText().isEmpty()||studentCourse.getText().isEmpty()||studentDate.getEditor().getText().isEmpty()||studentEmail.getText().isEmpty()||studentContact.getText().isEmpty()||addStudentLName.getText().isEmpty()){
            controller.getAlert("Error","Empty Filed","Please ensure that each and every column is filled");
        }else{
            String id=addStudentId.getText();
            String fName=addStudentFName.getText();
            String lName=addStudentLName.getText();
            String  sex=studentSex.getText();
            String department=studentDepartment.getText();
            String course=studentCourse.getText();
            String date=controller.dateFormatter(studentDate.getEditor().getText());
            String email=studentEmail.getText();
            String contact=studentContact.getText();
            String check="select studentId from university.students where studentId="+id;
            String sql = "insert into university.students " + "values(" + id + "," + "'" + fName + "'" + "," + "'" + lName + "'" + "," +"'"+ sex +"'" + "," +"'"+date +"'"+ ","+"'" +department+"'"+","+"'"+course+"'"+","+"'"+contact+"'"+","+"'"+email+"'"+");";
            if(controller.serverConnection()!=null){
                Statement statement=controller.serverConnection().createStatement();
                ResultSet resultSet=statement.executeQuery(check);
                if(resultSet.next()){
                    controller.getAlert("Warning","Student","Student is already present with this Id");
                }else{
                    statement.executeUpdate(sql);
                    addStudentId.clear();
                    addStudentLName.clear();
                    addStudentFName.clear();
                    studentSex.clear();
                    studentDepartment.clear();
                    studentCourse.clear();
                    studentDate.getEditor().clear();
                    studentEmail.clear();
                    studentContact.clear();
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Book Added");
                    alert.setHeaderText("Successfully");
                    alert.setContentText("Student "+fName+" is successfully Added");
                    alert.show();
                    controller.serverConnection().close();

                }
            }else{
                controller.getAlert("Not Found","Try Again","Database connection error");
            }
        }
        controller.serverConnection().close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       addStudentId.setOnKeyPressed(keyEvent -> {
           if(keyEvent.getCode().equals(KeyCode.ENTER)){
               addStudentFName.requestFocus();
           }
       });
       addStudentFName.setOnKeyPressed(keyEvent -> {
           if(keyEvent.getCode().equals(KeyCode.ENTER)){
               addStudentLName.requestFocus();
           }
       });
       addStudentLName.setOnKeyPressed(keyEvent -> {
           if(keyEvent.getCode().equals(KeyCode.ENTER)){
               studentSex.requestFocus();
           }
       });
       studentSex.setOnKeyPressed(keyEvent -> {
           if(keyEvent.getCode().equals(KeyCode.ENTER)){
               studentDepartment.requestFocus();
           }

       });
       studentDepartment.setOnKeyPressed(keyEvent ->{
           if(keyEvent.getCode().equals(KeyCode.ENTER)){
               studentCourse.requestFocus();
           }
       });
       studentCourse.setOnKeyPressed(keyEvent -> {
           if(keyEvent.getCode().equals(KeyCode.ENTER)){
               studentContact.requestFocus();
           }
       });
       studentContact.setOnKeyPressed(keyEvent -> {
           if(keyEvent.getCode().equals(KeyCode.ENTER)){
               studentEmail.requestFocus();
           }
       });
       studentEmail.setOnKeyPressed(keyEvent -> {
           if(keyEvent.getCode().equals(KeyCode.ENTER)){
               studentDate.requestFocus();
           }
       });
       studentDate.setOnKeyPressed(keyEvent -> {
           if(keyEvent.getCode().equals(KeyCode.ENTER)){
               try {
                   addStudentFunction();
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
