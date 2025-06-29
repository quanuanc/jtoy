package dev.cheng.multi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        SchemaType schemaType = SchemaContextHolder.getSchema();
        SchemaType resolvedSchema = schemaType != null ? schemaType : SchemaType.HACN;
        log.debug("Routing to datasource: {}", resolvedSchema);
        return resolvedSchema;
    }
}
