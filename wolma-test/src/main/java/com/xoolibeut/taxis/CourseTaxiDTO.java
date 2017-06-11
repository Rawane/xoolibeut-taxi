package com.xoolibeut.taxis;

public class CourseTaxiDTO {
	/**
	 * Numéro de taxi.
	 */
	private String c;
	/**
	 * course id
	 */
	private Long i;
	/**
	 * date de course.
	 */
	private Long e;

	private double a;

	private double o;
	/**
	 * ip
	 */
	private String ip;
	/**
	 * port
	 */
	private String p;
	/**
	 * signature
	 */
	private String s;
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	public Long getI() {
		return i;
	}
	public void setI(Long i) {
		this.i = i;
	}
	public Long getE() {
		return e;
	}
	public void setE(Long e) {
		this.e = e;
	}
	public double getA() {
		return a;
	}
	public void setA(double a) {
		this.a = a;
	}
	public double getO() {
		return o;
	}
	public void setO(double o) {
		this.o = o;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}

}
