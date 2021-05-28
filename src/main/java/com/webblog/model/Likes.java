package com.webblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Likes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "personId", referencedColumnName = "id")
    private Person person;

    @OneToOne
    @JoinColumn(name = "postId", referencedColumnName = "id")
    private Post post;
}