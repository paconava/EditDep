package com.paconava.model;

public class Programa {
	private Integer clave_dp;
	private Integer clave_sdp;
	private Integer clave_p;
	private String desc;
	private String subdepen;
	private Integer clave_comp;

	public Programa() {
	}

	public Programa(Integer clave_dp, Integer clave_sdp, Integer clave_p, String desc, Integer clave_comp, String subdepen) {
		this.clave_dp = clave_dp;
		this.clave_sdp = clave_sdp;
		this.clave_p = clave_p;
		this.desc = desc;
		this.clave_comp = clave_comp;
		this.subdepen = subdepen;
	}

	public Integer getClave_dp() {
		return clave_dp;
	}

	public void setClave_dp(Integer clave_dp) {
		this.clave_dp = clave_dp;
	}

	public Integer getClave_sdp() {
		return clave_sdp;
	}

	public void setClave_sdp(Integer clave_sdp) {
		this.clave_sdp = clave_sdp;
	}

	public Integer getClave_p() {
		return clave_p;
	}

	public void setClave_p(Integer clave_p) {
		this.clave_p = clave_p;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getClave_comp() {
		return clave_comp;
	}

	public void setClave_comp(Integer clave_comp) {
		this.clave_comp = clave_comp;
	}
	
	public String getSubdepen() {
		return subdepen;
	}

	public void setSubdepen(String subdepen) {
		this.subdepen = subdepen;
	}

}
