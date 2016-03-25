package solution.kelsoft.com.kwahuguide.Services;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;
import solution.kelsoft.com.kwahuguide.CustomMessage;

/**
 * Created by Rukuvwe on 3/19/2016.
 */
public class MyService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        CustomMessage.t(this, "OnStartJob");
        jobFinished(params, false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
