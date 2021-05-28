package com.webblog.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FavouriteMapper {
    private Long postId;
    private String postTitle;
    private String postBody;
}
