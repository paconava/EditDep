package net.codejava.spring.dao;

import java.util.List;

import net.codejava.spring.model.Subdependencia;
import net.codejava.spring.model.Subprograma;
import net.codejava.spring.model.Dependencia;
import net.codejava.spring.model.Programa;

/**
 * Defines DAO operations for the model.
 * @author Francisco Nava
 *
 */
public interface DepenDAO {
	
	int concat2int(int a, int b);
	
	int concat3int(int a, int b, int c);
	
	int concat4int(int a, int b, int c, int d);
	
	public void saveOrUpdate(Dependencia depen, int depenId);
	
	public void saveOrUpdateSd(Subdependencia depen, int depenId);
	
	public void saveOrUpdateProg(Programa prog, int depenId);
	
	public void saveOrUpdateSubp(Subprograma subprog, int depenId);
	
	public void newDependencia(Dependencia depen);
	
	public void insertDepen(Dependencia depen);
	
	public void insertSubdepen(Subdependencia subdepen);
	
	public void insertProg(Programa prog);
	
	public void insertSubProg(Subprograma subprog);
	
	public void deleteDepen(int depenId);
	
	public void deleteSubdepen(int depenId);
	
	public void deleteProg(int progId);
	
	public void deleteSubProg(int subProgId);
	
	public String subD(int depenId);
	
	public String nomD(int depenId);
	
	public String progP(int depenId);
	
	public Subdependencia get(int depenId);
	
	public Dependencia getD(int depenId);
	
	public Programa getP(int depenId);
	
	public Subprograma getSp(int depenId);
	
	public List<Subdependencia> listSd(int depen);
	
	public List<Dependencia> listD();
	
	public List<Programa> listP(int cve);
	
	public List<Subprograma> listSp(int cve);
	
}
