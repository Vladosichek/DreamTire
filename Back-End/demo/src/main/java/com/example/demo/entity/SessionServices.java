package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sessions_services")
public class SessionServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    public SessionServices(Session session, Service service, Long quantity){
        this.session = session;
        this.service = service;
        this.quantity = quantity;
    }

    public SessionServices() {}

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Session getSession(){
        return session;
    }

    public void setSession(Session session) {
        this.session =session;
    }

    public Service getService(){
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Long getQuantity(){
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}