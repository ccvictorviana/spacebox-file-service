package br.com.file.controller;

import br.com.file.domain.File;
import br.com.file.domain.filter.FileFilter;
import br.com.file.domain.view.FileView;
import br.com.file.model.request.FileFilterRequest;
import br.com.file.model.request.FileRenameRequest;
import br.com.file.model.request.FileRequest;
import br.com.file.model.respose.FileSummaryResponse;
import br.com.file.model.respose.FilesResponse;
import br.com.file.service.FileService;
import br.com.spacebox.common.model.response.UserResponse;
import br.com.spacebox.common.service.security.PrincipalToken;
import io.swagger.annotations.*;
import org.apache.commons.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/files")
@Api(tags = "files")
@CrossOrigin
public class FileController {
    @Autowired
    private FileService fileService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/")
    @ApiOperation(value = "Create file.")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
    })
    public FileSummaryResponse create(PrincipalToken token, @ApiParam("File") @RequestBody FileRequest request) {
        return modelMapper.map(fileService.create(token.getUserDetailsAuth(), modelMapper.map(request, File.class)), FileSummaryResponse.class);
    }

    @DeleteMapping(value = "/")
    @ApiOperation(value = "Delete file")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The file doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
    })
    public void delete(PrincipalToken token, Long fileId) {
        fileService.delete(token.getUserDetailsAuth(), fileId);
    }

    @PatchMapping(value = "/")
    @ApiOperation(value = "Update file")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The file doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
    })
    public void update(PrincipalToken token, @ApiParam("Update File") @RequestBody FileRequest request) {
        fileService.update(token.getUserDetailsAuth(), modelMapper.map(request, File.class));
    }

    @PatchMapping(value = "/rename")
    @ApiOperation(value = "Rename file name")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The file doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
    })
    public void rename(PrincipalToken token, @ApiParam("File name") @RequestBody FileRenameRequest request) {
        fileService.rename(token.getUserDetailsAuth(), request.getId(), request.getName());
    }

    @PostMapping(value = "/list")
    @ApiOperation(value = "Returns list of files", response = UserResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
    })
    public FilesResponse list(PrincipalToken token, @RequestBody FileFilterRequest request) {
        List<FileView> files = fileService.list(token.getUserDetailsAuth(), modelMapper.map(request, FileFilter.class));

        FilesResponse response = new FilesResponse();
        response.setFilter(request);
        response.setFiles(modelMapper.map(files != null ? files : new FileView[0], FileSummaryResponse[].class));
        return response;
    }

    @GetMapping(value = "/download")
    @ApiOperation(value = "Return file detail with content", response = UserResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
    })
    public ResponseEntity<Resource> download(PrincipalToken token, Long fileId) {
        File fileData = fileService.detail(token.getUserDetailsAuth(), fileId);
        ByteArrayResource resource = new ByteArrayResource(Base64.decodeBase64(fileData.getContent()));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileData.getName())
                .contentLength(fileData.getSize())
                .contentType(MediaType.parseMediaType(fileData.getType()))
                .body(resource);
    }
}
