package com.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    String[]loginArray;

    public Controller() throws IOException {

   BufferedReader reader=new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("logins.txt")));
   loginArray=reader.readLine().split(" ");

    }
    //Boolean variable to ensure User view only at a time
    public boolean showUserCondition=true;
    //Boolean variable to ensure Book view only at a time
    boolean showBookCondition=true;
    //Boolean variable to ensure student view only at a time
    boolean showStudentCondition=true;
    //----------------------------->Adding Unique Ids of structure which is built by the scene builder<-----------------------------
    @FXML
 public AnchorPane userAdminAnchor,adminPageAnchor,mainPageAnchor,adminLoginPage,userViewAnchor,userLoginAnchor,viewBookpane,studentAdminAnchor,userAnchorPane,bookPageVbox,userRootAnchor,adminAnchorPane,viewStudent;
    @FXML
    public VBox userPageAnchor,studentVbox;
    @FXML
    public TextField userId,adminId,searchUser,searchBook,searchStudent;
    @FXML
    public PasswordField userPassword,adminPassword;



    //----------------------------->Finished<-----------------------------
    //----------------------------------->Various Action events to handle Scene change<-----------------------------------
    @FXML
    public void adminBookToBookPage() throws IOException {
    changeSceneAnchor(adminPageAnchor,"AdminBookFunctionsFxml/adminBookFunctions.fxml");
    }
    @FXML
 public void adminButton() throws IOException {
        changeSceneAnchor(mainPageAnchor,"LoginFxml/adminLogin.fxml");  //Main page to Admin login page
    }
    public  void logoutAdmin() throws IOException {//Admin Page to Main Page
        changeSceneAnchor(adminPageAnchor,"MainWindow/MainWindow.fxml");
    }
    @FXML
    public void userLoginButton() throws IOException {
        changeSceneAnchor(mainPageAnchor,"LoginFxml/userLogin.fxml");  //Main Page to user Login Page
    }
    @FXML
    public void adminuserPageToAdminPage() throws IOException {
        showUserCondition=true;
        changeSceneAnchor(userAdminAnchor,"AdminMainWindow/adminPage.fxml");   //To change the scene from AdminUser to Admin Page
    }
    public void adminPageToUserPage() throws IOException {
        showUserCondition=true;
        changeSceneAnchor(adminPageAnchor,"AdminUserFunction/adminUserFunction.fxml");  //To change the scene from Admin page to AdminUser
    }
    @FXML
    public  void removeUser() throws IOException {
        showBookCondition=true;
        showUserCondition=true;

        getChildrenAnchors(userAnchorPane,"AdminUserFunction/removeUser.fxml");
    }
    @FXML
    public void addUser() throws IOException {
        showUserCondition=true;                           //To Move to Add user page.
        getChildrenAnchors(userAnchorPane,"AdminUserFunction/addUser.fxml");
    }
    @FXML
    public void addBooks() throws IOException {
      showBookCondition=true;
        getChildrenAnchors(bookPageVbox,"AdminBookFunctionsFxml/addBooks.fxml");
    }
    @FXML
    public void removeBookButton() throws IOException {
        showBookCondition=true;
        getChildrenAnchors(bookPageVbox,"AdminBookFunctionsFxml/removeBook.fxml");


    }
    //this is to change the scene from admin page to Student Page
    @FXML
    public void studentPage() throws IOException {
        changeSceneAnchor(adminPageAnchor,"StudentFunction/StudentFunctionsPage.fxml");
    }
    //this is to open add student page
    public void addStudentPage() throws IOException {
        showStudentCondition=true;
        getChildrenAnchors(studentAdminAnchor,"StudentFunction/addStudent.fxml");
    }
    //to change scene to update menu
    public void updateUser() throws IOException {
        showUserCondition=true;

        getChildrenAnchors(userAnchorPane,"AdminUserFunction/Update.fxml");
    }
    //to change scene to update Book
    public void updateBook() throws IOException {
        showBookCondition=true;
        getChildrenAnchors(bookPageVbox,"AdminBookFunctionsFxml/updateBook.fxml");
    }
    //This is to change the scene from userFunctions to remove Student  Page
    public void removeStudentScene() throws IOException {
        showStudentCondition=true;
        getChildrenAnchors(studentAdminAnchor,"StudentFunction/removeStudent.fxml");
    }
    //this function is use to change the scene to update functions
    public void updateStudentScene() throws IOException {
        showStudentCondition=true;
        getChildrenAnchors(studentAdminAnchor,"StudentFunction/updateStudent.fxml");
    }
    //this for user login

//from here we are starting functions to change scenes for users
    public void issueBookButton() throws IOException {

        getChildrenAnchors(userRootAnchor,"librarianFunctions/issueBook.fxml");

    }
//to change scene to subbmit page
    public void returnBookPage() throws IOException {
        getChildrenAnchors(userRootAnchor,"librarianFunctions/submmitBook.fxml");
    }
    //to change the scene to pay fine page
    public void payFinePage() throws IOException {
        getChildrenAnchors(userRootAnchor,"librarianFunctions/payFine.fxml");
    }
    //this action to change the scene from admin page to payment received.
    public void paymentReceived() throws IOException, SQLException {

        AnchorPane pane=FXMLLoader.load(getClass().getResource("AdminMainWindow/PaymentReceived.fxml"));
        adminAnchorPane.getChildren().setAll(pane);
        AnchorPane.setRightAnchor(pane,0.0);
        AnchorPane.setLeftAnchor(pane,0.0);
        AnchorPane.setTopAnchor(pane,0.0);
        AnchorPane.setBottomAnchor(pane,0.0);
        transactionTable(pane);
    }
    //------------------------------------->Change Scene Finished<-------------------------------------
//functions used to clear the all transaction made
    public  void  clearTransactions() throws SQLException, IOException {
        if(serverConnection()!=null){
            Statement statement=serverConnection().createStatement();
            String query="truncate table university.transactions;";
            statement.executeUpdate(query);
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Successful");
            alert.setTitle("Cleared");
            alert.setContentText("All Transactions Cleared");
            alert.show();

        }
    }
    //to get the total amount received until now
    public void totalAmount( ) throws SQLException, IOException {
        if(serverConnection()!=null){
            String query="select  Fine from university.transactions;";

            Statement statement=serverConnection().createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            int amount=0;
            while(resultSet.next()){
                 amount+=Integer.parseInt(resultSet.getString("Fine"));

            }
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Successfull");
            alert.setTitle("Total Amount");
            alert.setContentText("Total amount received Rupees "+amount);
            alert.show();
        }
    }



//   ---->Logins Implementation Finished<-------------------------
//Action to view defaulters in user
    @FXML
    public void defulaterAction( ) throws SQLException, IOException {
        defaulterTable(userRootAnchor);
    }
    //Action to view defaulter in admin
    @FXML
    public void adminDefaulters( ) throws SQLException, IOException {
        defaulterTable(adminAnchorPane);
    }
    //search user
    public void searchUser() throws SQLException, IOException {
        if(searchUser.getText().isEmpty()){
          getAlert("Error","Empty Column","Enter valid user Id");
        }else {
            String id=searchUser.getText();
            String query="select*from university.users where UserId="+id;
           userTable(userViewAnchor,query);
           searchUser.clear();
        }

    }
  //Action to view users
    public void allUsers() throws SQLException, IOException {
        userTable(userViewAnchor,"select*from university.users;");
    }
    @FXML
    public void viewUsers( ) throws IOException, SQLException {

        if(showUserCondition){
            AnchorPane pane =FXMLLoader.load(getClass().getResource("AdminUserFunction/showUsers.fxml"));
            userAnchorPane.getChildren().setAll(pane);
            AnchorPane.setRightAnchor(pane,0.0);
            AnchorPane.setLeftAnchor(pane,0.0);
            AnchorPane.setTopAnchor(pane,0.0);
            AnchorPane.setBottomAnchor(pane,0.0);
            userTable(pane,"select*from university.users;");
        }
        showUserCondition=false;
    }
  //Action to view Books
public void viewBooksButton( ) throws SQLException, IOException {
        if(showBookCondition){
            AnchorPane pane=FXMLLoader.load(getClass().getResource("AdminBookFunctionsFxml/viewBooks.fxml"));
            bookPageVbox.getChildren().setAll(pane);
            AnchorPane.setRightAnchor(pane,0.0);
            AnchorPane.setLeftAnchor(pane,0.0);
            AnchorPane.setTopAnchor(pane,0.0);
            AnchorPane.setBottomAnchor(pane,0.0);
            bookTable(pane,"select*from university.librarybooks");
        }
        showBookCondition=false;
}
    public  void searchBook() throws SQLException, IOException {
        if(searchBook.getText().isEmpty()){
            getAlert("Error","Empty Column","Enter valid book Id");
        }
        else{
            String id=searchBook.getText();
            String query="select*from university.librarybooks where bookId="+id;
            bookTable(viewBookpane,query);
            searchBook.clear();
        }
    }
    public void allBooks() throws SQLException, IOException {
        bookTable(viewBookpane,"select*from university.librarybooks;");
    }
//Action to view Students
    public void viewStudents( ) throws SQLException, IOException {
        if(showStudentCondition){
            AnchorPane pane=FXMLLoader.load(getClass().getResource("StudentFunction/showStudent.fxml"));
            studentAdminAnchor.getChildren().setAll(pane);
            AnchorPane.setRightAnchor(pane,0.0);
            AnchorPane.setLeftAnchor(pane,0.0);
            AnchorPane.setTopAnchor(pane,0.0);
            AnchorPane.setBottomAnchor(pane,0.0);
            studentTable(pane,"select*from university.students;");
        }
        showStudentCondition=false;
    }
    //to search the students
    public void searchStudent() throws SQLException, IOException {
        if(searchStudent.getText().isEmpty()){
            getAlert("Error","Empty Column","Enter valid Student Id");
        }
        else{
            String id=searchStudent.getText();
            String query="select*from university.students where studentId="+id;
            studentTable(viewStudent,query);
            searchStudent.clear();
        }
    }
    public void allStudents() throws SQLException, IOException {
        studentTable(viewStudent,"select*from university.students;");
    }


    //---------------------------------------------->Admin Actions on User Finished<----------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
    //------------------------------------------------->Functions<-------------------------------------------------.
    //this connection function provide connection to Mysql Database. Connection error are handled during implementation
    public Connection serverConnection() throws SQLException, IOException {
//        File file=new File("login.txt");
//        Scanner sc=new Scanner(new File("..\\..\\logins.txt"));
////        BufferedReader reader=new BufferedReader(new FileReader("logins.txt"));
//        String[]loginArray=sc.nextLine().split(" ");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/university", loginArray[0], loginArray[1]);
    }
    //this function is used to five warning alerts
    public void getAlert(String title,String header,String content){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
    //This function is used to format the date which is acceptable in Mysql if Date picker providing date like 01/12/2001
    //it will successfully convert it into 2001-12-01
    public static String dateFormatter(String dateData) throws ParseException {
        Date date;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyy/MM/dd");
        date = formatter.parse(dateData);
        String strDate = formatter2.format(date);
        return strDate.replace('/', '-');
    }
    //these 2 function are used to change the scene conveniently when certain condition meet like have same  root containers

    public void changeSceneAnchor(AnchorPane cureentPane, String fxmlFile) throws IOException {
        AnchorPane root=FXMLLoader.load(this.getClass().getResource(fxmlFile));
        cureentPane.getScene().setRoot(root);
    }
    public void getChildrenAnchors(AnchorPane currentPane,String fxmlfile) throws IOException {
        AnchorPane pane=FXMLLoader.load(getClass().getResource(fxmlfile));
        currentPane.getChildren().setAll(pane);
        AnchorPane.setRightAnchor(pane,0.0);
        AnchorPane.setLeftAnchor(pane,0.0);
        AnchorPane.setTopAnchor(pane,0.0);
        AnchorPane.setBottomAnchor(pane,0.0);
    }
    //this function is use to get preset date
    public static LocalDate presentDate(){
        return LocalDate.now();
    }
    //this function is used to find the difference between two dates
    public static long dateDifference(String issueDate,String submitDate){
        long daysBetween=0;
        try{
            SimpleDateFormat myFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date dateBefore=myFormat.parse(issueDate);
            Date dateAfter=myFormat.parse(submitDate);
            long difference=dateAfter.getTime()-dateBefore.getTime();
            daysBetween=(difference/(1000*60*60*24));

        }catch(Exception e){
            System.out.println(e);
        }
        return daysBetween;
    }
    // this function is use to create transaction list
    public ObservableList<TransactionDone> transactions() throws SQLException, IOException {
        ObservableList<TransactionDone> list =FXCollections.observableArrayList();
        String query="select students.studentId,students.studentFirstName,tDate,Fine,Paid from university.transactions inner join university.students on university.transactions.studentId=university.students.studentId;";
        Statement statement=serverConnection().createStatement();
        ResultSet resultSet=statement.executeQuery(query);
        while(resultSet.next()){
            list.add(new TransactionDone(resultSet.getString("studentId"),resultSet.getString("studentFirstName"),resultSet.getString("tDate"),resultSet.getString("Fine"),resultSet.getString("Paid")));

        }
        return list;
    }
    // this is to create table of transactions
    public void transactionTable(AnchorPane parent) throws SQLException, IOException {
//        this.id="";
//        this.name="";
//        this.date="";
//        this.fine="";
//        this.paid="";
        // 1st
        TableColumn<Defaulter,String> id=new TableColumn<>("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setMinWidth(100);
        //2nd
        TableColumn<Defaulter,String>name=new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setMinWidth(150);
        //3rd
        TableColumn<Defaulter,String> date=new TableColumn<>("Transaction Date");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        date.setMinWidth(300);
        //4th
        TableColumn<Defaulter,String> fine=new TableColumn<>("Fine");
        fine.setCellValueFactory(new PropertyValueFactory<>("fine"));
        fine.setMinWidth(200);
        //5th
        TableColumn<Defaulter,String> paid=new TableColumn<>("Status");
        paid.setCellValueFactory(new PropertyValueFactory<>("paid"));
        paid.setMinWidth(50);
        TableView table=new TableView();
        table.setItems(transactions());
        table.getColumns().addAll(id,name,date,fine,paid);

        AnchorPane.setBottomAnchor(table,0.0);
        parent.getChildren().addAll(table);
        AnchorPane.setRightAnchor(table,0.0);
        AnchorPane.setLeftAnchor(table,0.0);
        AnchorPane.setTopAnchor(table,49.0);
        AnchorPane.setBottomAnchor(table,0.0);


    }
    //this function is used to create the defaulter list
    public ObservableList<Defaulter> defaulters() throws SQLException, IOException {
        ObservableList<Defaulter> list =FXCollections.observableArrayList();
        String query="select students.studentId,cost,studentFirstName,studentLastName,Sex,dateOfBirth,Department,course,contactNumber,emailId from university.fined inner join university.students on university.fined.studentId=university.students.studentId;";

//        try (Statement statement = serverConnection().createStatement()) {
//            resultSet = statement.executeQuery(query);
//        }
        if(serverConnection()!=null){
            Statement statement =serverConnection().createStatement();
           ResultSet resultSet=statement.executeQuery(query);
            while(resultSet.next()){
                list.add(new Defaulter(resultSet.getString("studentId"),resultSet.getString("cost"),resultSet.getString("studentFirstName"),resultSet.getString("studentLastName"),resultSet.getString("Sex"),resultSet.getString("dateOfBirth"),resultSet.getString("Department"),resultSet.getString("course"),resultSet.getString("contactNumber"),resultSet.getString("emailId")));
            }
        }

        return list;
    }
    //this is to create table of defaulters
    public void defaulterTable(AnchorPane parentPane) throws SQLException, IOException {
      // 1st
        TableColumn<Defaulter,String> id=new TableColumn<>("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setMinWidth(50);
        //2nd
        TableColumn<Defaulter,String>cost=new TableColumn<>("Fine");
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        cost.setMinWidth(50);
        //3rd
        TableColumn<Defaulter,String> first=new TableColumn<>("First Name");
        first.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        first.setMinWidth(300);
        //4th
        TableColumn<Defaulter,String> last=new TableColumn<>("Last Name");
        last.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        last.setMinWidth(200);
        //5th
        TableColumn<Defaulter,String> sex=new TableColumn<>("Sex");
        sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        sex.setMinWidth(50);
        // 6th
        TableColumn<Defaulter,String> dob=new TableColumn<>("DateOfBirth");
        dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        dob.setMinWidth(70);
      //7th
        TableColumn<Defaulter,String> department=new TableColumn<>("Department");
        department.setCellValueFactory(new PropertyValueFactory<>("department"));
        department.setMinWidth(70);
        //8th
        TableColumn<Defaulter,String> course=new TableColumn<>("Course");
        course.setCellValueFactory(new PropertyValueFactory<>("course"));
        course.setMinWidth(150);
        //9th
        TableColumn<Defaulter,String> contact=new TableColumn<>("Contact");
        contact.setCellValueFactory(new PropertyValueFactory<>("number"));
        contact.setMinWidth(100);
       //10
        TableColumn<Defaulter,String> email=new TableColumn<>("Email");
        email.setCellValueFactory(new PropertyValueFactory<>("emailId"));
        email.setMinWidth(150);

        TableView table=new TableView();
        table.setItems(defaulters());
        table.getColumns().addAll(id,cost,first,last,sex,dob,department,course,contact,email);
        AnchorPane pane=FXMLLoader.load(getClass().getResource("librarianFunctions/ViewDefaulter.fxml"));
        parentPane.getChildren().setAll(pane);
        AnchorPane.setRightAnchor(pane,0.0);
        AnchorPane.setLeftAnchor(pane,0.0);
        AnchorPane.setTopAnchor(pane,0.0);
        AnchorPane.setBottomAnchor(pane,0.0);//adding new scene to current scene for  viewing table
        pane.getChildren().setAll(table); //adding table to current scene
        AnchorPane.setRightAnchor(table,0.0);
        AnchorPane.setLeftAnchor(table,0.0);
        AnchorPane.setTopAnchor(table,0.0);
        AnchorPane.setBottomAnchor(table,0.0);

    }
    //taking out all student from database using observableList
    public ObservableList<StudentValues>studentValues(String query) throws SQLException, IOException {
        ObservableList<StudentValues> list=FXCollections.observableArrayList();
        Statement statement=serverConnection().createStatement();
        ResultSet resultSet=statement.executeQuery(query);
        while(resultSet.next()){
            list.add(new StudentValues(resultSet.getString("studentId"),resultSet.getString("studentFirstName"),resultSet.getString("studentLastName"),resultSet.getString("Sex"),resultSet.getString("dateOfBirth"),resultSet.getString("Department"),resultSet.getString("course"),resultSet.getString("contactNumber"),resultSet.getString("emailId")));
        }
        return list;
    }


    //create table to show the values of students
    public void studentTable(AnchorPane parent,String query) throws SQLException, IOException {

        //1st
        TableColumn<BookValues,String> id=new TableColumn<>("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setMinWidth(50);
        //2nd
        TableColumn<GetValues,String> first=new TableColumn<>("First Name");
        first.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        first.setMinWidth(300);
        //3rd
        TableColumn<GetValues,String> last=new TableColumn<>("Last Name");
        last.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        last.setMinWidth(200);
        //4th
        TableColumn<GetValues,String> sex=new TableColumn<>("Sex");
        sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        sex.setMinWidth(50);
        //5th
        TableColumn<GetValues,String> dob=new TableColumn<>("DateOfBirth");
        dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        dob.setMinWidth(70);
        // 6th
        TableColumn<GetValues,String> department=new TableColumn<>("Department");
        department.setCellValueFactory(new PropertyValueFactory<>("department"));
        department.setMinWidth(70);
        //7th
        TableColumn<GetValues,String> course=new TableColumn<>("Course");
        course.setCellValueFactory(new PropertyValueFactory<>("course"));
        course.setMinWidth(150);
        //8th
        TableColumn<GetValues,String> contact=new TableColumn<>("Contact");
        contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        contact.setMinWidth(100);
        //9th
        TableColumn<GetValues,String> email=new TableColumn<>("Email");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        email.setMinWidth(150);

        TableView table=new TableView();
        table.setItems(studentValues(query));
        table.getColumns().addAll(id,first,last,sex,dob,department,course,contact,email);
//        AnchorPane pane=FXMLLoader.load(getClass().getResource("../../StudentFunction/showStudent.fxml"));
//        studentAdminAnchor.getChildren().setAll(pane);
//        AnchorPane.setRightAnchor(pane,0.0);
//        AnchorPane.setLeftAnchor(pane,0.0);
//        AnchorPane.setTopAnchor(pane,0.0);
//        AnchorPane.setBottomAnchor(pane,0.0);//adding new scene to current scene for  viewing table
        parent.getChildren().addAll(table); //adding table to current scene
        AnchorPane.setRightAnchor(table,0.0);
        AnchorPane.setLeftAnchor(table,0.0);
        AnchorPane.setTopAnchor(table,49.0);
        AnchorPane.setBottomAnchor(table,0.0);



    }

    public void bookTable(AnchorPane parent,String query) throws SQLException, IOException {
        //1st
        TableColumn<BookValues,String> id=new TableColumn<>("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setMinWidth(50);
        //2nd
        TableColumn<GetValues,String> title=new TableColumn<>("Book Title");
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        title.setMinWidth(300);
        //3rd
        TableColumn<GetValues,String> authName=new TableColumn<>("Author Name");
        authName.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        authName.setMinWidth(200);
        //4th
        TableColumn<GetValues,String> publishDate=new TableColumn<>("Publish Date");
        publishDate.setCellValueFactory(new PropertyValueFactory<>("publishDate"));
        publishDate.setMinWidth(50);
        //5th
        TableColumn<GetValues,String> availability=new TableColumn<>("Availability");
        availability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        availability.setMinWidth(70);
        // 6th

        TableView table=new TableView();
        table.setItems(bookValues(query));
        table.getColumns().addAll(id,title,authName,publishDate,availability);
//        AnchorPane pane=FXMLLoader.load(getClass().getResource("../../AdminBookFunctionsFxml/viewBooks.fxml"));

//        parent.getChildren().setAll(pane);
//        AnchorPane.setRightAnchor(pane,0.0);
//        AnchorPane.setLeftAnchor(pane,0.0);
//        AnchorPane.setTopAnchor(pane,0.0);
//        AnchorPane.setBottomAnchor(pane,0.0);//adding new scene to current scene for  viewing table
        parent.getChildren().addAll(table); //adding table to current scene
        AnchorPane.setRightAnchor(table,0.0);
        AnchorPane.setLeftAnchor(table,0.0);
        AnchorPane.setTopAnchor(table,49.0);
        AnchorPane.setBottomAnchor(table,0.0);
    }
    //This function is used to get all books from the database
    public ObservableList<BookValues> bookValues(String query) throws SQLException, IOException {
        ObservableList<BookValues> list=FXCollections.observableArrayList();
        Statement statement=serverConnection().createStatement();
        ResultSet resultSet=statement.executeQuery(query);
        while(resultSet.next()){
            list.add(new BookValues(resultSet.getString("bookId"),resultSet.getString("bookName"),resultSet.getString("authorName"),resultSet.getString("publicationYear"),resultSet.getString("availability")));
        }
        return list;
    }
    //This function is used to create columns to add them into tables(So Admin can view the users).
    public void userTable(AnchorPane parent,String query) throws SQLException, IOException {
        //1st
        TableColumn<GetValues,String> id=new TableColumn<>("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setMinWidth(50);
        //2nd
        TableColumn<GetValues,String> firstName=new TableColumn<>("First Name");
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstName.setMinWidth(100);
        //3rd
        TableColumn<GetValues,String> lastName=new TableColumn<>("Last Name");
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastName.setMinWidth(100);
        //4th
        TableColumn<GetValues,String> email=new TableColumn<>("Email");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        email.setMinWidth(300);
        //5th
        TableColumn<GetValues,String> phone=new TableColumn<>("Phone Number");
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phone.setMinWidth(70);
        // 6th
        TableColumn<GetValues,String> address=new TableColumn<>("Address");
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        address.setMinWidth(300);
        //7th
        TableColumn<GetValues,String> birth=new TableColumn<>("DOB");
        birth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        birth.setMinWidth(100);
        //8th
        TableColumn<GetValues,String> password=new TableColumn<>("Passwords");
        password.setCellValueFactory(new PropertyValueFactory<>("passwords"));
        password.setMinWidth(100);
        //Inserting values into table
        TableView table=new TableView();
        table.setItems(userValues(query));
        table.getColumns().addAll(id,firstName,lastName,email,phone,address,birth,password);
//        AnchorPane pane=FXMLLoader.load(getClass().getResource("../../AdminUserFunction/showUsers.fxml"));
//        userAnchorPane.getChildren().setAll(pane);
//        AnchorPane.setRightAnchor(pane,0.0);
//        AnchorPane.setLeftAnchor(pane,0.0);
//        AnchorPane.setTopAnchor(pane,0.0);
//        AnchorPane.setBottomAnchor(pane,0.0);//adding new scene to current scene for  viewing table

        parent.getChildren().addAll(table); //adding table to current scene
        AnchorPane.setRightAnchor(table,0.0);
        AnchorPane.setLeftAnchor(table,0.0);
        AnchorPane.setTopAnchor(table,49.0);
        AnchorPane.setBottomAnchor(table,0.0);
    }
    //Taking out all users from database using observableList
    public ObservableList<GetValues> userValues(String query) throws IOException, SQLException {
        ObservableList<GetValues> list= FXCollections.observableArrayList();

        Statement statement =serverConnection().createStatement();
        ResultSet resultSet=statement.executeQuery(query);
        while(resultSet.next()){
            list.add(new GetValues(resultSet.getString("UserId"),resultSet.getString("UserName"),resultSet.getString("UserLastName"),resultSet.getString("UserEmail"),resultSet.getString("UserPhone"),resultSet.getString("UserAddress"),resultSet.getString("DateOfBirth"),resultSet.getString("Passwords")));
        }
        return  list;
    }
}
