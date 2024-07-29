package music.mapper;

import music.dto.UserAuth;
import music.dto.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    // 회원 등록
    int insert(Users user) throws Exception;

    // 회원 조회
    Users select(int userNo) throws Exception;

    // 사용자 인증(로그인) - id
    Users login(@Param("username") String username);

    // 회원 권한 등록
    int insertAuth(UserAuth userAuth) throws Exception;

    // 회원 수정
    int update(Users user) throws Exception;

    // 회원 삭제
    int delete(@Param("userId") String userId) throws Exception;

}