package br.com.file.repository.custom.impl;

import br.com.file.domain.view.FileView;
import br.com.file.repository.custom.FileCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class FileRepositoryImpl implements FileCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<FileView> list(Long userId, Date beginUpdateDate, Long fileParentId) {
        return getFiles(" File f ", " (:userId IS NULL OR f.userId = :userId) ", userId, beginUpdateDate, fileParentId);
    }

    @Override
    public List<FileView> listFoldersShared(Long userId, Date beginUpdateDate, Long fileParentId) {
        return getFiles(" FileShare fs JOIN fs.file f ", " fs.userId = :userId ", userId, beginUpdateDate, fileParentId);
    }

    private List<FileView> getFiles(String from, String where, Long userId, Date beginUpdateDate, Long fileParentId) {
        StringBuilder builder = new StringBuilder();
        builder.append(" SELECT new br.com.file.domain.view.FileView(f.id, f.name, f.type, f.size, f.fileParentId, f.created, f.updated) ");
        builder.append(" FROM ");
        builder.append(from);
        builder.append(" WHERE ");
        builder.append(where);
        builder.append(" AND ((:fileParentId IS NULL AND f.fileParentId IS NULL) OR f.fileParentId = :fileParentId) ");

        if (beginUpdateDate != null)
            builder.append(" AND ( f.updated > :updated ) ");

        builder.append(" ORDER BY f.type, f.name ");

        TypedQuery<FileView> query = em.createQuery(builder.toString(), FileView.class)
                .setParameter("userId", userId)
                .setParameter("fileParentId", fileParentId);

        if (beginUpdateDate != null)
            query.setParameter("updated", beginUpdateDate);

        return query.getResultList();
    }
}