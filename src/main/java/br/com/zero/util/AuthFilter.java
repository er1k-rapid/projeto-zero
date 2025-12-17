package br.com.zero.util;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String path = req.getRequestURI();

        boolean isLoginPage = path.equals("/login");
        boolean isCadastroPage = path.equals("/cadastro");
        boolean isLoginApi = path.equals("/api/login");
        boolean isCadastroApi = path.equals("/api/empresas");
        boolean isUsuariosApi = path.equals("/api/usuarios");
        boolean isVerificarEmail = path.equals("/verificar-email");
        boolean isAssets = path.startsWith("/assets/") || path.startsWith("/resources/");
        boolean isRoot = path.equals("/");

        if (isRoot) {
            resp.sendRedirect("/login");
            return;
        }

        if (isLoginPage || isLoginApi || isAssets || isCadastroPage || isCadastroApi || isUsuariosApi || isVerificarEmail) {
            chain.doFilter(request, response);
            return;
        }

        if (session == null || session.getAttribute("usuario") == null) {

            if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            resp.sendRedirect("/login");
            return;
        }

        chain.doFilter(request, response);
    }
}

