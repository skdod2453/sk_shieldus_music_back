package music.security.jwt.filter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import music.dto.CustomUser;
import music.security.jwt.constants.JwtConstants;
import music.security.jwt.provider.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

//JWT 토근 생성
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    // 생성자
    public JwtAuthenticationFilter( AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        // 필터 URL 경로 설정 : /login
        setFilterProcessesUrl(JwtConstants.AUTH_LOGIN_URL);  // /login
    }

    //인증 시도 메소드
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        log.info("username : " + username);
        log.info("password : " + password);

        // 사용자 인증정보 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        // 사용자 인증 (로그인)
        authentication = authenticationManager.authenticate(authentication);

        log.info("인증 여부 : " + authentication.isAuthenticated());

        // 인증 실패 (username, password 불일치)
        if( !authentication.isAuthenticated() ) {
            log.info("인증 실패 : 아이디 또는 비밀번호가 일치하지 않습니다.");
            //TODO 401 인증 실패
            response.setStatus(401);            // 401 UNAUTHORIZED (인증 실패)
        }

        return authentication;
    }

    //인증 성공 메소드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {

        log.info("인증 성공...");

        CustomUser user = (CustomUser) authentication.getPrincipal();
        int userNo = user.getUser().getNo();
        String userId = user.getUser().getUserId();

        List<String> roles = user.getUser().getAuthList().stream()
                .map( (auth) -> auth.getAuth())
                .collect( Collectors.toList() )
                ;
        // JWT 토큰 생성 요청
        String jwt = jwtTokenProvider.createToken(userNo, userId, roles);

        // { Authorization : Bearer + {jwt} }
        response.addHeader(JwtConstants.TOKEN_HEADER, JwtConstants.TOKEN_PREFIX+ jwt);
        response.setStatus(200);
    }

}