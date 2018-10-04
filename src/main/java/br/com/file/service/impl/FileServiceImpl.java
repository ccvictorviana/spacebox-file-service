package br.com.file.service.impl;

import br.com.file.domain.File;
import br.com.file.domain.FileShare;
import br.com.file.domain.Notification;
import br.com.file.domain.enums.ENotificationType;
import br.com.file.domain.filter.FileFilter;
import br.com.file.domain.view.FileView;
import br.com.file.repository.FileRepository;
import br.com.file.repository.FileShareRepository;
import br.com.file.service.FileService;
import br.com.file.service.NotificationService;
import br.com.spacebox.common.exceptions.BusinessException;
import br.com.spacebox.common.messages.EMessage;
import br.com.spacebox.common.service.AEntityService;
import br.com.spacebox.common.service.ValidationType;
import br.com.spacebox.common.service.security.UserDetailsAuth;
import br.com.spacebox.common.validation.FluentValidationLong;
import br.com.spacebox.common.validation.FluentValidationString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileServiceImpl extends AEntityService<File> implements FileService {

    @Autowired
    private FileRepository repository;

    @Autowired
    private FileShareRepository fileShareRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    @Override
    public File create(UserDetailsAuth userDetailsAuth, File file) {
        File fileParent = null;
        boolean folderShared = false;

        if (file.getFileParentId() != null) {
            fileParent = repository.findFile(file.getFileParentId());
            folderShared = fileParent.getUserId() != userDetailsAuth.getId();
        }

        file.setUserId((fileParent != null) ? fileParent.getUserId() : userDetailsAuth.getId());
        validate(ValidationType.CREATE, file);
        repository.save(file);

        if (folderShared)
            generateNotification(ENotificationType.SHARE_CREATE, file, userDetailsAuth.getId());

        generateNotification(ENotificationType.CREATE, file);

        return file;
    }

    @Transactional
    @Override
    public void update(UserDetailsAuth userDetailsAuth, File file) {
        boolean isOwner;

        File fileDb = repository.findFile(file.getId());
        if (fileDb == null)
            throw new BusinessException(getMessage(EMessage.FILE_NOT_FOUND));

        hasAccess(userDetailsAuth, file.getFileParentId());
        isOwner = file.getUserId().equals(userDetailsAuth.getId());

        file.setUserId(fileDb.getUserId());
        validate(ValidationType.UPDATE, file);
        repository.save(file);

        if (!isOwner)
            generateNotification(ENotificationType.SHARE_UPDATE, file, userDetailsAuth.getId());

        generateNotification(ENotificationType.UPDATE, file);
    }

    @Transactional
    @Override
    public void delete(UserDetailsAuth userDetailsAuth, Long fileId) {
        boolean isOwner;

        File file = repository.findFile(fileId);
        if (file == null)
            throw new BusinessException(getMessage(EMessage.FILE_NOT_FOUND));

        hasAccess(userDetailsAuth, file.getFileParentId());
        isOwner = file.getUserId().equals(userDetailsAuth.getId());

        if (!isOwner)
            generateNotification(ENotificationType.SHARE_DELETE, file, userDetailsAuth.getId());

        generateNotification(ENotificationType.DELETE, file);

        List<FileShare> filesShare = fileShareRepository.findAllByFileId(fileId);
        for (FileShare fileShare : filesShare) {
            generateNotification(ENotificationType.UNSHARE_WITH, file, fileShare.getUserId());
            generateNotification(ENotificationType.UNSHARE, file);
        }

        fileShareRepository.delete(fileId);
        repository.delete(fileId);
    }

    @Override
    public List<FileView> list(UserDetailsAuth userDetailsAuth, FileFilter filter) {
        List<FileView> files = repository.list(userDetailsAuth.getId(), filter.getBeginUpdateDate(), filter.getFileParentId());

        if (filter.getFileParentId() == null)
            files.addAll(repository.listFoldersShared(userDetailsAuth.getId(), filter.getBeginUpdateDate(), filter.getFileParentId()));
        else if (files.size() == 0) {
            hasAccess(userDetailsAuth, filter.getFileParentId());
            files.addAll(repository.list(null, filter.getBeginUpdateDate(), filter.getFileParentId()));
        }

        return files;
    }

    @Override
    public File detail(UserDetailsAuth userDetailsAuth, Long fileId) {
        File file = repository.findFile(fileId, userDetailsAuth.getId());

        if (file == null)
            throw new BusinessException(getMessage(EMessage.FILE_NOT_FOUND));

        return file;
    }

    @Override
    public void rename(UserDetailsAuth userDetailsAuth, Long fileId, String name) {
        List<String> errors = new ArrayList<>();

        FluentValidationLong.notNullAndEmpty().test(fileId).addMessage(getMessage(EMessage.REQUIRED_FIELD_ID), errors);
        FluentValidationString.notNullAndEmpty().test(name).addMessage(getMessage(EMessage.REQUIRED_FIELD_NAME), errors);

        if (errors != null && !errors.isEmpty())
            throw new BusinessException(errors);

        repository.rename(userDetailsAuth.getId(), fileId, name);
    }

    @Override
    protected void onValidate(ValidationType validationType, File file, List<String> errors) {
        File fileDB;

        FluentValidationString.notNullAndEmpty().test(file.getName()).addMessage(getMessage(EMessage.REQUIRED_FIELD_NAME), errors);

        if (file.getFileParentId() != null) {
            fileDB = repository.findByIdAndUserId(file.getFileParentId(), file.getUserId());
            if (fileDB == null) {
                errors.add(getMessage(EMessage.FOLDERPARENT_NOT_FOUND));
            } else if (fileDB.getType() != null) {
                errors.add(getMessage(EMessage.REQUIRED_PARENTFILEFOLDER));
            }
        }

        if (validationType == ValidationType.UPDATE) {
            FluentValidationLong.notNullAndEmpty().test(file.getId()).addMessage(getMessage(EMessage.REQUIRED_FIELD_ID), errors);
        }

        if (file.getName() != null) {
            fileDB = repository.findByNameAndFileParentId(file.getName(), file.getFileParentId());
            if (fileDB != null && !fileDB.getId().equals(file.getId())) {
                errors.add(getMessage(EMessage.ALREADYEXISTS_NAME));
            }
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

    private void hasAccess(UserDetailsAuth userDetailsAuth, Long fileParentId) {
    }
}
