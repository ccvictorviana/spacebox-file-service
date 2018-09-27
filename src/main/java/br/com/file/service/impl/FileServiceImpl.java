package br.com.file.service.impl;

import br.com.file.domain.File;
import br.com.file.domain.Notification;
import br.com.file.domain.enums.ENotificationType;
import br.com.file.domain.filter.FileFilter;
import br.com.file.domain.view.FileView;
import br.com.file.repository.FileRepository;
import br.com.file.service.FileService;
import br.com.file.service.NotificationService;
import br.com.spacebox.common.exceptions.BusinessException;
import br.com.spacebox.common.messages.EMessage;
import br.com.spacebox.common.service.AEntityService;
import br.com.spacebox.common.service.ValidationType;
import br.com.spacebox.common.validation.FluentValidationLong;
import br.com.spacebox.common.validation.FluentValidationString;
import br.com.spacebox.common.service.security.UserDetailsAuth;
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
    private NotificationService notificationService;

    @Transactional
    @Override
    public File create(UserDetailsAuth userDetailsAuth, File file) {
        file.setUserId(userDetailsAuth.getId());
        this.validate(ValidationType.CREATE, file);
        repository.save(file);
        generateNotification(ENotificationType.CRAETE, file);
        return file;
    }

    @Transactional
    @Override
    public void update(UserDetailsAuth userDetailsAuth, File file) {
        this.validate(ValidationType.UPDATE, file);
        file.setUserId(userDetailsAuth.getId());
        repository.save(file);
        generateNotification(ENotificationType.UPDATE, file);
    }

    @Override
    public List<FileView> list(UserDetailsAuth userDetailsAuth, FileFilter filter) {
        return repository.list(userDetailsAuth.getId(), filter.getBeginUpdateDate(), filter.getFileParentId());
    }

    @Transactional
    @Override
    public void delete(UserDetailsAuth userDetailsAuth, Long fileId) {
        File file = repository.findByIdAndUserId(fileId, userDetailsAuth.getId());

        if (file == null)
            throw new BusinessException(getMessage(EMessage.FILE_NOT_FOUND));

        repository.delete(fileId);
        generateNotification(ENotificationType.DELETE, file);
    }

    @Override
    public File detail(UserDetailsAuth userDetailsAuth, Long fileId) {
        File file = repository.findByIdAndUserId(fileId, userDetailsAuth.getId());

        if (file == null)
            throw new BusinessException(getMessage(EMessage.FILE_NOT_FOUND));

        return file;
    }

    @Override
    public File findFile(UserDetailsAuth userDetailsAuth, Long fileId) {
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
        Notification fn = new Notification();

        fn.setCreated(file.getUpdated());
        fn.setFileId(file.getId());
        fn.setFileName(file.getName());
        fn.setType(type.getType());
        fn.setUserId(file.getUserId());

        notificationService.create(fn);
    }
}
