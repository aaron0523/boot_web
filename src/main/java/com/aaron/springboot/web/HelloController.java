package com.aaron.springboot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // JSON을 반환하는 컨트롤러로 만들어 준다.
// 예전에는 @ResponseBody를 각 메소드마다 선언했던 것을 한번에 사용할 수 있게 한다.
public class HelloController {

    @GetMapping("/hello") // 예전에는 @RequestMapping(method = RequestMethod.GET)으로 사용했었음
    public String hello() {
        return "hello";
    }
}
