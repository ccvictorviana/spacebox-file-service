package br.com.file.controller;

import br.com.file.domain.filter.NotificationFilter;
import br.com.file.domain.view.NotificationView;
import br.com.file.model.request.NotificationFilterRequest;
import br.com.file.model.respose.NotificationResponse;
import br.com.file.model.respose.NotificationsResponse;
import br.com.file.service.NotificationService;
import br.com.spacebox.common.service.security.PrincipalToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@Api(tags = "notifications")
@CrossOrigin
public class NotificationController {
    @Autowired
    private NotificationService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/")
    @ApiOperation(value = "Returns list of notifications", response = NotificationsResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")
    })
    public NotificationsResponse list(PrincipalToken token, @RequestBody NotificationFilterRequest request) {
        List<NotificationView> notifications = service.list(token.getUserDetailsAuth(), modelMapper.map(request, NotificationFilter.class));

        NotificationsResponse response = new NotificationsResponse();
        response.setFilter(request);
        response.setNotifications(modelMapper.map(notifications != null ? notifications : new NotificationView[0], NotificationResponse[].class));

        return response;
    }
}
