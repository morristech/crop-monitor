package midien.kheldiente.cma.utils.scheduler;


import io.reactivex.Scheduler;

public interface BaseSchedulerProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}
