package com.library.userDuities;

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

public class Submit implements Initializable {
    Controller controller=new Controller();
    @FXML
    TextField submmitStudentId,issueBookId;

    public Submit() throws IOException {
    }

    public void subbmitBook(ActionEvent event) throws SQLException, IOException {
     submitBookFunction();
    }
    public void submitBookFunction() throws SQLException, IOException {
        if(submmitStudentId.getText().isEmpty()||issueBookId.getText().isEmpty()){
            controller.getAlert("Error","Empty Column","please filled each and every column");
        }else{
            String bookId=issueBookId.getText();
            String studentId=submmitStudentId.getText();
            String SubbmitDate=String.valueOf(Controller.presentDate());
            String checkBook="select bookId from university.issuebook where bookId="+bookId;
            String checkDate="select date from university.issuebook where bookId="+bookId;
            if(controller.serverConnection()!=null){
                Statement statement=controller.serverConnection().createStatement();
                ResultSet resultSet=statement.executeQuery(checkBook);
                if(resultSet.next()){
                    resultSet=statement.executeQuery(checkDate);
                    if(resultSet.next()){
                        String date=resultSet.getString("date");

                        if(controller.dateDifference(date,SubbmitDate)>15){

                            long difference=Math.abs(controller.dateDifference(date, SubbmitDate)-15);
                            long fine=difference*10;
                            String checkFine="select studentId from university.fined where studentId="+studentId;
                            resultSet=statement.executeQuery(checkFine);
                            if(resultSet.next()){
                                String gettingFine="select cost from university.fined where studentId="+studentId;
                                resultSet=statement.executeQuery(gettingFine);
                                if(resultSet.next()){
                                    long getFine=Integer.valueOf(resultSet.getString("cost"));
                                    long total=fine+getFine;
                                    String updateFine="update university.fined set cost="+total+" where studentId="+studentId;
                                    String transactions="update university.transactions set Fine="+total+" where studentId="+studentId;
                                    total=0;
                                    statement.executeUpdate(updateFine);
                                    statement.executeUpdate(transactions);
                                    String updateIssueBook="delete from university.issuebook where bookId="+bookId;
                                    statement.executeUpdate(updateIssueBook);
                                    String dataUpdateInLibrary = "update university.librarybooks set availability=" + 1 + " where bookId=" + bookId;
                                    statement.executeUpdate(dataUpdateInLibrary);
                                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                                    alert.setHeaderText("successfull");
                                    alert.setTitle("Submmited");
                                    alert.setContentText("Book submiited successfully");
                                    alert.show();
                                    issueBookId.clear();
                                    submmitStudentId.clear();
                                }

                            } else{
                                String insertFine="insert into university.fined values("+studentId+","+fine+");";
                                String transaction="insert into university.transactions(studentId,Fine,Paid) values"+"("+studentId+","+fine+","+"'"+"np"+"'"+");";
                                statement.executeUpdate(insertFine);
                                statement.executeUpdate(transaction);
                                String updateIssueBook="delete from university.issuebook where bookId="+bookId;
                                statement.executeUpdate(updateIssueBook);
                                String dataUpdateInLibrary = "update university.librarybooks set availability=" + 1 + " where bookId=" + bookId;
                                statement.executeUpdate(dataUpdateInLibrary);
                                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("successfull");
                                alert.setTitle("Submmited");
                                alert.setContentText("Book submiited successfully");
                                alert.show();
                                issueBookId.clear();
                                submmitStudentId.clear();
                            }


                        }
                        if( controller.dateDifference(date,SubbmitDate)<=15){
                            String updateIssueBook="delete from university.issuebook where bookId="+bookId;
                            statement.executeUpdate(updateIssueBook);
                            String dataUpdateInLibrary = "update university.librarybooks set availability=" + 1 + " where bookId=" + bookId;
                            statement.executeUpdate(dataUpdateInLibrary);
                            Alert alert=new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("successfull");
                            alert.setTitle("Submmited");
                            alert.setContentText("Book submiited successfully");
                            alert.show();
                            issueBookId.clear();
                            submmitStudentId.clear();

                        }
                    }


                }else {
                    controller.getAlert("Error","wrong book id","Enter valid id.");
                }


            }
        }
        controller.serverConnection().close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
submmitStudentId.setOnKeyPressed(keyEvent -> {
    if(keyEvent.getCode().equals(KeyCode.ENTER)){
        issueBookId.requestFocus();
    }
});
issueBookId.setOnKeyPressed(keyEvent -> {
    if(keyEvent.getCode().equals(KeyCode.ENTER)){
        try {
            submitBookFunction();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
});
    }
}
