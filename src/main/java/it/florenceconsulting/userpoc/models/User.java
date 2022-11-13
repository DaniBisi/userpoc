/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.florenceconsulting.userpoc.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author daniele
 */
@Entity
@Table(name = "user")
@Data
@Builder
public class User{

    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private String email;
    private String lastName;
    private String firstName;
    private String cellPhone;

}
