package com.webblog.POJO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentMapper {
    private Long postId;
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private String username;
    @JsonIgnore
    private String name;
    private String comment;
    @JsonIgnore
    private String title;
    @JsonIgnore
    private String image;
    private Long userId;
}

