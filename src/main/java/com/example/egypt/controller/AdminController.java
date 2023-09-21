//package com.example.egypt.controller;
//
//import com.example.egypt.entity.Role;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class AdminController {
//
//    @GetMapping("/admin/resource")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String adminResource() {
//        return "Hello Admin";
//    }
//}
package com.example.egypt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public String userAccess() {
        return "User access";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public String adminAccess() {
        return "Admin access";
    }
}