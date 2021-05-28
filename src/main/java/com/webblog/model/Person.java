package com.webblog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Person extends TimerController implements Serializable{

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @JsonIgnore
    @Column(nullable = false, columnDefinition = "int default 0")
    private int IsDelete;

    @JsonIgnore
    @Column(name = "removeDate")
    private String removeDate;

    @JsonIgnore
    @Column(nullable = false, columnDefinition = "int default 0")
    private int personDeactivated;

    @JsonIgnore
    @Column(columnDefinition = "MEDIUMBLOB")
    private String profilePics;

    @JsonIgnore
    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Post> post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(int isDelete) {
        IsDelete = isDelete;
    }

    public String getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(String removeDate) {
        this.removeDate = removeDate;
    }

    public int getPersonDeactivated() {
        return personDeactivated;
    }

    public void setPersonDeactivated(int personDeactivated) {
        this.personDeactivated = personDeactivated;
    }

    public String getProfilePics() {
        return profilePics;
    }

    public void setProfilePics(String profilePics) {
        this.profilePics = profilePics;
    }

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }
}

