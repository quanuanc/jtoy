package dev.cheng.schema;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        SchemaType schemaType = SchemaContextHolder.getSchema();
        SchemaType resolvedSchema;
        if (schemaType != null)
            resolvedSchema = schemaType;
        else {
            log.warn("Do not have schema specified");
            resolvedSchema = SchemaType.PUBLIC;
        }
        log.debug("Routing to datasource: {}", resolvedSchema);
        return resolvedSchema;
    }
}
