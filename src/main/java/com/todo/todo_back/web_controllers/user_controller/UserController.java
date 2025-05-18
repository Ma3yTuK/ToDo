package com.todo.todo_back.web_controllers.user_controller;

import com.todo.todo_back.entities.User;
import com.todo.todo_back.repositories.ImageRepository;
import com.todo.todo_back.repositories.UserRepository;
import com.todo.todo_back.specifications.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("users")
public class UserController {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @GetMapping("/sorting_variants")
    public Collection<String> getSortingVariants() {
        return List.of(User.Fields.NAME.getDatabaseFieldName(), User.Fields.IS_VERIFIED.getDatabaseFieldName());
    }

    @GetMapping("/all")
    public Page<UserShortDTO> getAllUsers(UserRequestParams userRequestParams) {
        return userRepository.findAll(UserSpecification.searchBy(userRequestParams.getSearchQuery()), userRequestParams.getPageable()).map(UserShortDTO::new);
    }

    @GetMapping("/me")
    public UserDTO getCurrentUser(Authentication authentication) {
        return new UserDTO(getUserFromAuthentication(authentication));
    }

    @GetMapping("/{userId}")
    public UserDTO getUser(@PathVariable Long userId) {
        return new UserDTO(userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN)));
    }

    @PostMapping("/update")
    public UserDTO updateCurrent(@RequestBody UserUpdateDTO userUpdateDTO, Authentication authentication) {
        User user = getUserFromAuthentication(authentication);
        if (userUpdateDTO.getName() != null) user.setName(userUpdateDTO.getName());
        if (userUpdateDTO.getImageId() != null) user.setImage(imageRepository.findById(userUpdateDTO.getImageId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        return new UserDTO(userRepository.save(user));
    }

    @PostMapping("/update/{userId}")
    public UserDTO updateUser(@PathVariable Long userId, @RequestBody UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        if (userUpdateDTO.getName() != null) user.setName(userUpdateDTO.getName());
        if (userUpdateDTO.getImageId() != null) user.setImage(imageRepository.findById(userUpdateDTO.getImageId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        return new UserDTO(userRepository.save(user));
    }

    public User getUserFromAuthentication(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }
}
