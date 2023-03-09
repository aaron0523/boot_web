package com.aaron.springboot.web;

import com.aaron.springboot.config.auth.LoginUser;
import com.aaron.springboot.config.auth.dto.SessionUser;
import com.aaron.springboot.service.PostsService;
import com.aaron.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    /* 머스테치 스타터 덕분에 앞의 경로와 뒤의 파일 확장자는 자동으로 지정
       index 를 반환하므로 (return "index";)
       src/main/resources/template/index.mustache 로 전환되어 View Resolver 가 처리 */
    @GetMapping("/")
    /* Model : 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장
       postsService.findAllDesc()로 가져온 결과를 posts 로 index.mustache 에 전달 */
    public String index(Model model, @LoginUser SessionUser user) {

            // @LoginUser 사용하지 않았을때의 코드
                /*model.addAttribute("posts", postsService.findAllDesc());
                 (SessionUser) httpSession.getAttribute("user")
                   : CustomOAuth2UserService 에서 로그인 성공 시 세션에 SessionUser 를 저장하도록 구성
                      즉, 로그인 성공 시 httpSession.getAttibute("user")에서 값을 가져올 수 있음
                SessionUser user = (SessionUser) httpSession.getAttribute("user");
                 if(user != null)
                   : 세션에 저장된 값이 있을 때만 model 에 userName 으로 등록
                     세션에 저장된 값이 없으면 model 엔 아무런 값이 없는 상태니 로그인 버튼이 보이게 됨
                if(user != null) {
                    model.addAttribute("myName", user.getName());
                }
                return "index";*/
        /* @LoginUser SessionUser user
       : 기존 user = (SessionUser) httpSession.getAttribute("user")로 가져오던 세션 정보 값이 개선
         어느 컨트롤러든지 @LoginUser 만 사용하면 세션 정보를 가져올 수 있게 됨 */
        model.addAttribute("posts", postsService.findAllDesc());
        if(user != null) {
            model.addAttribute("myName", user.getName());
        }
        return "index";
    }

    // '/posts/save' 를 호출하면 posts-save.mustache 를 호출하는 메소드
    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }

}

