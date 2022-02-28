package com.openclassrooms.paymybuddy.model;


import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private long id;

  @Column(name = "first_name")
  @NotNull
  @Size(max = 50)
  private String firstName;

  @Column(name = "last_name")
  @Size(max = 50)
  private String lastName;

  @Column(name = "email")
  @NotNull
  @Size(max = 50)
  private String email;

  @Column(name = "password")
  @NotNull
  @Size(max = 60)
  private String password;

  @Column(name = "enabled")
  @NotNull
  private boolean enabled;

  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  private List<UserAuthority> userAuthorities = new ArrayList<>();

  @OneToOne(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  private Account account;

  public User() {

  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Account getAccount() {
    return account;
  }

  public List<UserAuthority> getUserAuthorities() {
    return userAuthorities;
  }

  //According to specifications, two users can't have the same mail, as it is their ids.
  // As a consequence equality is test on emails.
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return getEmail().equals(user.getEmail());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getFirstName(), getLastName(), getEmail(), getPassword(), isEnabled());
  }

  //We do not display password nor enabled attribute in toString() method.
  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      ", email='" + email + '\'' +
      '}';
  }

  public void addAccount(Account account) {
    this.account = account;
    account.setUser(this);
  }

  public void removeAccount(Account account) {
    this.account = null;
    account.setUser(null);
  }

  public void addUserAuthority(UserAuthority userAuthority) {
    userAuthorities.add(userAuthority);
    userAuthority.setUser(this);
  }

  public void removeUserAuthority(UserAuthority userAuthority) {
    userAuthorities.remove(userAuthority);
    userAuthority.setUser(null);
  }
}
