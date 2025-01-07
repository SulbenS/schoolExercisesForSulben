package no.ntnu.Hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping("/Hello")
  public String hello() {
    return "Hello, World!";
  }
}