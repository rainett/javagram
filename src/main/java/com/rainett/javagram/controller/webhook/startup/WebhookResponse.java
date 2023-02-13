package com.rainett.javagram.controller.webhook.startup;

import lombok.Data;

@Data
public class WebhookResponse {

    private boolean isOk;
    private boolean result;
    private String description;

}
