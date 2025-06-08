package com.example.demo.dto;

public class SessionServiceItemDTO {

    private Long serviceId;
    private String serviceName;
    private Long quantity;

    public SessionServiceItemDTO() {
    }

    public SessionServiceItemDTO(Long serviceId, String serviceName, Long quantity) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.quantity = quantity;
    }

    public SessionServiceItemDTO(Long serviceId, Long quantity) {
        this.serviceId = serviceId;
        this.quantity = quantity;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}


