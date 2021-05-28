package com.webblog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
public class Post extends TimerController implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   @Size(min = 3, max = 50)
   private String title;

   @NotBlank
   @Size(max = 1000)
   @Column(nullable = false, columnDefinition = "TEXT")
   private String body;

   @JsonIgnore
   @Column(columnDefinition = "MEDIUMBLOB")
   private String image;

   @JsonIgnore
   @Column(nullable = false)
   private String status;

   @JsonIgnore
   @ManyToOne
   @JoinColumn(name = "personId", referencedColumnName = "id")
   private Person person;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getBody() {
      return body;
   }

   public void setBody(String body) {
      this.body = body;
   }

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public Person getPerson() {
      return person;
   }

   public void setPerson(Person person) {
      this.person = person;
   }
}
