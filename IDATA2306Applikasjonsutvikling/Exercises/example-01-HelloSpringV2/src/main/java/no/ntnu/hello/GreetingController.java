package no.ntnu.hello;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A REST API controller which responds to HTTP requests for /hello.
 */
@RestController
public class GreetingController {

  /**
   * Responds to HTTP Delete requests for /hello.
   *
   * @return a greeting message
   */
  @DeleteMapping("/hello")
  public ResponseEntity<String> greetingDelete() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Hello, world!");
  }

  /**
   * Responds to HTTP GET requests for /hello.
   *
   * @return a greeting message
   */
  @GetMapping("/hello")
  public ResponseEntity<String> greeting() {
    return ResponseEntity.ok("Hello, world!");
  }



  /**
   * Responds to HTTP GET requests for /Hei
   *
   * @return a greeting message in Norwegian
   */
  @GetMapping("/hei")
  public ResponseEntity<String> norwegianGreeting() {
    return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).body("Hei, verden!");
  }
}

