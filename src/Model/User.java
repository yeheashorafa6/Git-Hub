package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {

    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String phone;
    private String gender; //enum("female","male")
    private String role; //enum("admin","patient")

    public User(String username, String password, String firstName, String lastName,
            int age, String email, String phone, String gender, String role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.role = role;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //crate , updata , delete , red
    //create a new user in users table
    public int save() throws SQLException, ClassNotFoundException {
        Connection c = DbConnection.getInstance().getConnection();
        PreparedStatement ps = null;
        int recordCounter = 0;
        String sqlQuery = "INSERT INTO users (id, username, password, firstName, lastName"
                + ", age, email, phone, gender, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = c.prepareStatement(sqlQuery);

        ps.setInt(1, this.getId());
        ps.setString(2, this.getUsername());
        ps.setString(3, this.getPassword());
        ps.setString(4, this.getFirstName());
        ps.setString(5, this.getLastName());
        ps.setInt(6, this.getAge());
        ps.setString(7, this.getEmail());
        ps.setString(8, this.getPhone());
        ps.setString(9, this.getGender());
        ps.setString(10, this.getRole());
        
        recordCounter = ps.executeUpdate();
        if (recordCounter > 0) {
            System.out.println(this.getUsername()
                    + " User was added successfully!");
        }
        if (ps != null) {
            ps.close();
        }
        c.close();
        return recordCounter;
    }

    //get all users from users table
    public static ArrayList<User> getAllUsers() throws SQLException, ClassNotFoundException {
        Connection c = DbConnection.getInstance().getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<User> users = new ArrayList<>();

        String sqlQuery = "SELECT * FROM USERS";
        ps = c.prepareStatement(sqlQuery);
        rs = ps.executeQuery();
        while (rs.next()) {
            User user = new User(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6),
                    rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
            user.setId(rs.getInt(1));
            users.add(user);
        }
        if (ps != null) {
            ps.close();
        }
        c.close();
        return users;
    }

    //update an existing user in users table 
    public int update() throws SQLException, ClassNotFoundException {
        Connection c = DbConnection.getInstance().getConnection();
        PreparedStatement ps = null;
        int recordCounter = 0;
        String sqlQuery = "UPDATE users SET username=?, password=?, firstName=?,"
                + " lastName=?, age=?, email=?, phone=?, gender=?, role=? WHERE id=?";
        ps = c.prepareStatement(sqlQuery);
        ps.setString(1, this.getUsername());
        ps.setString(2, this.getPassword());
        ps.setString(3, this.getFirstName());
        ps.setString(4, this.getLastName());
        ps.setInt(5, this.getAge());
        ps.setString(6, this.getEmail());
        ps.setString(7, this.getPhone());
        ps.setString(8, this.getGender());
        ps.setString(9, this.getRole());
        ps.setInt(10, this.getId());
        recordCounter = ps.executeUpdate();
        if (recordCounter > 0) {
            System.out.println("User with id : " + this.getId() + " was updated successfully!");
        }
        if (ps != null) {
            ps.close();
        }
        c.close();
        return recordCounter;
    }

    public static ArrayList<User> searchInPatientsByFirstName(String firstName) throws SQLException, ClassNotFoundException {
        Connection c = DbConnection.getInstance().getConnection();
        ArrayList<User> patients = new ArrayList<>();  // to add the specific patient we search in
        
        PreparedStatement ps = null;
        String sql = "SELECT * FROM users WHERE firstName = ?";
        ps = c.prepareStatement(sql);
        ps.setString(1, firstName);
        
        ResultSet rs = ps.executeQuery();
        while (rs.next()) { // if you found users(patients)
            if(rs.getString("role").equals("patient")){ // if this user is patient add it to the ArrayList
                User user = new User(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6),
                        rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
                user.setId(rs.getInt(1));
                patients.add(user);
            }
        }
        if (ps != null) {
            ps.close();
        }
        c.close();
        return patients;
    }
    
    //Delete: delete an user from users table 
    public int delete() throws SQLException, ClassNotFoundException{
        Connection c = DbConnection.getInstance().getConnection();
        PreparedStatement ps = null;
        int recordCounter =0;
        
        String sqlQuery = "DELETE FROM users WHERE ID=? ";
        ps = c.prepareStatement(sqlQuery);
        ps.setInt(1, this.getId());
        recordCounter = ps.executeUpdate();
        if (recordCounter > 0) {
            System.out.println("The user with id : "+this.getId()+" was deleted successfully!");
        }
        if (ps != null){
            ps.close();
        }
        c.close();
        return recordCounter;  
    }

}
