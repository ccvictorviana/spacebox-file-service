package br.com.file.service;

import br.com.file.domain.File;
import br.com.file.domain.filter.FileFilter;
import br.com.file.domain.view.FileView;
import br.com.spacebox.common.service.security.UserDetailsAuth;

import java.util.List;

public interface FileService {
    File save(UserDetailsAuth userDetailsAuth, File file, byte[] content);

    void delete(UserDetailsAuth userDetailsAuth, Long fileId);

    File detail(UserDetailsAuth userDetailsAuth, Long fileId);

    void rename(UserDetailsAuth userDetailsAuth, Long fileId, String name);

    List<FileView> list(UserDetailsAuth userDetailsAuth, FileFilter filter);
}