package br.com.file.model.respose;

import br.com.file.model.request.NotificationFilterRequest;
import io.swagger.annotations.ApiModelProperty;

public class NotificationsResponse {

    @ApiModelProperty(position = 0)
    private NotificationFilterRequest filter;

    @ApiModelProperty(position = 1)
    private NotificationResponse[] notifications;

    public NotificationFilterRequest getFilter() {
        return filter;
    }

    public void setFilter(NotificationFilterRequest filter) {
        this.filter = filter;
    }

    public NotificationResponse[] getNotifications() {
        return notifications;
    }

    public void setNotifications(NotificationResponse[] notifications) {
        this.notifications = notifications;
    }

}
