package com.yoyodev.springblog.core.repositories;

import com.yoyodev.springblog.core.entities.projection.UserAuthProjection;
import com.yoyodev.springblog.core.entities.projection.UserProjection;
import com.yoyodev.springblog.core.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface AuthUserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT u FROM User u
            WHERE u.username = :identity
            OR u.email = :identity
            """)
    Optional<UserProjection> findUserByIdentity(@Param("identity") String identity);

    @Query(value = "SELECT * FROM get_user_auth(:username)", nativeQuery = true)
    List<UserAuthProjection> findUserAuthByUsername(@Param("username") String username);
}
