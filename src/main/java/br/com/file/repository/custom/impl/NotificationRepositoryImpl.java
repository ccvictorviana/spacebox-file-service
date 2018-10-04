package br.com.file.repository.custom.impl;

import br.com.file.domain.view.NotificationView;
import br.com.file.repository.custom.NotificationCustomRepository;
import br.com.file.utils.ParseUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
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
                " n.user_owner_id as userOwnerId," +
                " n.user_action_id as userActionId," +
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
                " n.user_owner_id = :userId AND " +
                " (:created IS NULL OR n.created > :created) AND " +
                " (:fileParentId = 0 OR f.file_parent_id = :fileParentId) " +
                "ORDER BY n.created DESC");

        query.setParameter("userId", userId)
                .setParameter("fileParentId", (fileParentId == null) ? new Long(0) : fileParentId)
                .setParameter("created", beginUpdateDate, TemporalType.TIMESTAMP);

        List<NotificationView> result = new ArrayList<>();
        List<Object[]> resultsGeneric = query.getResultList();

        if (resultsGeneric != null) {
            NotificationView.Builder builder = new NotificationView.Builder();
            for (Object[] entity : resultsGeneric) {
                result.add(builder
                        .withNId(ParseUtils.parseLong(entity[0]))
                        .withNType(ParseUtils.parseInteger(entity[1]))
                        .withNFileName(ParseUtils.parseString(entity[2]))
                        .withUserOwnerId(ParseUtils.parseLong(entity[3]))
                        .withUserActionId(ParseUtils.parseLong(entity[4]))
                        .withNCreated(ParseUtils.parseDate(entity[5]))
                        .withFileId(ParseUtils.parseLong(entity[6]))
                        .withFName(ParseUtils.parseString(entity[7]))
                        .withFType(ParseUtils.parseString(entity[8]))
                        .withFSize(ParseUtils.parseLong(entity[9]))
                        .withFileParentId(ParseUtils.parseLong(entity[10]))
                        .withFCreated(ParseUtils.parseDate(entity[11]))
                        .withFUpdated(ParseUtils.parseDate(entity[12]))
                        .build());
            }
        }

        return result;
    }
}