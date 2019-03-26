package be.ae.googleclouddemo.moodboard.mood;

import com.google.pubsub.v1.PubsubMessage;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

@Component
public class MoodConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoodConsumer.class);

    private final MoodService repository;

    public MoodConsumer(MoodService repository) {
        this.repository = repository;
    }

    public String subscription() {
        return "mood-board";
    }

    /**
     * The actual consumer logic.
     *
     * @param acknowledgeablePubsubMessage Wrapper of a Pub/Sub message that adds acknowledging capability.
     */
    protected void consume(BasicAcknowledgeablePubsubMessage acknowledgeablePubsubMessage) {
        PubsubMessage message = acknowledgeablePubsubMessage.getPubsubMessage();

        // process message
        LOGGER.info("message received: " + message.getData().toStringUtf8());
        try {
            JSONObject messageObject = (JSONObject) new JSONParser(DEFAULT_PERMISSIVE_MODE).parse(message.getData().toStringUtf8());
            Double averageMood = Double.parseDouble(messageObject.get("average_mood").toString());
            repository.setMood(averageMood);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // acknowledge that message was received
        acknowledgeablePubsubMessage.ack();
    }



    /**
     * Implementation of a {@link Consumer} functional interface which only calls the
     * {@link #consume(BasicAcknowledgeablePubsubMessage) consume} method.
     */
    public Consumer<BasicAcknowledgeablePubsubMessage> consumer() {
        return this::consume;
    }
}
