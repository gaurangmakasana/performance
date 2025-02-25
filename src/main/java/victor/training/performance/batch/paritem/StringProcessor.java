package victor.training.performance.batch.paritem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import static victor.training.performance.util.PerformanceUtil.sleepq;

@Slf4j
public class StringProcessor implements ItemProcessor<String, String> {
    @Override
    public synchronized String process(String item) {
        log.debug("Start processing " + item);
        sleepq(100);
        log.debug("End processing " + item);
        return item.toUpperCase();
    }
}
