package com.example.demo.dto;

public class SessionServiceResponseDTO {
    private Long id;
    private Long  sessionId;
    private Long serviceId;
    private Long quantity;

    public SessionServiceResponseDTO(Long id, Long sessionId, Long serviceId, Long quantity){
        this.id = id;
        this.sessionId = sessionId;
        this.serviceId = serviceId;
        this.quantity = quantity;
    }

    public SessionServiceResponseDTO() {}

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
