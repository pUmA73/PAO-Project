package models;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int rating;

    public User() {}

    public User(String firstName, String lastName, String email, String password, int rating) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.rating = rating;
    }

    // Setters
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // Getters
    public int getUserId() {return userId;}
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getRating() { return rating; }

    @Override
    public String toString() {
        return "User {" +
                "First Name: " + getFirstName() + "\n" +
                "Last Name: " + getLastName() + "\n" +
                "Email: " + getEmail() + "\n" +
                "Password: " + getPassword() + "\n" +
                "Rating: " + getRating() + "\n" +
                "}";
    }
}
