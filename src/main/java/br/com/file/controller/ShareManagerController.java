package br.com.file.controller;

import br.com.file.domain.FileShare;
import br.com.file.model.request.ShareRequest;
import br.com.file.model.respose.FileSharedResponse;
import br.com.file.service.ShareManagerService;
import br.com.spacebox.common.model.response.UserResponse;
import br.com.spacebox.common.service.security.PrincipalToken;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/share")
@Api(tags = "share")
public class ShareManagerController {

    @Autowired
    private ShareManagerService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/users")
    @ApiOperation(value = "Returns list of users for share files", response = UserResponse[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
    })
    public UserResponse[] search(PrincipalToken token, String name) {
        return modelMapper.map(service.listUsers(token.getUserDetailsAuth(), name), UserResponse[].class);
    }

    @PostMapping("/")
    @ApiOperation(value = "Share file with users.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
    })
    public void share(PrincipalToken token, @ApiParam("Share") @RequestBody ShareRequest request) {
        service.share(token.getUserDetailsAuth(), request.getFileId(), request.getUserId());
    }

    @PostMapping("/unshare")
    @ApiOperation(value = "Unshare file with users.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
    })
    public void unshare(PrincipalToken token, @ApiParam("Share") @RequestBody ShareRequest request) {
        service.unshare(token.getUserDetailsAuth(), request.getFileId(), request.getUserId());
    }

    @GetMapping("/")
    @ApiOperation(value = "List users available for share folder.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
    })
    public FileSharedResponse[] list(PrincipalToken token, @ApiParam("FileId") Long fileId) {
        List<FileShare> sharedList = service.list(token.getUserDetailsAuth(), fileId);
        FileSharedResponse[] result = new FileSharedResponse[sharedList.size()];

        if (sharedList.size() > 0) {
            int index = 0;
            for (FileShare fileShare : sharedList) {
                result[index++] = new FileSharedResponse(fileShare.getId(), fileShare.getUser().getUsername(), fileShare.getUserId());
            }
        }

        return result;
    }
}
