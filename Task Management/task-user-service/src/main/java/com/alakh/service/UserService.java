package com.alakh.service;

import com.alakh.modal.User;
import java.util.*;

public interface UserService {
    public User getUserProfile(String jwt);

    public List<User> getAllUsers();
}
