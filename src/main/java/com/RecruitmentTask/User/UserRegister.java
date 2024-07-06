package com.RecruitmentTask.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class UserRegister {

    private final UserRepository userRepository;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserRegister(UserRepository userRepository, UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Konto o takiej nazwie już istnieje.");
            return "redirect:/login";
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("success", "Użytkownik zarejestrowany pomyślnie.");
        return "redirect:/login";
    }

    @PostMapping("/api/signup")
    public ResponseEntity<String> handleFormSubmit(@RequestBody User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.saveUser(user);

        return ResponseEntity.ok("Formularz przesłany pomyślnie");
    }
}
