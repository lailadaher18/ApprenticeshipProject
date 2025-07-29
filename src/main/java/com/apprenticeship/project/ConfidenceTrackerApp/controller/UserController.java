package com.apprenticeship.project.ConfidenceTrackerApp.controller;

import com.apprenticeship.project.ConfidenceTrackerApp.model.CustomUserPrincipal;
import com.apprenticeship.project.ConfidenceTrackerApp.model.User;
import com.apprenticeship.project.ConfidenceTrackerApp.service.UserService;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private static final Map<Integer, String> GENRE_MAP = new HashMap<>();

    static {
        GENRE_MAP.put(28, "Action");
        GENRE_MAP.put(12, "Adventure");
        GENRE_MAP.put(16, "Animation");
        GENRE_MAP.put(35, "Comedy");
        GENRE_MAP.put(80, "Crime");
        GENRE_MAP.put(99, "Documentary");
        GENRE_MAP.put(18, "Drama");
        GENRE_MAP.put(10751, "Family");
        GENRE_MAP.put(14, "Fantasy");
        GENRE_MAP.put(36, "History");
        GENRE_MAP.put(27, "Horror");
        GENRE_MAP.put(10402, "Music");
        GENRE_MAP.put(9648, "Mystery");
        GENRE_MAP.put(10749, "Romance");
        GENRE_MAP.put(878, "Science Fiction");
        GENRE_MAP.put(10770, "TV Movie");
        GENRE_MAP.put(53, "Thriller");
        GENRE_MAP.put(10752, "War");
        GENRE_MAP.put(37, "Western");
    }

    @GetMapping("/register")
    public String showRegistrationForm(@RequestParam(value = "page", defaultValue = "1") Integer page, Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        model.addAttribute("page", page);
        model.addAttribute("genres", GENRE_MAP);
        return "register";
    }

    @PostMapping(value = "/register", params = "page=1")
    public String handleFormPart1(@ModelAttribute User user, HttpSession session) {
        session.setAttribute("partialUser", user);
        return "redirect:/register?page=2";
    }

    @PostMapping(value = "/register", params = "page=2")
    public String handleFormPart2(@RequestParam(required = false) List<String> favoriteGenres, HttpSession session, RedirectAttributes redirectAttributes) {
        User partialUser = (User) session.getAttribute("partialUser");
        if (partialUser == null) {
            redirectAttributes.addFlashAttribute("flashMessages", "Please complete part 1 of the form first.");
            return "redirect:/register?page=1";
        }

        if (favoriteGenres != null && !favoriteGenres.isEmpty()) {
        //    partialUser.setFavoriteGenres(String.join(",", favoriteGenres));
        } else {
        //    partialUser.setFavoriteGenres("Animation"); // Default genre
        }

        try {
            userService.registerUser(partialUser);
            session.removeAttribute("partialUser");
            redirectAttributes.addFlashAttribute("flashMessages", "Registration successful! Please log in.");
            return "redirect:/login";
        } catch (Exception e) {
            logger.error("Registration failed", e);
            redirectAttributes.addFlashAttribute("flashMessages", "Invalid Registration Details");
            redirectAttributes.addFlashAttribute("user", partialUser);
            return "redirect:/register?page=2";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, HttpSession session, RedirectAttributes redirectAttributes) {
        Optional<User> loggedInUser = userService.userLogin(user.getUsername(), user.getPassword());
        if (loggedInUser.isPresent()) {
            session.setAttribute("user", loggedInUser.get());
            return "redirect:/home";
        } else {
            redirectAttributes.addFlashAttribute("flashMessages", "Invalid credentials");
            return "redirect:/login";
        }
    }

    @GetMapping("/profile")
    public String showProfilePage(@RequestParam(value = "page", defaultValue = "1") Integer page, Model model, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserPrincipal)) {
            return "redirect:/login";
        }

        CustomUserPrincipal userDetails = (CustomUserPrincipal) authentication.getPrincipal();
        Optional<User> user = userService.getUser(userDetails.getUserId());

        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            model.addAttribute("page", page);
            model.addAttribute("genres", GENRE_MAP);
            return "profile";
        } else {
            return "redirect:/login";
        }
    }

//    @PostMapping(value = "/profile")
//    public String handleProfileUpdate(@ModelAttribute UserFormDTO userFormDTO, Authentication authentication, RedirectAttributes redirectAttributes) {
//        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserPrincipal)) {
//            return "redirect:/login";
//        }
//        CustomUserPrincipal userDetails = (CustomUserPrincipal) authentication.getPrincipal();
//        Long userId = userDetails.getUserId();
//
//        String favoriteGenreString = (userFormDTO.getFavoriteGenres() != null) ? String.join(",", userFormDTO.getFavoriteGenres()) : null;
//
//        Optional<User> editedUser = userService.editUser(
//                userId,
//                userFormDTO.getUsername(),
//                userFormDTO.getFirstName(),
//                userFormDTO.getPassword(),
//                favoriteGenreString
//        );
//
//        if (editedUser.isPresent()) {
//            redirectAttributes.addFlashAttribute("flashMessages", "User details updated successfully!");
//        } else {
//            redirectAttributes.addFlashAttribute("flashMessages", "Failed to update user details.");
//        }
//        return "redirect:/profile";
//    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}