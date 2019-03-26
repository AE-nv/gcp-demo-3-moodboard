package be.ae.googleclouddemo.moodboard;

import be.ae.googleclouddemo.moodboard.mood.MoodConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class MoodSubscriberConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoodSubscriberConfig.class);
    private final PubSubTemplate pubSubTemplate;
    private final MoodConsumer moodConsumer;

    public MoodSubscriberConfig(PubSubTemplate pubSubTemplate, MoodConsumer moodConsumer) {
        this.pubSubTemplate = pubSubTemplate;
        this.moodConsumer = moodConsumer;
    }

    /**
     * It's called only when the application is ready to receive requests.
     * Passes a consumer implementation when subscribing to a Pub/Sub topic.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void subscribe() {
        LOGGER.info("Subscribing {} to {}", moodConsumer.getClass().getSimpleName(),
                moodConsumer.subscription());
        pubSubTemplate.subscribe(moodConsumer.subscription(), moodConsumer.consumer());
    }
}
