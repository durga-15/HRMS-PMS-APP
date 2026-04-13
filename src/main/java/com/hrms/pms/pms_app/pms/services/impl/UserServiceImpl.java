package com.hrms.pms.pms_app.pms.services.impl;
import com.hrms.pms.pms_app.pms.config.AppConstants;
import com.hrms.pms.pms_app.pms.dtos.RoleDto;
import com.hrms.pms.pms_app.pms.dtos.UserDto;
import com.hrms.pms.pms_app.pms.entities.Role;
import com.hrms.pms.pms_app.pms.entities.User;
import com.hrms.pms.pms_app.pms.exception.ResourceNotFoundException;
import com.hrms.pms.pms_app.pms.helper.UserHelper;
import com.hrms.pms.pms_app.pms.repositories.RoleRepository;
import com.hrms.pms.pms_app.pms.repositories.UserRepository;
import com.hrms.pms.pms_app.pms.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    //    private final UserHelper userHelper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {

//        if(userDto.getEmail() == null || userDto.getEmail().isBlank()){
//            throw new IllegalArgumentException("Email is Required");
//        }
//
//        if(userRepository.existsByEmailIgnoreCase(userDto.getEmail())){
//            throw new IllegalArgumentException("User with given email already exists");
//        }
//
//        User user = modelMapper.map(userDto, User.class);
//        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//
//        if (user.getRoles() == null) {
//            user.setRoles(new HashSet<>());
//        }
//        //role will be assign here for the user
//        Role role = roleRepository.findByName("ROLE_" +AppConstants.EMP_ROLE).orElseThrow(() -> new RuntimeException("Role not found"));
//        user.getRoles().add(role);
//        User savedUser = userRepository.save(user);
//        return modelMapper.map(savedUser, UserDto.class);

        // ✅ Validate email
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        // ✅ Check duplicate
        if (userRepository.existsByEmailIgnoreCase(userDto.getEmail())) {
            throw new IllegalArgumentException("User with given email already exists");
        }

        // ✅ Map DTO → Entity
        User user = modelMapper.map(userDto, User.class);

        // ✅ Encode password
//        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // ✅ Assign roles
        Set<Role> roles = new HashSet<>();

        // 🔐 Check if logged-in user is ADMIN
//        boolean isAdmin = false;
//
//        if (SecurityContextHolder.getContext().getAuthentication() != null) {
//            isAdmin = SecurityContextHolder.getContext()
//                    .getAuthentication()
//                    .getAuthorities()
//                    .stream()
//                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
//        }

        // ✅ If roles provided AND creator is ADMIN
        if (userDto.getRoles() != null && !userDto.getRoles().isEmpty()) {

//            if (!isAdmin) {
//                throw new RuntimeException("Only ADMIN can assign roles");
//            }

            for (RoleDto roleDto : userDto.getRoles()) {

                String roleName = roleDto.getName(); // ✅ extract name

                Role role = roleRepository.findByName("ROLE_" + roleName.toUpperCase())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

                roles.add(role);
            }

        } else {
            // ✅ Default role
            Role defaultRole = roleRepository.findByName("ROLE_" + AppConstants.EMP_ROLE)
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            roles.add(defaultRole);
        }

        user.setRoles(roles);

        // ✅ Save user
        User savedUser = userRepository.save(user);

        // ✅ Return DTO
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with given email id"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        UUID uId = UserHelper.parseUUID(userId);
        User existingUser = userRepository.findById(uId).orElseThrow(() -> new ResourceNotFoundException("User Not Found With this given Id"));
        if(userDto.getUserName() != null) existingUser.setUserName(userDto.getUserName());
        if(userDto.getPassword() != null) existingUser.setPassword(userDto.getPassword());
        existingUser.setStatus(userDto.isStatus());
        User updateUser = userRepository.save(existingUser);
        return modelMapper.map(updateUser, UserDto.class);
    }

    @Override
    public void deleteUser(String userId) {
        UUID uID = UserHelper.parseUUID(userId);
        User user = userRepository.findById(uID).orElseThrow(() -> new ResourceNotFoundException("User not found with this give id"));
        userRepository.delete(user);
    }
    @Override
    public UserDto getUserById(String userId){
        User user = userRepository.findById(UserHelper.parseUUID(userId)).orElseThrow(()-> new ResourceNotFoundException("User not found with this give id"));
        return modelMapper.map(user, UserDto.class);
    }
    @Override
    @Transactional
    public Iterable<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }
}


