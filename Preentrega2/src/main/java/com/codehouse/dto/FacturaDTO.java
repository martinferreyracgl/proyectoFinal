package com.codehouse.dto;

import java.util.Date;

public class FacturaDTO {
	
	 private int id;
	    private Date createdAt;
	    private double total;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public Date getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}
		public double getTotal() {
			return total;
		}
		public void setTotal(double total) {
			this.total = total;
		}

}
