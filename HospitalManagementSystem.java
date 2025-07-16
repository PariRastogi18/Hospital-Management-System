package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

import static java.sql.DriverManager.getConnection;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String userName = "root";
    private static final String password = "A7$kP9!x";

    public static void main(String[] args) {
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url,userName,password);
            Patients patients = new Patients(connection,scanner);
            Doctors doctors = new Doctors(connection);
            while (true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patients");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice  = scanner.nextInt();

                switch (choice){
                    case 1:
                        patients.addPatient();
                        break;
                    case 2:
                        patients.viewPatient();
                        break;
                    case 3:
                        doctors.viewDoctors();
                        break;
                    case 4:
                        bookAppointment(patients,doctors,connection,scanner);
                        break;
                    case 5:
                        return;

                    default:
                        System.out.println("Enter valid choice");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patients patients,Doctors doctors, Connection connection,Scanner scanner){
        System.out.print("Enter Patient Id: ");
        int patientId  = scanner.nextInt();
        System.out.print("Enter Doctor Id: ");
        int doctorId = scanner.nextInt();
        System.out.print("Enter appointment date(YYYY-MM-DD): ");
        String appointmentDate = scanner.next();
        if (patients.getPatientId(patientId) && doctors.getDoctorId(doctorId)){
            if (checkDoctorAvailable(doctorId,appointmentDate,connection)){
                 String appointmentQuery = "INSERT INTO appointments(patient_id,doctor_id,appointment_date) VALUES(?,?,?)";
               try {
                   PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                   preparedStatement.setInt(1,patientId);
                   preparedStatement.setInt(2,doctorId);
                   preparedStatement.setString(3,appointmentDate);
                   int affectedRows = preparedStatement.executeUpdate();
                   if (affectedRows>0){
                       System.out.println(" Appointment Done!!!");
                   }else {
                       System.out.println("Appointment Failed!!!");
                   }
               }catch (SQLException e){
                   e.printStackTrace();
               }
            }else {
                System.out.println("Doctor is not available on this date");
            }
        }else {
            System.out.println("Either doctor or patient doesn't exist!!!");

        }

    }
    public static boolean checkDoctorAvailable(int doctorId, String appointmentDate,Connection connection){
      String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
      try {
          PreparedStatement preparedStatement = connection.prepareStatement(query);
          preparedStatement.setInt(1,doctorId);
          preparedStatement.setString(2,appointmentDate);
          ResultSet resultSet = preparedStatement.executeQuery();
          if (resultSet.next()){
              int count = resultSet.getInt(1);
              if (count == 0){
                  return true;
              }else {
                  return false;
              }
          }
      }catch (SQLException e){
          e.printStackTrace();
      }
      return false;
    }



}
