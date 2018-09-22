package br.com.file.repository;

import br.com.file.domain.Notification;
import br.com.file.repository.custom.NotificationCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationCustomRepository {

}