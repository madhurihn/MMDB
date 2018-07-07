package mmdb_fastutil.kvs.app;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheck.Result;

public class AppHealthCheck extends HealthCheck {
    
    public AppHealthCheck() {
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();//This is always healthy...
    }
}
