package be.ae.googleclouddemo.moodboard.mood;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static be.ae.googleclouddemo.moodboard.mood.MoodController.emitters;

@Service
public class MoodService {

	public void setMood(Double mood) {
		emitters.forEach((SseEmitter emitter) -> {
			try {
				emitter.send(mood, MediaType.APPLICATION_JSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
