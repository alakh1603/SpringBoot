package com.alakh.service;


import com.alakh.modal.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE", url = "http://localhost:5001")
public interface UserService {

    @GetMapping("/app/users/profile") // should be /api/users/profile but getting 403 forbidden error
    public UserDto getUSerProfile(@RequestHeader("Authorization") String jwt);
}
