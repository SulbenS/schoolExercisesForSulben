package com.example.spring_boot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

  public PageController() {

  }

  @GetMapping("/")
  public String getHome() {
    return "index";
  }

  @GetMapping("/about")
  public String getAbout() {
      return "about";
  }

  @GetMapping("/books")
  public String getBooks() {
      return "books";
  }
}
