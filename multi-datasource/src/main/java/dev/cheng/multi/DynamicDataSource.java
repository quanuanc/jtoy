package dev.cheng.multi;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        SchemaType schemaType = SchemaContextHolder.getSchema();
        return schemaType != null ? schemaType : SchemaType.HACN;
    }
}
