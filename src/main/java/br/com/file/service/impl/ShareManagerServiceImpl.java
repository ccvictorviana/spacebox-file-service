package br.com.file.service.impl;

import br.com.file.domain.File;
import br.com.file.domain.FileShare;
import br.com.file.domain.Notification;
import br.com.file.domain.enums.ENotificationType;
import br.com.file.domain.view.UserShareView;
import br.com.file.repository.FileRepository;
import br.com.file.repository.FileShareRepository;
import br.com.file.service.NotificationService;
import br.com.file.service.ShareManagerService;
import br.com.spacebox.common.exceptions.BusinessException;
import br.com.spacebox.common.messages.EMessage;
import br.com.spacebox.common.service.AEntityService;
import br.com.spacebox.common.service.ValidationType;
import br.com.spacebox.common.service.security.UserDetailsAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShareManagerServiceImpl extends AEntityService<FileShare> implements ShareManagerService {

    @Autowired
    private FileRepository repository;

    @Autowired
    private FileShareRepository fileShareRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public List<UserShareView> listUsers(UserDetailsAuth userDetailsAuth, String userName) {
        return fileShareRepository.listUsers(userDetailsAuth.getId(), userName);
    }

    @Override
    public List<FileShare> list(UserDetailsAuth userDetailsAuth, Long fileId) {
        File file = repository.findFile(fileId, userDetailsAuth.getId());

        if (file == null)
            throw new BusinessException(getMessage(EMessage.FILE_NOT_FOUND));

        return fileShareRepository.findAllByFileId(fileId);
    }

    @Override
    public void share(UserDetailsAuth userDetailsAuth, Long fileId, Long userId) {
        File file = repository.findByIdAndUserId(fileId, userDetailsAuth.getId());

        if (file == null)
            throw new BusinessException(getMessage(EMessage.FILE_NOT_FOUND));

        if (userId == null)
            throw new BusinessException(getMessage(EMessage.USER_NOT_FOUND));

        fileShareRepository.save(new FileShare(fileId, userId));
        generateNotification(ENotificationType.SHARE_WITH, file, userId);
        generateNotification(ENotificationType.SHARE, file);
    }

    @Override
    public void unshare(UserDetailsAuth userDetailsAuth, Long fileId, Long userId) {
        File file = repository.findByIdAndUserId(fileId, userDetailsAuth.getId());

        if (userId != null) {
            fileShareRepository.delete(fileId, userId);
            generateNotification(ENotificationType.UNSHARE_WITH, file, userId);
            generateNotification(ENotificationType.UNSHARE, file);
        }
    }

    private void generateNotification(ENotificationType type, File file) {
        generateNotification(type, file, file.getUserId());
    }

    private void generateNotification(ENotificationType type, File file, Long userId) {
        Notification fn = new Notification();

        fn.setCreated(file.getUpdated());
        fn.setFileId(file.getId());
        fn.setFileName(file.getName());
        fn.setType(type);
        fn.setUserActionId(userId);
        fn.setUserOwnerId(userId);

        notificationService.create(fn);
    }

    @Override
    protected void onValidate(ValidationType validationType, FileShare fileShare, List<String> list) {

    }

    private void hasAccess(UserDetailsAuth userDetailsAuth, Long fileParentId) {
    }

}
