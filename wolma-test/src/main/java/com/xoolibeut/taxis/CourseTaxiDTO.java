package com.xoolibeut.taxis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CourseTaxiDTO {
	/**
	 * Numéro de taxi.
	 */
	private String c;
	/**
	 * course id
	 */
	private String i;
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
	 * téléphone
	 */
	private String h;
	/**
	 * type
	 */
	private String t;
	/**
	 * signature
	 */
	private String s;
	
	private String m;
	
	private String tt;
	
	private String ty;
	private String min;
	private String max;
	private String ci;
	private String ta;

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
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

	public String getH() {
		return h;
	}

	public void setH(String h) {
		this.h = h;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public String getTt() {
		return tt;
	}

	public void setTt(String tt) {
		this.tt = tt;
	}

	public String getTy() {
		return ty;
	}

	public void setTy(String ty) {
		this.ty = ty;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public String getI() {
		return i;
	}

	public void setI(String i) {
		this.i = i;
	}

	public String getTa() {
		return ta;
	}

	public void setTa(String ta) {
		this.ta = ta;
	}

	

}
