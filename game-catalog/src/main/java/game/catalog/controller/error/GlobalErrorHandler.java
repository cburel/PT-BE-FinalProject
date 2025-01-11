package game.catalog.controller.error;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {
	
	public Map<String, String> handleNoSuchElementException(NoSuchElementException exception){
		log.info("Exception: {}", exception.toString());
		return Map.of("message", exception.toString());
	}
}
