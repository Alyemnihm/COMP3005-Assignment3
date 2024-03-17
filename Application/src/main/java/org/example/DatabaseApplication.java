package org.example;

import java.sql.*;
import java.util.Objects;
import java.util.Scanner;


public class DatabaseApplication {

    private final String url = "jdbc:postgresql://localhost:5432/Assignment3";
    private final String user = "postgres";
    private final String password = "password1234";

    //Database operation methods

    /*
     * Function: getAllStudents
     * Retrieves and displays all records from the students table.
     *
     * @params: None
     * @return: None
     */
    public void getAllStudents() {
        String SQL = "SELECT * FROM Students";

        try(Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = con.prepareStatement(SQL)) {

            ResultSet results = pstmt.executeQuery();
            printResultSet(results);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*
     * Function: addStudent
     * Inserts a new student record into the students table.
     *
     * @params: String first_name
     *          String last_name
     *          String email
     *          String enrollment_date
     *
     * @return: None
     */
    public void addStudent(String first_name, String last_name, String email, String enrollment_date) {
        String SQL = "INSERT INTO Students(first_name, last_name, email, enrollment_date) VALUES(?,?,?,?)";

        try(Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = con.prepareStatement(SQL)) {

            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, email);
            pstmt.setDate(4, Date.valueOf(enrollment_date));
            pstmt.executeUpdate();
            System.out.println("Student added successfully!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*
     * Function: updateStudentEmail
     * Updates the email address for a student with the specified student_id.
     *
     * @params: int student_id
     *          String new_email
     *
     * @return None
     */
    public void updateStudentEmail(int student_id, String new_email) {
        String SQL = "UPDATE Students SET email=? WHERE student_id=?";

        try(Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = con.prepareStatement(SQL)) {

            pstmt.setString(1, new_email);
            pstmt.setInt(2, student_id);
            pstmt.executeUpdate();
            System.out.println("Student email updated!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*
     * Function: deleteStudent
     * Deletes the record of the student with the specified student_id.
     *
     * @params: int student_id
     * @return: None
     */
    public void deleteStudent(int student_id) {
        String SQL = "DELETE FROM Students WHERE student_id=?";

        try(Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = con.prepareStatement(SQL)) {

            pstmt.setInt(1, student_id);
            pstmt.executeUpdate();
            System.out.println("Student deleted successfully!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*
     * Function: printResultSet
     * Prints the results of a query
     *
     * @params: ResultSet results
     * @return: None
     */
    public void printResultSet(ResultSet results) throws SQLException {
        ResultSetMetaData metaData = results.getMetaData();
        int colNum = metaData.getColumnCount();

        System.out.println("-------------");
        System.out.println("Results:");
        System.out.println("-------------");
        System.out.print("\n");

        //Strings to format output
        String cellFormat = "|  %-23s  ";
        String cellFormat2 = "   %-23s  ";

        //print column names
        for(int i = 1; i <= colNum; i++) {
            System.out.format(cellFormat, metaData.getColumnName(i));
        }
        System.out.print("\n");

        //print data
        while(results.next()) {
            for(int i = 1; i <= colNum; i++) {
                String colVal = results.getString(i);
                System.out.format(cellFormat2, colVal);
            }
            System.out.print("\n");
        }

        System.out.print("\n");
        System.out.println("-------------");
    }

    public static void main(String[] args) {

        DatabaseApplication dbApp = new DatabaseApplication();
        Scanner scanner = new Scanner(System.in);

        boolean keepRunning = true;
        String userInput;
        while(keepRunning) {

            System.out.println("Which of the following operations would you like to preform?");
            System.out.println("\t1. Retrieve all students");
            System.out.println("\t2. Add a student");
            System.out.println("\t3. Update a student's email");
            System.out.println("\t4. Delete a student");
            System.out.print("\n");
            System.out.println("Enter the number of the operation you'd like to perform: ");
            System.out.print("> ");
            userInput = scanner.nextLine();
            System.out.print("\n");

            //retrieve all students
            if(Objects.equals(userInput, "1")) {
                dbApp.getAllStudents();

                System.out.print("\n");
            }

            //add a student
            else if(Objects.equals(userInput, "2")) {
                String firstName;
                String lastName;
                String email;
                String enrollmentDate;

                //Get student's first name
                System.out.print("Enter student's first name: > ");
                firstName = scanner.nextLine();

                System.out.print("\n");

                //Get student's last name
                System.out.print("Enter student's last name: > ");
                lastName = scanner.nextLine();

                System.out.print("\n");

                //Get student's email
                System.out.print("Enter student's email: > ");
                email = scanner.nextLine();

                System.out.print("\n");

                //Get student's enrollment date
                System.out.print("Enter student's enrollment date (yyyy-mm-dd): > ");
                enrollmentDate = scanner.nextLine();

                System.out.print("\n");

                dbApp.addStudent(firstName, lastName, email, enrollmentDate);

                System.out.print("\n");
            }

            //update a student's email
            else if(Objects.equals(userInput, "3")) {
                int id;
                String email;

                //Get student's id
                System.out.print("Enter student's ID: > ");
                id = Integer.parseInt(scanner.nextLine());

                System.out.print("\n");

                //Get student's email
                System.out.print("Enter student's new email: > ");
                email = scanner.nextLine();

                System.out.print("\n");

                dbApp.updateStudentEmail(id, email);

                System.out.print("\n");
            }

            //delete a student
            else if(Objects.equals(userInput, "4")) {
                int id;

                //Get student's id
                System.out.print("Enter student's ID: > ");
                id = Integer.parseInt(scanner.nextLine());

                System.out.print("\n");

                dbApp.deleteStudent(id);

                System.out.print("\n");
            }

            else {
                System.out.println("Input not recognized.");

                System.out.print("\n");
            }


            //Ask user if they'd like to perform another operation
            System.out.println("Would you like to perform another operation? (y/n)");
            userInput = scanner.nextLine();
            if(Objects.equals(userInput, "y")) {
                System.out.print("\n");
                continue;
            }
            else if(Objects.equals(userInput, "n")) {
                System.out.print("\n");
                System.out.println("Thank you for using this application, have a great day!");
                scanner.close();
                keepRunning = false;
            }
            else {
                System.out.print("\n");
                System.out.println("Unrecognized input. Terminating program.");
                scanner.close();
                keepRunning = false;
            }


        }
    }
}