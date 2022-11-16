/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.florenceconsulting.userpoc.dto;

import it.florenceconsulting.userpoc.annotations.Email;
import it.florenceconsulting.userpoc.annotations.NoSpecialCharacter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author daniele
 */
@Data
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    @NoSpecialCharacter
    private String username;
    @Email
    private String email;
    @NoSpecialCharacter
    private String lastname;
    @NoSpecialCharacter
    private String firstname;
    @NoSpecialCharacter
    private String cellphone;

}
