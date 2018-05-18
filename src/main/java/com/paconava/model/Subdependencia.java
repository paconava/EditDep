package net.codejava.spring.model;

public class Subdependencia {
	private int clave_ds;
	private int clave_s;
	private int clave_comp;
	private int status;
	private int tipo_desc;
	private String desc;
	

	public Subdependencia() {
	}

	public Subdependencia(int clave_ds, int clave_s, int clave_comp, int status, int tipo_desc, String desc) {
		this.clave_ds = clave_ds;
		this.clave_s = clave_s;
		this.clave_comp = clave_comp;
		this.status = status;
		this.tipo_desc = tipo_desc;
		this.desc = desc;
	}

	public int getClave_ds() {
		return clave_ds;
	}

	public void setClave_ds(int clave_ds) {
		this.clave_ds = clave_ds;
	}

	public int getClave_s() {
		return clave_s;
	}

	public void setClave_s(int clave_s) {
		this.clave_s = clave_s;
	}

	public int getClave_comp() {
		return clave_comp;
	}

	public void setClave_comp(int clave_comp) {
		this.clave_comp = clave_comp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTipo_desc() {
		return tipo_desc;
	}

	public void setTipo_desc(int tipo_desc) {
		this.tipo_desc = tipo_desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
