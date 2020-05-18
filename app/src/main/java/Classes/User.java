package Classes;

public class User extends Object {
    //  private int userId;
    private String userName;
    private String surname;
    private String name;
    private String country;
    private String password;
    private String email;
    private String maxBudget;
    private String minBudget;
    private String dailyBudget;
    private String currentBudget;
    private String impressions;


    public User(String userName, String surname, String name, String country, String password, String email, String maxBudget, String minBudget, String dailyBudget, String currentBudget) {
        this.userName = userName;
        this.surname = surname;
        this.name = name;
        this.country = country;
        this.password = password;
        this.email = email;
        this.maxBudget = maxBudget;
        this.minBudget = minBudget;
        this.dailyBudget = dailyBudget;
        this.currentBudget = currentBudget;
    }
  public User(String maxBudget, String minBudget, String dailyBudget, String currentBudget){
        this.maxBudget = maxBudget;
        this.minBudget = minBudget;
        this.dailyBudget = dailyBudget;
        this.currentBudget = currentBudget;
  }
    public User(String surname, String name, String userName, String country, String password, String email) {
        this.surname = surname;
        this.name = name;
        this.userName = userName;
        this.country = country;
        this.password = password;
        this.email = email;
    }

    public User() {

    }

    public String getImpressions() {
        return impressions;
    }

    public void setImpressions(String impressions) {
        this.impressions = impressions;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    //  public int getUserId() {
    //    return userId;
    //}

    //public void setUserId(int userId) {
    //   this.userId = userId;
    //}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(String maxBudget) {
        this.maxBudget = maxBudget;
    }

    public String getMinBudget() {
        return minBudget;
    }

    public void setMinBudget(String minBudget) {
        this.minBudget = minBudget;
    }

    public String getDailyBudget() {
        return dailyBudget;
    }

    public void setDailyBudget(String dailyBudget) {
        this.dailyBudget = dailyBudget;
    }

    public String getCurrentBudget() {
        return currentBudget;
    }

    public void setCurrentBudget(String currentBudget) {
        this.currentBudget = currentBudget;
    }

}
