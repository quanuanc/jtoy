package dev.cheng.multi;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SchemaInterceptor implements HandlerInterceptor {

    private static final String SCHEMA_HEADER = "X-Schema";
    private static final String SCHEMA_PARAM = "schema";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String schema = getSchemaFromRequest(request);

        if (schema != null) {
            try {
                SchemaType schemaType = SchemaType.fromString(schema);
                SchemaContextHolder.setSchema(schemaType);
            } catch (IllegalArgumentException e) {
                // 如果schema无效，使用默认的HACN
                SchemaContextHolder.setSchema(SchemaType.HACN);
            }
        } else {
            // 默认使用HACN schema
            SchemaContextHolder.setSchema(SchemaType.HACN);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) {
        SchemaContextHolder.clearSchema();
    }

    private String getSchemaFromRequest(HttpServletRequest request) {
        // 首先检查请求头
        String schema = request.getHeader(SCHEMA_HEADER);
        if (schema == null) {
            // 然后检查请求参数
            schema = request.getParameter(SCHEMA_PARAM);
        }
        return schema;
    }
}
