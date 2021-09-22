package com.example.barclays.accountmanagementsystem.service;

import com.example.barclays.accountmanagementsystem.dto.ApiResponse;
import com.example.barclays.accountmanagementsystem.entity.Customer;
import com.example.barclays.accountmanagementsystem.entity.User;
import com.example.barclays.accountmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ApiResponse loginUser(User user){
        Optional<User> respUser = userRepository.findByUserIdAndPasswordAndRole(user.getUserId(), user.getPassword(), user.getRole());
        if(respUser.isEmpty())
            return new ApiResponse(false,"authentication error");
        return new ApiResponse(true,"authentication successful");
    }

    public ApiResponse save(User user){
        userRepository.save(user);
        return new ApiResponse(true,"Saved changes");
    }

    public void firstSignup(User user){

    }

    public String FetchTempPassword(User user){
        Optional<User> tempUser = userRepository.findById(user.getUserId());
        if(tempUser.isEmpty())
            return null;
        return tempUser.get().getPassword();
    }


}
