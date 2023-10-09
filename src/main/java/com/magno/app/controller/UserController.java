package com.magno.app.controller;

import com.magno.app.DTO.UserDTO;
import com.magno.app.DTO.UserRecordDTO;
import com.magno.app.controller.dto.UserRequestDTO;
import com.magno.app.controller.dto.UserResponseDTO;
import com.magno.app.entity.User;
import com.magno.app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/app/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    public UserService userService;

    @PostMapping("/save")
    public ResponseEntity<UserResponseDTO> newUser(@RequestBody UserRequestDTO userRequestDTO){
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(userRequestDTO, UserDTO.class);
        var saveUser = userService.newUser(userDTO);
        return new ResponseEntity<>(modelMapper.map(userDTO, UserResponseDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Optional<UserResponseDTO>> findUserById(@PathVariable(value = "id") Long id){
        Optional<UserRecordDTO> userRecordDTOOptional = userService.findUserById(id);
        if (userRecordDTOOptional.isPresent()){
            UserResponseDTO userResponseDTO = new ModelMapper()
                    .map(userRecordDTOOptional.get(), UserResponseDTO.class);
            return new ResponseEntity<>(Optional.of(userResponseDTO), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
