/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.florenceconsulting.userpoc.dto;

import it.florenceconsulting.userpoc.annotations.Email;
import it.florenceconsulting.userpoc.annotations.NoSpecialCharacter;
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
    @NoSpecialCharacter
    private String userName;
    @Email
    private String email;
    @NoSpecialCharacter
    private String lastName;
    @NoSpecialCharacter
    private String firstName;
    @NoSpecialCharacter
    private String cellPhone;

}
