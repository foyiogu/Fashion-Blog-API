package com.webblog.POJO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostMapper {
    private Long id;
    private String title;
    private String body;
    private String imageName;
    private String name;
    private String username;
    private String email;
    private int noLikes;
    private int noComments;
    private boolean likedPost;
    private Long userId;
}
