package com.aaron.springboot.web;

import com.aaron.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // JSON을 반환하는 컨트롤러로 만들어 준다.
// 예전에는 @ResponseBody를 각 메소드마다 선언했던 것을 한번에 사용할 수 있게 한다.
public class HelloController {

    @GetMapping("/hello") // 예전에는 @RequestMapping(method = RequestMethod.GET)으로 사용했었음
    public String hello() {
        return "hello";
    }

    // 롬복 사용 후
    @GetMapping("/hello/dto")
    /* RequestParam : 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션
       name (@RequestParam("name")) 이란 이름으로 넘긴 파라미터를 메소드 파라미터 name(String name)에 저장 */
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
}
