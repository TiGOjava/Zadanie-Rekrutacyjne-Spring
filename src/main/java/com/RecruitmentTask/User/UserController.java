package com.RecruitmentTask.User;

import com.RecruitmentTask.Report.Report;
import com.RecruitmentTask.Report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private final UserService userService;
    private final ReportService reportService;

    public UserController(UserService userService, ReportService reportService) {
        this.userService = userService;
        this.reportService = reportService;
    }

    @GetMapping("/home")
    public String showHome(Model model, Authentication authentication) {
        String currentUsername = authentication.getName();
        User currentUser = userService.getUserByLogin(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Report> reports = reportService.findByReportingPersonUsername(currentUsername);
        model.addAttribute("reports", reports);
        model.addAttribute("currentUsername", currentUsername);
        model.addAttribute("currentUserId", currentUser.getId());
        return "home";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
