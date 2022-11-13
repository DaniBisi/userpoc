/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.florenceconsulting.userpoc.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author daniele
 */
@Data
@Builder
@ToString
public class UserDto {

    private Long id;
    private String userName;
    private String email;
    private String lastName;
    private String firstName;
    private String cellPhone;

    

}
