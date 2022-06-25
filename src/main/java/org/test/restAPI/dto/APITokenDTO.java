package org.test.restAPI.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class APITokenDTO {

    private String mid;

    private String access;

    private String refresh;

}
