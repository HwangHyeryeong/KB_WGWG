package kb.wgwg.user.repository;

import kb.wgwg.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from USER_ENTITY where email = ?1", nativeQuery = true)
    User findByEmail(String email);

    @Query(value = "select * from USER_ENTITY where nick_name = ?1", nativeQuery = true)
    User findByNickName(String nickName);

    @Modifying
    void deleteByUserSeq(Long userSeq);

    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.userSeq = :userSeq")
    void updateUserPassword(@Param("newPassword") String newPassword, @Param("userSeq") Long userSeq) throws Exception;
}
