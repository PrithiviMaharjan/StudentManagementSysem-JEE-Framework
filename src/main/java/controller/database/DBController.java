package controller.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.LoginModel;
import model.PasswordEncryptionWithAes;
import model.StudentModel;
import utils.StringUtils;

public class DBController {

	/**
	 * Establishes a connection to the database using pre-defined credentials and
	 * driver information.
	 * 
	 * @return A `Connection` object representing the established connection to the
	 *         database.
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public Connection getConnection() throws SQLException, ClassNotFoundException {

		// Load the JDBC driver class specified by the StringUtils.DRIVER_NAME constant
		Class.forName(StringUtils.DRIVER_NAME);

		// Create a connection to the database using the provided credentials
		return DriverManager.getConnection(StringUtils.LOCALHOST_URL, StringUtils.LOCALHOST_USERNAME,
				StringUtils.LOCALHOST_PASSWORD);
	}

	/**
	 * This method attempts to register a new student in the database.
	 * 
	 * @param student A `StudentModel` object containing the student's information.
	 * @return An integer value indicating the registration status: - 1:
	 *         Registration successful - 0: Registration failed (no rows affected) -
	 *         -1: Internal error (e.g., ClassNotFound or SQLException)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int registerStudent(StudentModel student) {

		try {
			// Prepare a statement using the predefined query for student registration
			PreparedStatement stmt = getConnection().prepareStatement(StringUtils.QUERY_REGISTER_STUDENT);

			// Set the student information in the prepared statement
			stmt.setString(1, student.getFirstName());
			stmt.setString(2, student.getLastName());
			stmt.setDate(3, Date.valueOf(student.getDob()));
			stmt.setString(4, student.getGender());
			stmt.setString(5, student.getEmail());
			stmt.setString(6, student.getPhoneNumber());
			stmt.setString(7, student.getSubject());
			stmt.setString(8, student.getUsername());
			stmt.setString(9, PasswordEncryptionWithAes.encrypt(student.getUsername(), student.getPassword()));
			stmt.setString(10, student.getImageUrlFromPart());

			// Execute the update statement and store the number of affected rows
			int result = stmt.executeUpdate();

			// Check if the update was successful (i.e., at least one row affected)
			if (result > 0) {
				return 1; // Registration successful
			} else {
				return 0; // Registration failed (no rows affected)
			}

		} catch (ClassNotFoundException | SQLException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			return -1; // Internal error
		}
	}

	/**
	 * This method attempts to validate a student login by checking the username and
	 * password against a database.
	 * 
	 * @param username The username provided by the user attempting to log in.
	 * @param password The password provided by the user attempting to log in.
	 * @return An integer value indicating the login status: - 1: Login successful -
	 *         0: Username or password mismatch - -1: Username not found in the
	 *         database - -2: Internal error (e.g., SQL or ClassNotFound exceptions)
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public int getStudentLoginInfo(LoginModel loginModel) {
		// Try-catch block to handle potential SQL or ClassNotFound exceptions
		try {
			// Prepare a statement using the predefined query for login check
			PreparedStatement st = getConnection().prepareStatement(StringUtils.QUERY_LOGIN_USER_CHECK);

			// Set the username in the first parameter of the prepared statement
			st.setString(1, loginModel.getUsername());

			// Execute the query and store the result set
			ResultSet result = st.executeQuery();

			// Check if there's a record returned from the query
			if (result.next()) {
				// Get the username from the database
				String userDb = result.getString(StringUtils.USER_NAME);

				// Get the password from the database
				String encryptedPwd = result.getString(StringUtils.PASSWORD);

				String decryptedPwd = PasswordEncryptionWithAes.decrypt(encryptedPwd, userDb);
				// Check if the username and password match the credentials from the database
				if (userDb.equals(loginModel.getUsername()) && decryptedPwd.equals(loginModel.getPassword())) {
					// Login successful, return 1
					return 1;
				} else {
					// Username or password mismatch, return 0
					return 0;
				}
			} else {
				// Username not found in the database, return -1
				return -1;
			}

			// Catch SQLException and ClassNotFoundException if they occur
		} catch (SQLException | ClassNotFoundException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			// Return -2 to indicate an internal error
			return -2;
		}
	}

	public Boolean checkEmailIfExists(String email) {
		// TODO: Implement logic to check if the provided email address exists in the
		// database
		// This method should likely query the database using DBController and return
		// true if the email exists, false otherwise.
		return false;
	}

	public Boolean checkNumberIfExists(String number) {
		// TODO: Implement logic to check if the provided phone number exists in the
		// database
		// This method should likely query the database using DBController and return
		// true if the phone number exists, false otherwise.
		return false;
	}

	public Boolean checkUsernameIfExists(String username) {
		// TODO: Implement logic to check if the provided username exists in the
		// database
		// This method should likely query the database using DBController and return
		// true if the username exists, false otherwise.
		return false;
	}

	public ArrayList<StudentModel> getAllStudentsInfo() {
		try {
			PreparedStatement stmt = getConnection().prepareStatement(StringUtils.QUERY_GET_ALL_STUDENTS);
			ResultSet result = stmt.executeQuery();

			ArrayList<StudentModel> students = new ArrayList<StudentModel>();

			while (result.next()) {
				StudentModel student = new StudentModel();
				student.setFirstName(result.getString("first_name"));
				student.setLastName(result.getString("last_name"));
				student.setDob(result.getDate("birthday").toLocalDate());
				student.setEmail(result.getString("email"));
				student.setGender(result.getString("gender"));
				student.setPhoneNumber(result.getString("number"));
				student.setSubject(result.getString("subject"));
				student.setImageUrlFromDB(result.getString("image"));
				student.setUsername(result.getString("user_name"));				
				students.add(student);
			}
			return students;
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public int deleteStudentInfo(String username) {
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(StringUtils.QUERY_DELETE_USER);
			st.setString(1, username);
			return st.executeUpdate();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace(); // Log the exception for debugging
			return -1;
		}
	}
}