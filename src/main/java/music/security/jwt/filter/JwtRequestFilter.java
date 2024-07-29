package music.security.jwt.filter;

import java.io.IOException;

import music.security.jwt.constants.JwtConstants;
import music.security.jwt.provider.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

//JWT 해석 필터
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    // 생성자
    public JwtRequestFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //JWT 요청 필터
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 헤더에서 jwt 토큰을 가져옴
        String header = request.getHeader(JwtConstants.TOKEN_HEADER);
        log.debug("Authorization Header: {}", header);

        // jwt 토큰이 없으면 다음 필터로 이동
        // Bearer + {jwt} 체크
        if (header == null || header.isEmpty() || !header.startsWith(JwtConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // JWT
        // Bearer + {jwt} ➡ "Bearer " 제거
        String jwt = header.replace(JwtConstants.TOKEN_PREFIX, "").trim();

        // 토큰 유효성 검사
        if (jwtTokenProvider.validateToken(jwt)) {
            log.info("유효한 JWT 토큰입니다.");

            // 토큰 해석
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);

            // 로그인
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            log.warn("유효하지 않은 JWT 토큰입니다.");
        }

        // 다음 필터
        filterChain.doFilter(request, response);
    }
}