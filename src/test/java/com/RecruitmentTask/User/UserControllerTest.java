package com.RecruitmentTask.User;

import com.RecruitmentTask.Report.Report;
import com.RecruitmentTask.Report.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ReportService reportService;

    @Mock
    private Authentication authentication;

    @Mock
    private Model model;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowHome() {
        String username = "testuser";
        User user = new User();
        user.setId(1L);
        user.setLogin(username);

        List<Report> reports = new ArrayList<>();
        reports.add(new Report());

        when(authentication.getName()).thenReturn(username);
        when(userService.getUserByLogin(username)).thenReturn(Optional.of(user));
        when(reportService.findByReportingPersonUsername(username)).thenReturn(reports);

        String viewName = userController.showHome(model, authentication);

        verify(model).addAttribute("reports", reports);
        verify(model).addAttribute("currentUsername", username);
        verify(model).addAttribute("currentUserId", user.getId());

        assertEquals("home", viewName);
    }

    @Test
    public void testShowHome_UserNotFound() {
        String username = "nonexistentuser";

        when(authentication.getName()).thenReturn(username);
        when(userService.getUserByLogin(username)).thenReturn(Optional.empty());

        try {
            userController.showHome(model, authentication);
        } catch (UsernameNotFoundException e) {
            assertEquals("User not found", e.getMessage());
        }

        verify(userService).getUserByLogin(username);
        verify(reportService, never()).findByReportingPersonUsername(anyString());
    }

    @Test
    public void testShowLoginForm() {
        String viewName = userController.showLoginForm();
        assertEquals("login", viewName);
    }
}

