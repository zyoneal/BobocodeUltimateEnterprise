package ServerJavaServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@WebServlet("/morning")
public class MorningServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        printClientsMood(req);
        printClientCookies(req);
        printMorning(req,resp);
        resp.addCookie(new Cookie("SUPER_ID", UUID.randomUUID().toString()));
    }

    private void printClientCookies(HttpServletRequest req) {
        Stream.of(req.getCookies()).forEach(cookie -> System.out.println(cookie.getName() + " - " + cookie.getValue()));
    }

    @SneakyThrows
    private void printMorning(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        var sessionName = Optional.ofNullable((String)session.getAttribute("name"));
        var requestName = Optional.ofNullable(req.getParameter("name"));

        requestName.filter(name -> sessionName.isEmpty())
                .ifPresent(name -> session.setAttribute("name",name));

        var writer = resp.getWriter();
        var name = requestName
                .or(() -> sessionName)
                .orElse("my friend");
        writer.println("Good morning, " + name);
    }

    private void printClientsMood(HttpServletRequest req) {
        var name = Optional.ofNullable(req.getParameter("name"))
                .orElse(req.getRemoteAddr());
        Optional.ofNullable(req.getHeader("X-mood"))
                .ifPresent(mood -> System.out.println(name + "is feeling " + mood));
    }

}
