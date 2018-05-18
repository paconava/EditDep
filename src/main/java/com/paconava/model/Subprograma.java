package net.codejava.spring.model;

public class Subprograma {
	private Integer clave_dsp;
	private Integer clave_sdsp;
	private Integer clave_psp;
	private Integer clave_sp;
	private String desc;
	private Integer clave_comp;
	private Integer status;

	public Subprograma() {
	}

	public Subprograma(Integer clave_dsp, Integer clave_sdsp, Integer clave_psp, Integer clave_sp, String desc, Integer clave_comp, Integer status) {
		this.clave_dsp = clave_dsp;
		this.clave_sdsp = clave_sdsp;
		this.clave_psp = clave_psp;
		this.clave_sp = clave_sp;
		this.desc = desc;
		this.clave_comp = clave_comp;
		this.status = status;
	}

	public Integer getClave_dsp() {
		return clave_dsp;
	}

	public void setClave_dsp(Integer clave_dsp) {
		this.clave_dsp = clave_dsp;
	}

	public Integer getClave_sdsp() {
		return clave_sdsp;
	}

	public void setClave_sdsp(Integer clave_sdsp) {
		this.clave_sdsp = clave_sdsp;
	}

	public Integer getClave_psp() {
		return clave_psp;
	}

	public void setClave_psp(Integer clave_psp) {
		this.clave_psp = clave_psp;
	}

	public Integer getClave_sp() {
		return clave_sp;
	}

	public void setClave_sp(Integer clave_sp) {
		this.clave_sp = clave_sp;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
