package paginaAspe.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AuthFilter implements Filter {

    private final JwtUtil jwt;
    private final AntPathMatcher matcher = new AntPathMatcher();
    private final List<String> openPaths = List.of(
            "/api/auth/**",
            "/api/public/**",
            "/actuator/**");

    public AuthFilter(JwtUtil jwt) {
        this.jwt = jwt;
    }

    private boolean isOpen(String path) {
        for (String p : openPaths)
            if (matcher.match(p, path))
                return true;
        return false;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) req;
        HttpServletResponse w = (HttpServletResponse) res;

        if ("OPTIONS".equalsIgnoreCase(r.getMethod())) {
            w.setStatus(200);
            return;
        }

        String path = r.getRequestURI();
        if (isOpen(path)) {
            chain.doFilter(req, res);
            return;
        }

        String auth = r.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            unauthorized(w, "Falta token Bearer");
            return;
        }

        String token = auth.substring(7);
        try {
            Jws<Claims> jws = jwt.parse(token);
            Claims claims = jws.getPayload();
            r.setAttribute("auth.userId", ((Number) claims.get("id")).longValue());
            r.setAttribute("auth.email", claims.get("email"));
            r.setAttribute("auth.rol", claims.get("rol"));
            chain.doFilter(req, res);
        } catch (Exception e) {
            unauthorized(w, "Token inválido o expirado");
        }
    }

    private void unauthorized(HttpServletResponse w, String msg) throws IOException {
        w.setStatus(401);
        w.setContentType("application/json");
        new ObjectMapper().writeValue(w.getOutputStream(), Map.of("error", msg));
    }
}
