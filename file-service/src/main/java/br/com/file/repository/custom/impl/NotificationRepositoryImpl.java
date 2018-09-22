package br.com.file.repository.custom.impl;

import br.com.file.domain.view.NotificationView;
import br.com.file.repository.custom.NotificationCustomRepository;
import br.com.file.utils.ParseUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationRepositoryImpl implements NotificationCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<NotificationView> list(Long userId, Date beginUpdateDate, Long fileParentId) {
        Query query = em.createNativeQuery("SELECT " +
                " n.id as nId," +
                " n.type as nType," +
                " n.file_name," +
                " n.user_id as nUserId," +
                " n.created as nCreated," +
                " f.id as fId," +
                " f.name as fName," +
                " f.type as fType," +
                " f.size as fSize," +
                " f.file_parent_id," +
                " f.created as fCreated," +
                " f.updated as fUpdated " +
                "FROM tb_notification n " +
                "LEFT JOIN tb_file f ON n.file_id = f.id " +
                "WHERE " +
                " n.user_id = :userId AND " +
                " (:created IS NULL OR n.created > :created) AND " +
                " (:fileParentId IS NULL OR f.file_parent_id = :fileParentId) " +
                "ORDER BY n.created DESC");

        query.setParameter("userId", userId);
        query.setParameter("fileParentId", fileParentId);
        query.setParameter("created", beginUpdateDate);

        List<NotificationView> result = new ArrayList<>();
        List<Object[]> resultsGeneric = query.getResultList();

        if (resultsGeneric != null) {
            NotificationView.Builder builder = new NotificationView.Builder();
            for (Object[] entity : resultsGeneric) {
                result.add(builder
                        .withNId(ParseUtils.parseLong(entity[0]))
                        .withNType(ParseUtils.parseInteger(entity[1]))
                        .withNFileName(ParseUtils.parseString(entity[2]))
                        .withNUserId(ParseUtils.parseLong(entity[3]))
                        .withNCreated(ParseUtils.parseDate(entity[4]))
                        .withFileId(ParseUtils.parseLong(entity[5]))
                        .withFName(ParseUtils.parseString(entity[6]))
                        .withFType(ParseUtils.parseString(entity[7]))
                        .withFSize(ParseUtils.parseLong(entity[8]))
                        .withFileParentId(ParseUtils.parseLong(entity[9]))
                        .withFCreated(ParseUtils.parseDate(entity[10]))
                        .withFUpdated(ParseUtils.parseDate(entity[11]))
                        .build());
            }
        }

        return result;
    }
}