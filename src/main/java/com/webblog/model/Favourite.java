package com.webblog.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Favourite implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false)
    private Long currentUserId;

    @Column(nullable = false)
    private Long postId;
}
