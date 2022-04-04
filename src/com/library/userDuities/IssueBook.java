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

public class IssueBook implements Initializable {
    Controller controller=new Controller();
    @FXML
    TextField issueStudentId,issueBookId;

    public IssueBook() throws IOException {
    }

    public void issueBookjAction(ActionEvent event) throws SQLException, IOException {
issueBookFunction();

    }
    public void issueBookFunction() throws SQLException, IOException {
        if (issueBookId.getText().isEmpty() || issueStudentId.getText().isEmpty()) {
            controller.getAlert("Error", "Empty column", "Please ensure that you have filled each and every column");
        } else {
            String studentId = issueStudentId.getText();
            String bookId = issueBookId.getText();
            String date = String.valueOf(Controller.presentDate());
            String bookIdCheck = "select bookId from university.librarybooks where bookId=" + bookId;
            String bookIdCheckInIssue = "select bookId from university.issuebook where bookId=" + bookId;
            String studendIdCheck = "select studentId from university.students where studentId=" + studentId;
            String insertion = "insert into university.issuebook values(" + bookId + "," + studentId + "," + "'" + date + "'" + ");";

            if (controller.serverConnection() != null) {
                Statement statement = controller.serverConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(bookIdCheck);

                if (resultSet.next()) {                 //this is to check that this book id is present in library or not
                    resultSet = statement.executeQuery(bookIdCheckInIssue); //if yes it will check this id present in issued book table
                    if (resultSet.next()) {
                        controller.getAlert("Error", "Alredy Issued", "This book is alredy issued to someone else"); //if book alredy issued to someone it will give you an alert
                    } else {
                        //else it will move to check the availability to student id in a database
                        String checkFine="select studentId from university.fined where studentId="+studentId;
                        resultSet=statement.executeQuery(checkFine);
                        if(resultSet.next()){
                            String getAmount="select cost from university.fined where studentId="+studentId;
                            resultSet=statement.executeQuery(getAmount);
                            if(resultSet.next()){
                                String fine=resultSet.getString("cost");
                                Alert alert=new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("Fine");
                                alert.setTitle("Pending fine");
                                alert.setContentText("Book can not be issued to this person because of fine of Rupees "+fine);
                                alert.show();
                                issueStudentId.clear();
                                issueBookId.clear();
                            }

                        }else{
                            resultSet = statement.executeQuery(studendIdCheck);
                            if (resultSet.next()) {
                                String dataUpdateInLibrary = "update university.librarybooks set availability=" + 0 + " where bookId=" + bookId;
                                statement.executeUpdate(dataUpdateInLibrary);
                                int update = statement.executeUpdate(insertion);
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("successfull");
                                alert.setTitle("Book issued");
                                alert.setContentText("Book successfully issued to student with id " + studentId);
                                alert.show();
                                issueStudentId.clear();
                                issueBookId.clear();
                            } else {
                                controller.getAlert("Error", "Not found", "Student id that you have entered is not found Please enter valid student id.");
                            }
                        }

                    }

//                   resultSet=statement.executeQuery(updateBooks);
//                   if(resultSet.next()){
//                       String copies=resultSet.getString("currentCopies");
//                       int number=Integer.valueOf(copies);
//                       number-=1;
//                       String updateData="update university.librarybooks set currentCopies="+number+"where bookId="+bookId;
//                       statement.executeUpdate(updateData);
//
//                   }
                } else {
                    controller.getAlert("Error", "Not Found", "Book id that have entered is not found.");
                }
            }


        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
issueStudentId.setOnKeyPressed(keyEvent -> {
    if(keyEvent.getCode().equals(KeyCode.ENTER)){
        issueBookId.requestFocus();
    }
});
issueBookId.setOnKeyPressed(keyEvent -> {
    if(keyEvent.getCode().equals(KeyCode.ENTER)){
        try {
            issueBookFunction();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
});
    }
}
