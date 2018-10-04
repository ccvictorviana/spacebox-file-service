package br.com.file.repository.custom;

import br.com.file.domain.view.FileView;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface FileCustomRepository {

    List<FileView> list(Long userId, Date beginUpdateDate, Long fileParentId);

    List<FileView> listFoldersShared(Long userId, Date beginUpdateDate, Long fileParentId);
}
