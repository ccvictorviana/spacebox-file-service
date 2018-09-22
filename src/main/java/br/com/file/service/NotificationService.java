package br.com.file.service;

import br.com.file.domain.Notification;
import br.com.file.domain.filter.NotificationFilter;
import br.com.file.domain.view.NotificationView;
import br.com.spacebox.common.service.security.UserDetailsAuth;

import java.util.List;

public interface NotificationService {

    Notification create(Notification file);

    List<NotificationView> list(UserDetailsAuth userDetailsAuth, NotificationFilter filter);
}