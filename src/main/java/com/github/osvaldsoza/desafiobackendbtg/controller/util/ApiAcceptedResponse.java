package com.github.osvaldsoza.desafiobackendbtg.controller.util;

public class ApiAcceptedResponse {

    private String message;
    private String trackingId;

    public ApiAcceptedResponse(String message, String trackingId) {
        this.message = message;
        this.trackingId = trackingId;
    }

    public static ApiAcceptedResponse of(String message, String trackingId){
        return new ApiAcceptedResponse(message,trackingId);
    }

    public String getMessage() {
        return message;
    }

    public String getTrackingId() {
        return trackingId;
    }
}
