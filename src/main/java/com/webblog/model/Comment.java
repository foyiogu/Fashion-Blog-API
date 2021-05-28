package com.webblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment extends TimerController implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "comment",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NotBlank
    @Size(min = 3)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "personId", referencedColumnName = "id")
    private Person person;

    @ManyToOne

    @JoinColumn(name = "postId", referencedColumnName = "id")
    private Post post;
}
