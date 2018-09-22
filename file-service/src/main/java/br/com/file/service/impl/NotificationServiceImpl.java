package br.com.file.service.impl;

import br.com.file.domain.Notification;
import br.com.file.domain.filter.NotificationFilter;
import br.com.file.domain.view.NotificationView;
import br.com.file.repository.NotificationRepository;
import br.com.file.service.NotificationService;
import br.com.spacebox.common.service.AEntityService;
import br.com.spacebox.common.service.ValidationType;
import br.com.spacebox.common.service.security.UserDetailsAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationServiceImpl extends AEntityService<Notification> implements NotificationService {

    @Autowired
    private NotificationRepository repository;

    @Override
    public Notification create(Notification notification) {
        this.validate(ValidationType.CREATE, notification);
        repository.save(notification);
        return notification;
    }

    @Override
    public List<NotificationView> list(UserDetailsAuth userDetailsAuth, NotificationFilter filter) {
        return repository.list(userDetailsAuth.getId(), filter.getBeginUpdateDate(), filter.getFileParentId());
    }


    @Override
    protected void onValidate(ValidationType validationType, Notification notification, List<String> list) {

    }
}
