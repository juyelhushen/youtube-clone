package com.youtubeclone.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {

    private String Id;
    private String sub;
    @JsonProperty("given_name")
    private String givenName;
    @JsonProperty("nickname")
    private String nickName;
    private String name;
    private String picture;
//    private String locale;
    private String email;
}
