package org.example.floormanagement.Security.RoleControllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAnyRole('ADMIN')")
public class AdminController {

    @GetMapping
    public String getAdmin() {
        return "Secured Endpoint :: GET - Admin Controller";
    }
}
