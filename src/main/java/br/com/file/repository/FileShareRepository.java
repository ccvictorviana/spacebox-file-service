package br.com.file.repository;

import br.com.file.domain.FileShare;
import br.com.file.domain.view.UserShareView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FileShareRepository extends JpaRepository<FileShare, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM FileShare f WHERE f.fileId = ?1 AND f.userId = ?2")
    void delete(Long fileId, Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM FileShare f WHERE f.fileId = ?1")
    void delete(Long fileId);

    @Query("SELECT new br.com.file.domain.view.UserShareView(u.id, u.username) FROM User u WHERE u.username LIKE CONCAT('%',?2,'%') AND u.id != ?1")
    List<UserShareView> listUsers(Long userId, String userName);

    List<FileShare> findAllByFileId(Long fileId);
}