package com.todo.todo_back.web_controllers.user_controller;

import com.todo.todo_back.entities.User;
import com.todo.todo_back.repositories.ImageRepository;
import com.todo.todo_back.repositories.UserRepository;
import com.todo.todo_back.specifications.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
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
    public PagedModel<UserShortDTO> getAllUsers(UserRequestParams userRequestParams) {
        return new PagedModel<>(userRepository.findAll(UserSpecification.searchBy(userRequestParams.getSearchQuery(), userRequestParams.getIsVerified()), userRequestParams.getPageable()).map(UserShortDTO::new));
    }

    @GetMapping("/meShort")
    public UserShortDTO getCurrentUserShort(Authentication authentication) {
        return new UserShortDTO(getUserFromAuthentication(authentication));
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
        return updateUser(user, userUpdateDTO);
    }

    @PostMapping("/update/{userId}")
    public UserDTO updateUser(@PathVariable Long userId, @RequestBody UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));
        return updateUser(user, userUpdateDTO);
    }

    private UserDTO updateUser(User user, UserUpdateDTO userUpdateDTO) {
        if (userUpdateDTO.getName() != null) user.setName(userUpdateDTO.getName());
        if (userUpdateDTO.getImageId() != null) user.setImage(imageRepository.findById(userUpdateDTO.getImageId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        try {
            return new UserDTO(userRepository.save(user));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public User getUserFromAuthentication(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }
}
