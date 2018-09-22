package br.com.file.repository;

import br.com.file.domain.File;
import br.com.file.domain.view.FileView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    File findByIdAndUserId(Long id, Long userId);

    File findByNameAndFileParentId(String name, Long fileParentId);

    @Query("SELECT new br.com.file.domain.view.FileView(f.id, f.name, f.type, f.size, f.fileParentId, f.created, f.updated) FROM File f " +
            "WHERE f.userId = ?1 AND (?2 IS NULL OR f.updated > ?2) AND ((?3 IS NULL AND f.fileParentId IS NULL) OR f.fileParentId = ?3) ORDER BY f.type, f.name")
    List<FileView> list(Long userId, Date beginUpdateDate, Long fileParentId);

    @Modifying
    @Transactional
    @Query("UPDATE File f SET f.name = ?3 WHERE f.id = ?1 AND f.userId = ?2")
    void rename(Long userId, Long id, String name);
}