package com.paconava.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import com.paconava.model.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

/**
 * An implementation of the DepenDAO interface
 * @author Francisco Nava
 *
 */
public class DepenDAOImpl implements DepenDAO {

	private JdbcTemplate jdbcTemplate;
	
	public DepenDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	//Métodos para concatenar
	public int concat2int(int a, int b) {
		a = a * 100;
		return (a+b);
	}
	
	public int concat3int(int a, int b, int c) {
		a = a * 10000;
		b = b * 100;
		return (a+b+c);
	}
	
	public int concat4int(int a, int b, int c, int d) {
		a = a * 1000000;
		b = b * 10000;
		c = c * 100;
		return (a+b+c+d);
	}
	
	// Métodos para guardar
	@Override
	public void saveOrUpdate(Dependencia depen, int depenId) {
		int cve = depen.getId();
		int ccsp = (depenId-cve)*100;
		String sql = "UPDATE Dependencia SET ClaveDependencia=?, DescripcionDependencia=? WHERE ClaveDependencia=?";
		jdbcTemplate.update(sql, cve, depen.getName(), depenId);
		sql = "UPDATE Subdependencia SET ClaveDependenciaSubdependencia=?, ClaveCompuestaSubdependencia=(ClaveCompuestaSubdependencia - ?) WHERE ClaveDependenciaSubdependencia=?";	
		jdbcTemplate.update(sql, cve, ccsp, depenId);
		ccsp = ccsp * 100;
		sql = "UPDATE Programa SET ClaveDependenciaPrograma=?, ClaveCompuestaPrograma=(ClaveCompuestaPrograma - ?) WHERE ClaveDependenciaPrograma=?";	
		jdbcTemplate.update(sql, cve, ccsp, depenId);
		ccsp = ccsp * 100;
		sql = "UPDATE Subprograma SET ClaveDependenciaSubprograma=?, " 
		+ "ClaveCompuestaSubprograma=(ClaveCompuestaSubprograma - ?) WHERE ClaveDependenciaSubprograma=?";	
		jdbcTemplate.update(sql, cve, ccsp, depenId);

	}
	
	// Métodos para insersión
	@Override
	public void insertDepen(Dependencia depen) {
		String sql = "INSERT INTO Dependencia (ClaveDependencia, DescripcionDependencia)"
		+ " VALUES (?, ?)";
		jdbcTemplate.update(sql, depen.getId(), depen.getName());
	}
	
	@Override
	public void insertSubdepen(Subdependencia subdepen) {
		subdepen.setClave_comp(concat2int(subdepen.getClave_ds(), subdepen.getClave_s()));
		String sql = "INSERT INTO Subdependencia "
		+ "(ClaveDependenciaSubdependencia, ClaveSubdependencia, DescripcionSubdependencia, ClaveCompuestaSubdependencia, EstatusSubdependencia, TipoDescripcionSubdependencia)"
		+ " VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, subdepen.getClave_ds(), subdepen.getClave_s(), subdepen.getDesc(), subdepen.getClave_comp(), subdepen.getStatus(), subdepen.getTipo_desc());
	}
	
	@Override
	public void insertProg(Programa prog) {
		prog.setClave_comp(concat3int(prog.getClave_dp(), prog.getClave_sdp(), prog.getClave_p()));
		String sql = "INSERT INTO Programa "
		+ "(ClaveCompuestaPrograma, ClaveDependenciaPrograma, ClavePrograma, ClaveSubdependenciaPrograma, DescripcionPrograma)"
		+ " VALUES (?, ?, ?, ?, ?)";

		jdbcTemplate.update(sql, prog.getClave_comp(), prog.getClave_dp(), prog.getClave_p(), prog.getClave_sdp(), prog.getDesc());
	}
	
	@Override
	public void insertSubProg(Subprograma subprog) {
		subprog.setClave_comp(concat4int(subprog.getClave_dsp(), subprog.getClave_sdsp(), subprog.getClave_psp(), subprog.getClave_sp()));
		String sql = "INSERT INTO Subprograma "
		+ "(ClaveDependenciaSubprograma, " + 
		"ClaveSubdependenciaSubprograma, " + 
		"ClaveProgramaSubprograma, " + 
		"ClaveSubprograma, " + 
		"DescripcionSubprograma, " + 
		"ClaveCompuestaSubprograma, " + 
		"EstatusSubprograma) " + 
		"VALUES (?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, subprog.getClave_dsp(), subprog.getClave_sdsp(), subprog.getClave_psp(), subprog.getClave_sp(), subprog.getDesc(), subprog.getClave_comp(), subprog.getStatus());
	}
	
	@Override
	public void saveOrUpdateSd(Subdependencia depen, int depenId) {
		if (depen.getClave_comp() > 0) {
			String sql = "UPDATE Subdependencia "
			+ "SET ClaveDependenciaSubdependencia=?, "
			+ "ClaveSubdependencia=?, "
			+ "DescripcionSubdependencia=?, "
			+ "ClaveCompuestaSubdependencia=?, "
			+ "EstatusSubdependencia=?, "
			+ "TipoDescripcionSubdependencia=? "
			+ "WHERE ClaveCompuestaSubdependencia=?";
			jdbcTemplate.update(sql, depen.getClave_ds(), depen.getClave_s(), depen.getDesc(), depen.getClave_comp(), depen.getStatus(), depen.getTipo_desc(), depenId);
		}
	}
	
	@Override
	public void saveOrUpdateProg(Programa prog, int depenId) {
		String sql = "UPDATE Programa "
		+ "SET ClaveDependenciaPrograma=?, "
		+ "ClaveSubdependenciaPrograma=?, "
		+ "ClavePrograma=?, "
		+ "DescripcionPrograma=?, "
		+ "ClaveCompuestaPrograma=? "
		+ "WHERE ClaveCompuestaPrograma=?";
		jdbcTemplate.update(sql, prog.getClave_dp(), prog.getClave_sdp(), prog.getClave_p(), prog.getDesc(), prog.getClave_comp(), depenId);
	}
	
	@Override
	public void saveOrUpdateSubp(Subprograma subprog, int depenId) {
		String sql = "UPDATE Subprograma "
		+ "SET ClaveDependenciaSubprograma=?, "
		+ "ClaveSubdependenciaSubprograma=?, "
		+ "ClaveProgramaSubprograma=?, "
		+ "ClaveSubprograma=?, "
		+ "DescripcionSubprograma=?, "
		+ "ClaveCompuestaSubprograma=?, "
		+ "EstatusSubprograma=? "
		+ "WHERE ClaveCompuestaSubprograma=?";
		jdbcTemplate.update(sql, subprog.getClave_dsp(), subprog.getClave_sdsp(),subprog.getClave_psp(), subprog.getClave_sp(), subprog.getDesc(), subprog.getClave_comp(), subprog.getStatus(), depenId);

	}

	@Override	
	public void newDependencia(Dependencia depen) {
		String sql = "INSERT INTO Dependencia VALUES (?,?)";
		jdbcTemplate.update(sql, depen.getId(), depen.getName());
	}
	
	@Override
	public void deleteDepen(int depenId) {
		String sql;

		sql = "DELETE FROM Subprograma WHERE ClaveDependenciaSubprograma=?";
		jdbcTemplate.update(sql, depenId);
		sql = "DELETE FROM Programa WHERE ClaveDependenciaPrograma=?";
		jdbcTemplate.update(sql, depenId);
		sql = "DELETE FROM Subdependencia WHERE ClaveDependenciaSubdependencia=?";
		jdbcTemplate.update(sql, depenId);
		sql = "DELETE FROM Dependencia WHERE ClaveDependencia=?";
		jdbcTemplate.update(sql, depenId);
	}
	
	@Override
	public void deleteSubdepen(int depenId) {
		String sql;
		sql = "DELETE FROM Subprograma WHERE ClaveCompuestaSubprograma BETWEEN ? and ?";
		jdbcTemplate.update(sql, (depenId*10000), (depenId*10000+9999));
		sql = "DELETE FROM Programa WHERE ClaveCompuestaPrograma BETWEEN ? and ?";
		jdbcTemplate.update(sql, (depenId*100), (depenId*100+99));
		sql = "DELETE FROM Subdependencia WHERE ClaveCompuestaSubdependencia=?";
		jdbcTemplate.update(sql, depenId);
	}
	
	@Override
	public void deleteProg(int progId) {
		String sql;
		sql = "DELETE FROM Subprograma WHERE ClaveCompuestaSubprograma BETWEEN ? and ?";
		jdbcTemplate.update(sql, (progId*100), (progId*100+99));
		sql = "DELETE FROM Programa WHERE ClaveCompuestaPrograma=?";
		jdbcTemplate.update(sql, progId);
	}
	
	@Override
	public void deleteSubProg(int subProgId) {
		String sql;
		sql = "DELETE FROM Subprograma WHERE ClaveCompuestaSubprograma=?";
		jdbcTemplate.update(sql, subProgId);
	}


	@Override
	public Subdependencia get(int depenId) {
		String sql = "SELECT * FROM Subdependencia WHERE ClaveCompuestaSubdependencia=" + depenId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<Subdependencia>() {

			@Override
			public Subdependencia extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				if (rs.next()) {
					Subdependencia depen = new Subdependencia();
					depen.setClave_ds(rs.getInt("ClaveDependenciaSubdependencia"));
					depen.setClave_s(rs.getInt("ClaveSubdependencia"));
					depen.setDesc(rs.getString("DescripcionSubdependencia"));
					depen.setClave_comp(rs.getInt("ClaveCompuestaSubdependencia"));
					depen.setStatus(rs.getInt("EstatusSubdependencia"));
					depen.setTipo_desc(rs.getInt("TipoDescripcionSubdependencia"));
					
					return depen;
				}
				return null;
			}
		});
	}
	
	
	@Override
	public Dependencia getD(int depenId) {
		String sql = "SELECT * FROM Dependencia WHERE ClaveDependencia=" + depenId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<Dependencia>() {
			@Override
			public Dependencia extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				if (rs.next()) {
					Dependencia depen = new Dependencia();
					depen.setId(rs.getInt("ClaveDependencia"));
					depen.setName(rs.getString("DescripcionDependencia"));
					
					return depen;
				}
				return null;
			}
		});
	}

	@Override
	public Programa getP(int depenId) {
		String sql = "SELECT * FROM Programa WHERE ClaveCompuestaPrograma=" + depenId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<Programa>() {
			@Override
			public Programa extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				if (rs.next()) {
					Programa prog = new Programa();
					prog.setClave_comp(rs.getInt("ClaveCompuestaPrograma"));
					prog.setClave_dp(rs.getInt("ClaveDependenciaPrograma"));
					prog.setClave_p(rs.getInt("ClavePrograma"));
					prog.setClave_sdp(rs.getInt("ClaveSubdependenciaPrograma"));
					prog.setDesc(rs.getString("DescripcionPrograma"));
					
					return prog;
				}
				return null;
			}
		});
	}
	
	@Override
	public Subprograma getSp(int depenId) {
		String sql = "SELECT * FROM Subprograma WHERE ClaveCompuestaSubprograma=" + depenId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<Subprograma>() {
			@Override
			public Subprograma extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				if (rs.next()) {
					Subprograma subprog = new Subprograma();
					subprog.setClave_comp(rs.getInt("ClaveCompuestaSubprograma"));
					subprog.setClave_dsp(rs.getInt("ClaveDependenciaSubprograma"));
					subprog.setClave_psp(rs.getInt("ClaveProgramaSubprograma"));
					subprog.setClave_sdsp(rs.getInt("ClaveSubdependenciaSubprograma"));
					subprog.setClave_sp(rs.getInt("ClaveSubprograma"));
					subprog.setStatus(rs.getInt("EstatusSubprograma"));
					subprog.setDesc(rs.getString("DescripcionSubprograma"));
					
					return subprog;
				}
				return null;
			}
		});
	}
	
	@Override
	public List<Dependencia> listD() {
		String sql = "SELECT * FROM Dependencia";
		List<Dependencia> listDepen = jdbcTemplate.query(sql, new RowMapper<Dependencia>() {
			@Override
			public Dependencia mapRow(ResultSet rs, int rowNum) throws SQLException {
				Dependencia aDepen = new Dependencia();

				aDepen.setId(rs.getInt("ClaveDependencia"));
				aDepen.setName(rs.getString("DescripcionDependencia"));
				
				return aDepen;
			}
		});
		return listDepen;
	}
	
	@Override
	public String subD(int depenId) {
		String sql = "SELECT * FROM Subdependencia WHERE ClaveCompuestaSubdependencia=" + depenId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<String>() {
			@Override
			public String extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				if (rs.next()) {
					String depen = rs.getString("DescripcionSubdependencia");
					return depen;
				}
				return null;
			}
		});
	}
	
	@Override
	public String nomD(int depenId) {
		String sql = "SELECT * FROM Dependencia WHERE ClaveDependencia=" + depenId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<String>() {

			@Override
			public String extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				if (rs.next()) {
					String depen = rs.getString("DescripcionDependencia");
					return depen;
				}
				return null;
			}
		});
	}
	
	@Override
	public String progP(int depenId) {
		String sql = "SELECT * FROM Programa WHERE ClaveCompuestaPrograma=" + depenId;
		return jdbcTemplate.query(sql, new ResultSetExtractor<String>() {
			@Override
			public String extractData(ResultSet rs) throws SQLException,
			DataAccessException {
				if (rs.next()) {
					String depen = rs.getString("DescripcionPrograma");
					return depen;
				}
				return null;
			}
		});
	}
	
	@Override
	public List<Subdependencia> listSd(int depen) {
		String sql = "SELECT * FROM Subdependencia WHERE ClaveCompuestaSubdependencia BETWEEN "
		+ (depen*100) + "AND " + (depen*100+99);
		List<Subdependencia> listDepen = jdbcTemplate.query(sql, new RowMapper<Subdependencia>() {
			@Override
			public Subdependencia mapRow(ResultSet rs, int rowNum) throws SQLException {
				Subdependencia aDepen = new Subdependencia();

				aDepen.setClave_ds(rs.getInt("ClaveDependenciaSubdependencia"));
				aDepen.setClave_comp(rs.getInt("ClaveCompuestaSubdependencia"));
				aDepen.setDesc(rs.getString("DescripcionSubdependencia"));
				aDepen.setClave_s(rs.getInt("ClaveSubdependencia"));
				aDepen.setStatus(rs.getInt("EstatusSubdependencia"));
				aDepen.setTipo_desc(rs.getInt("TipoDescripcionSubdependencia"));

				return aDepen;
			}
		});
		return listDepen;
	}

	@Override
	public List<Programa> listP(int cve) {
		String sql = "SELECT * FROM Programa WHERE ClaveCompuestaPrograma BETWEEN " + cve + " AND " + (cve+99);
		List<Programa> listDepenP = jdbcTemplate.query(sql, new RowMapper<Programa>() {
			@Override
			public Programa mapRow(ResultSet rs, int rowNum) throws SQLException {
				Programa aDepen = new Programa();
				
				aDepen.setClave_dp(rs.getInt("ClaveDependenciaPrograma"));
				aDepen.setClave_sdp(rs.getInt("ClaveSubdependenciaPrograma"));
				aDepen.setClave_p(rs.getInt("ClavePrograma"));
				aDepen.setDesc(rs.getString("DescripcionPrograma"));
				aDepen.setClave_comp(rs.getInt("ClaveCompuestaPrograma"));

				return aDepen;
			}
		});
		return listDepenP;
	}

	@Override
	public List<Subprograma> listSp(int cve) {
		String sql = "SELECT * FROM Subprograma WHERE ClaveCompuestaSubprograma BETWEEN " + cve + " AND " + (cve + 99);
		List<Subprograma> listDepen = jdbcTemplate.query(sql, new RowMapper<Subprograma>() {
			@Override
			public Subprograma mapRow(ResultSet rs, int rowNum) throws SQLException {
				Subprograma aDepen = new Subprograma();
				
				aDepen.setClave_dsp(rs.getInt("ClaveDependenciaSubprograma"));
				aDepen.setClave_sdsp(rs.getInt("ClaveSubdependenciaSubprograma"));
				aDepen.setClave_psp(rs.getInt("ClaveProgramaSubprograma"));
				aDepen.setClave_sp(rs.getInt("ClaveSubprograma"));
				aDepen.setDesc(rs.getString("DescripcionSubprograma"));
				aDepen.setClave_comp(rs.getInt("ClaveCompuestaSubprograma"));
				aDepen.setStatus(rs.getInt("EstatusSubprograma"));
				
				return aDepen;
			}
		});
		return listDepen;
	}
}
