package com.webblog.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonMapper {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String profilePics;
}
