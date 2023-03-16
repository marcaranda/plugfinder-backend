package backend.plugfinder.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class UserModel {

    //region User Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long user_id;
    @Column (unique = true, nullable = false)
    private String username;
    @Column (nullable = false)
    private String real_name;
    @Column (unique = true)
    private String phone;
    @Column (unique = true, nullable = false)
    private String email;
    @Column (nullable = false)
    private String password;
    @Column (nullable = false)
    private String birth_date;
    @Column (nullable = false, columnDefinition = "boolean default false" )
    private boolean deleted;
    //endregion

    //region Getters y Setters

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    //endregion
}
