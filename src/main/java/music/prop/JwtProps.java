package music.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("token")       // application.properties 의 하위 속성 경로 지정
public class JwtProps {

    private String secret;
}