package by.Ahmed.jdbc.starter.servlet;

import by.Ahmed.jdbc.starter.dto.CreateUserDto;
import by.Ahmed.jdbc.starter.entity.Gender;
import by.Ahmed.jdbc.starter.entity.Role;
import by.Ahmed.jdbc.starter.service.UserService;
import by.Ahmed.jdbc.starter.utils.JspHelper;
import by.Ahmed.jdbc.starter.validator.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;

import static by.Ahmed.jdbc.starter.utils.UrlPath.REGISTRATION;

@WebServlet(REGISTRATION)
@Slf4j
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", Role.values());
        req.setAttribute("genders", Gender.values());
        req.getRequestDispatcher(JspHelper.getPath("registration"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                var name = req.getParameter("name");
                var userDto = CreateUserDto.builder()
                        .name(req.getParameter("name"))
                        .birthday(req.getParameter("birthday"))
                        .email(req.getParameter("email"))
                        .password(req.getParameter("pwd"))
                        .role(req.getParameter("role"))
                        .gender(req.getParameter("gender"))
                        .build();
                try {
                    userService.create(userDto);
                    log.info("User is created: {}", userDto);
                    resp.sendRedirect("/login");
                } catch (ValidationException exception) {
                    req.setAttribute("errors", exception.getErrors());
                    doGet(req, resp);
                }
                session.getTransaction().commit();
                session.close();
            }
        } catch (Exception e) {
            log.error("Exception occured: {}", e);
        }
    }
}