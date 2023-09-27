package com.danilo.projetocomspring.entities;

import java.util.Date;

public class Order {
  private long id;
  private Date moment;
  private OrderStatus orderStatus;

  private User client;

  public Order() {
  }

  public Order(long id, Date moment, OrderStatus orderStatus, User client) {
    this.id = id;
    this.moment = moment;
    this.orderStatus = orderStatus;
    this.client = client;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Date getMoment() {
    return moment;
  }

  public void setMoment(Date moment) {
    this.moment = moment;
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  public User getClient() {
    return client;
  }

  public void setClient(User client) {
    this.client = client;
  }
}
