/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.florenceconsulting.userpoc.service;

import it.florenceconsulting.userpoc.dto.UserDto;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author daniele
 */
public interface FileService {


    public List<UserDto> loadFromCsv(MultipartFile file) throws UnsupportedEncodingException, IOException;
    
}
