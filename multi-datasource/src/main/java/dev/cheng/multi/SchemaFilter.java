package dev.cheng.multi;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class SchemaFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // 获取当前认证的用户
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated() &&
                    !authentication.getPrincipal().equals("anonymousUser")) {

                String username = authentication.getName();
                log.debug("Current authenticated user: {}", username);

                // 根据用户名设置对应的schema
                SchemaType schemaType = determineSchemaByUser(username);
                SchemaContextHolder.setSchema(schemaType);

                log.debug("Set schema to: {} for user: {}", schemaType, username);
            }

            filterChain.doFilter(request, response);

        } finally {
            // 清理ThreadLocal，防止内存泄漏
            SchemaContextHolder.clearSchema();
        }
    }

    private SchemaType determineSchemaByUser(String username) {
        return switch (username) {
            case "user1" -> SchemaType.HACN;
            case "user2" -> SchemaType.HBCN;
            default -> {
                log.warn("Unknown user: {}, defaulting to HACN schema", username);
                yield SchemaType.HACN;
            }
        };
    }
}
