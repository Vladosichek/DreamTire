package com.example.demo.dto;

public class SessionServiceRequestDTO {
    private Long  sessionId;
    private Long serviceId;
    private Long quantity;

    public SessionServiceRequestDTO(Long sessionId, Long serviceId, Long quantity){
        this.sessionId = sessionId;
        this.serviceId = serviceId;
        this.quantity = quantity;
    }

    public SessionServiceRequestDTO() {}

    public Long getSessionId(){
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId =sessionId;
    }

    public Long getServiceId(){
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getQuantity(){
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
