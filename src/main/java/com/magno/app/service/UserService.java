package com.magno.app.service;

import com.magno.app.DTO.UserDTO;
import com.magno.app.DTO.UserRecordDTO;
import com.magno.app.entity.User;
import com.magno.app.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encryptPassword;
    public UserDTO newUser(UserDTO userDTO){
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(encryptPassword.encode(user.getPassword()));
        var newUser = userRepository.save(user);
        return userDTO;
    }
    public Optional<UserRecordDTO> findUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        UserRecordDTO userRecordDTO = new ModelMapper().map(user.get(), UserRecordDTO.class);
        return Optional.of(userRecordDTO);
    }
/*
    public String saveNewUser(User user, BindingResult result,
                              Model model, RedirectAttributes attributes){
        if (result.hasErrors()){
            return "/form-novo-usuario";
        }
        User validateEmail = user.findByEmail(user.getEmail());
        if (validateEmail != null){
            model.addAttribute("emailExists", "E-mail já existe");
            return "/form-novo-usuario";
        }
        userRepository.save(user);
        attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso");
        return "redirect:/usuario/novo";
    }

 */

}
