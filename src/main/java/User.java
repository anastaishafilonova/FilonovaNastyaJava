public class User {
    public String firstName;
    public String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean equals(User user) {
        boolean flag = true;
        if (!(this.firstName.equals(user.firstName) && this.lastName.equals(user.lastName))) {
            flag = false;
        }
        return flag;
    }
}

