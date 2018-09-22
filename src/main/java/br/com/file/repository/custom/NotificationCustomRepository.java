package br.com.file.repository.custom;

import br.com.file.domain.view.NotificationView;

import java.util.Date;
import java.util.List;

public interface NotificationCustomRepository {

    List<NotificationView> list(Long userId, Date beginUpdateDate, Long fileParentId);
}
