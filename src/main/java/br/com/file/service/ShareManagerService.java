package br.com.file.service;

import br.com.file.domain.FileShare;
import br.com.file.domain.view.UserShareView;
import br.com.spacebox.common.service.security.UserDetailsAuth;

import java.util.List;

public interface ShareManagerService {

    List<FileShare> list(UserDetailsAuth userDetailsAuth, Long fileId);

    List<UserShareView> listUsers(UserDetailsAuth userDetailsAuth, String userName);

    void share(UserDetailsAuth userDetailsAuth, Long fileId, Long userId);

    void unshare(UserDetailsAuth userDetailsAuth, Long fileId, Long userId);

}