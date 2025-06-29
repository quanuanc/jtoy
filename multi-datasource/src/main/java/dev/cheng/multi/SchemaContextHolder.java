package dev.cheng.multi;

public class SchemaContextHolder {
    private static final ThreadLocal<SchemaType> contextHolder = new ThreadLocal<>();

    public static void setSchema(SchemaType schemaType) {
        contextHolder.set(schemaType);
    }

    public static SchemaType getSchema() {
        return contextHolder.get();
    }

    public static void clearSchema() {
        contextHolder.remove();
    }
}
