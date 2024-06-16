package org.example.floormanagement.Security.RoleControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/management")

public class MemberController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping
    public String getMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "Secured Endpoint :: GET - Member Controller";
    }
}
