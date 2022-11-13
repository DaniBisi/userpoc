/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.florenceconsulting.userpoc.annotations;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author daniele
 */
public class NoSpecialCharacterValidator implements ConstraintValidator<NoSpecialCharacter, String> {

    @Override
    public void initialize(NoSpecialCharacter constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern regex = Pattern.compile("[^A-Za-z0-9]");
        return regex.matcher(value).find();

    }
}
