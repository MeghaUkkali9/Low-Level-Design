public class Customer {
    private int customerId;
    private String email;
    private String phone;
    private String name;

    public Customer(int customerId, String email, String phone, String name){
        this.name = name;
        this.customerId = customerId;
        this.email = email;
        this.phone = phone;
    }


    public int getCustomerId() {
        return customerId;
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

    public String getName() {
        return name;
    }
}
