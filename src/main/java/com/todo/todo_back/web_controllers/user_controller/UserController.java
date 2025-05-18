package com.todo.todo_back.web_controllers.user_controller;

import com.todo.todo_back.entities.user.User;
import com.todo.todo_back.repositories.ImageRepository;
import com.todo.todo_back.repositories.user_repository.UserRepository;
import com.todo.todo_back.repositories.user_repository.UserShortRepository;
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
    private final UserShortRepository userShortRepository;

    @GetMapping("/sorting_variants")
    public Collection<String> getSortingVariants() {
        return List.of(User.Fields.NAME.getDatabaseFieldName());
    }

    @GetMapping("/all")
    public Page<UserShortDTO> getAllUsers(UserRequestParams userRequestParams) {
        return userShortRepository.findAll(UserSpecification.searchBy(userRequestParams.getSearchQuery()), userRequestParams.getPageable()).map(UserShortDTO::new);
    }

    @GetMapping("/me")
    public UserDTO getCurrentUser(Authentication authentication) {
        return new UserDTO(userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
    }

    @GetMapping("/{userId}")
    public UserDTO getUser(@PathVariable Long userId) {
        return new UserDTO(userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
    }

    @PostMapping("/update")
    public UserDTO updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(userUpdateDTO.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        user.setName(userUpdateDTO.getName());
        user.setImage(imageRepository.findById(userUpdateDTO.getImageId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        return new UserDTO(userRepository.save(user));
    }
}
