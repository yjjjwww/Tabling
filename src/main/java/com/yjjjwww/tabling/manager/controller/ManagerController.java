package com.yjjjwww.tabling.manager.controller;

import com.yjjjwww.tabling.manager.model.ManagerPartnerForm;
import com.yjjjwww.tabling.manager.model.ManagerSignInForm;
import com.yjjjwww.tabling.manager.model.ManagerSignUpForm;
import com.yjjjwww.tabling.manager.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody ManagerSignUpForm managerSignUpForm) {
        return ResponseEntity.ok(managerService.signUp(managerSignUpForm));
    }

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody ManagerSignInForm managerSignInForm) {
        return ResponseEntity.ok(managerService.signIn(managerSignInForm));
    }

    @PostMapping("/partner")
    public ResponseEntity<String> getPartner(@RequestBody ManagerPartnerForm managerPartnerForm) {
        return ResponseEntity.ok(managerService.getPartner(managerPartnerForm));
    }
}
