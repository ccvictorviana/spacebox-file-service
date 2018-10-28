package br.com.file.repository;

import br.com.file.domain.File;
import br.com.file.repository.custom.FileCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface FileRepository extends JpaRepository<File, Long>, FileCustomRepository {
    File findByIdAndUserId(Long id, Long userId);

    @Query("SELECT f FROM File f WHERE f.id = ?1")
    File findFile(Long id);

    @Query("SELECT f FROM File f WHERE f.id = ?1 AND f.userId = ?2 AND f.fileParentId IS NULL")
    File findFile(Long id, Long userId);

    File findByNameAndFileParentIdAndUserId(String name, Long fileParentId, Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE File f SET f.name = ?3 WHERE f.id = ?1 AND f.userId = ?2")
    void rename(Long userId, Long id, String name);
}