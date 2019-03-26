package be.ae.googleclouddemo.moodboard.mood;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class MoodController {

    static final List<SseEmitter> emitters = Collections.synchronizedList( new ArrayList<>());

    @GetMapping("mood")
    public SseEmitter getMood() {

        SseEmitter emitter = new SseEmitter();

        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));

        return emitter;
    }
}
