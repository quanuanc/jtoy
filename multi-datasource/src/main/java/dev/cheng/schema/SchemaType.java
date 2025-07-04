package dev.cheng.schema;

import lombok.Getter;

@Getter
public enum SchemaType {
    PUBLIC("public"),
    HACN("hacn"),
    HBCN("hbcn");

    private final String schemaName;

    SchemaType(String schemaName) {
        this.schemaName = schemaName;
    }

    public static SchemaType fromString(String schema) {
        for (SchemaType type : SchemaType.values()) {
            if (type.schemaName.equalsIgnoreCase(schema)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown schema: " + schema);
    }
}
