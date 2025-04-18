package dmit2015.batch;

import dmit2015.entity.EnforcementZoneCentre;

import jakarta.batch.api.chunk.ItemProcessor;
import jakarta.batch.runtime.context.JobContext;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Properties;

/**
 * An ItemProcessor is executed after an ItemReader has finished.
 */
@Named
@Dependent
public class EnforcementZoneCentreStringToSqlStatementItemProcessor implements ItemProcessor {

    @Inject
    private JobContext _jobContext;

    /**
     * Change the return type of this method to the type of object (JsonObject, String, etc) you are processing
     * Process one item returned from an ItemReader
     */
    @Override
    public String processItem(Object item) throws Exception {
        Properties jobParameters = _jobContext.getProperties();
        String tableName = jobParameters.getProperty("table_name");

        String line = (String) item;

        Optional<EnforcementZoneCentre> optionalModel = EnforcementZoneCentre.parseCsv(line);
        EnforcementZoneCentre model = optionalModel.orElseThrow();

        return String.format(
                "INSERT INTO %s(site_id, location_description, speed_limit, reason_code, latitude, longitude, createTime, geoLocation) "
                + " VALUES(%d, '%s',  %d, '%s', %f, %f, '%s','%s');",
                tableName.toUpperCase(),
                model.getSiteId(),
                model.getLocationDescription(),
                model.getSpeedLimit(),
                model.getReasonCodes(),
                model.getLatitude(),
                model.getLongitude(),
                LocalDateTime.now(),
                String.format("POINT(%f %f)", model.getLongitude() , model.getLatitude() )
                );
    }

}