package com.danilo.projetocomspring.entities;

import java.util.Date;

public class Payment {
  private long id;
  private Date moment;

  private Order order;

  public Payment() {
  }

  public Payment(long id, Date moment, Order order) {
    this.id = id;
    this.moment = moment;
    this.order = order;
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

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }
}
