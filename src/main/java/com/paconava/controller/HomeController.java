package net.codejava.spring.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.codejava.spring.dao.DepenDAO;
import net.codejava.spring.model.Dependencia;
import net.codejava.spring.model.Subdependencia;
import net.codejava.spring.model.Programa;
import net.codejava.spring.model.Subprograma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This controller routes accesses to the application to the appropriate
 * hanlder methods. 
 * @author Francisco Nava
 *
 */
@Controller
public class HomeController {

	@Autowired
	private DepenDAO depenDAO;
	
	@GetMapping("/")
	public  ModelAndView homeView(ModelAndView model) throws IOException{
		model.addObject("depenNotFound", false);
		model.setViewName("index");
		return model;
	}
	
	@GetMapping("/new-depen")
	public ModelAndView newDepen(ModelAndView model) {
		Dependencia newDepen = new Dependencia();
		model.addObject("newD", newDepen);
		model.setViewName("new-depen");
		return model;
	}
	
	@GetMapping("/new-subdep")
	public ModelAndView newSubdepen(ModelAndView model, HttpServletRequest request) {
		Subdependencia newSubdep = new Subdependencia();
		int depenId = Integer.parseInt(request.getParameter("cve_depen"));
		newSubdep.setClave_ds(depenId);
		model.addObject("newSD", newSubdep);
		model.addObject("cveD", depenId);
		model.setViewName("new-subdep");
		return model;
	}
	
	@GetMapping("/new-prog")
	public ModelAndView newProg(ModelAndView model, HttpServletRequest request) {
		int subdepenId = Integer.parseInt(request.getParameter("clave_sd"));
		Programa newProg = new Programa();
		newProg.setClave_dp(subdepenId/100);
		newProg.setClave_sdp(subdepenId%100);
		model.addObject("newProg", newProg);
		model.setViewName("new-prog");
		return model;
	}
	
	@GetMapping("/new-subprog")
	public ModelAndView newSubProg(ModelAndView model, HttpServletRequest request) {
		int progId = Integer.parseInt(request.getParameter("clave_sp"));
		Subprograma newSubProg = new Subprograma();
		newSubProg.setClave_dsp(progId/10000);
		newSubProg.setClave_sdsp((progId%10000)/100);
		newSubProg.setClave_psp(progId%100);
		model.addObject("newSubProg", newSubProg);
		model.setViewName("new-subprog");
		return model;
	}
	
	@PostMapping("/saveNewDepen")
	public ModelAndView saveNewDepen(@ModelAttribute Dependencia depen) {
		depenDAO.insertDepen(depen);		
		return new ModelAndView("redirect:/subdependencias?cve_depen=" + depen.getId());
	}
	
	@PostMapping("/saveDepen")
	public ModelAndView saveDepen(@ModelAttribute Dependencia depenObj, HttpServletRequest request) {
		int depenId = Integer.parseInt(request.getParameter("clave"));
		depenDAO.saveOrUpdate(depenObj, depenId);		
		return new ModelAndView("redirect:/subdependencias?cve_depen=" + depenObj.getId());
	}
	
	@PostMapping("/newSubdepen")
	public ModelAndView saveSubdepen(@ModelAttribute Subdependencia subdepen) {
		depenDAO.insertSubdepen(subdepen);
		int cve = subdepen.getClave_comp() / 100;
		return new ModelAndView("redirect:/subdependencias?cve_depen=" + cve);
	}
	
	@PostMapping("/saveNewProg")
	public ModelAndView saveNewProg(@ModelAttribute Programa prog) {
		depenDAO.insertProg(prog);		
		return new ModelAndView("redirect:/programas?clave_comp=" + prog.getClave_comp()/100);
	}
	
	@PostMapping("/saveNewSubProg")
	public ModelAndView saveNewProg(@ModelAttribute Subprograma subprog) {
		depenDAO.insertSubProg(subprog);		
		return new ModelAndView("redirect:/subprogramas?clave_comp=" + subprog.getClave_comp()/100);
	}
	
	@PostMapping("/saveSubDepen")
	public ModelAndView saveSubDepen(@ModelAttribute Subdependencia depen, HttpServletRequest request) {
		int depenId = Integer.parseInt(request.getParameter("clave_comp"));
		depenDAO.saveOrUpdateSd(depen, depenId);
		int cve = depen.getClave_comp() / 100;
		return new ModelAndView("redirect:/subdependencias?cve_depen=" + cve);
	}
	
	@PostMapping("/saveProg")
	public ModelAndView saveProg(@ModelAttribute Programa prog, HttpServletRequest request) {
		int progId = Integer.parseInt(request.getParameter("clave_comp"));
		depenDAO.saveOrUpdateProg(prog, progId);
		return new ModelAndView("redirect:/programas?clave_comp=" + progId/100);
	}
	
	@PostMapping("/saveSubp")
	public ModelAndView saveSubp(@ModelAttribute Subprograma subp, HttpServletRequest request) {
		int subprogId = Integer.parseInt(request.getParameter("clave_comp"));
		depenDAO.saveOrUpdateSubp(subp, subprogId);
		return new ModelAndView("redirect:/subprogramas?clave_comp=" + subprogId/100);
	}
	
	@GetMapping("/editDepen")
	public ModelAndView editDepen(HttpServletRequest request) {
		int depenId = Integer.parseInt(request.getParameter("clave_comp"));
		
		Subdependencia depen = depenDAO.get(depenId);
		ModelAndView model = new ModelAndView("DepenForm");
		model.addObject("depen", depen);
		
		return model;
	}

	@RequestMapping(value = "/save", method=RequestMethod.POST )
	public ModelAndView guardarSubDepen(HttpServletRequest request) {

		int depenId = Integer.parseInt(request.getParameter("clave_comp"));
		Subdependencia depen = depenDAO.get(depenId);
		depenDAO.saveOrUpdateSd(depen, depenId);
		ModelAndView model = new ModelAndView("redirect:/");
		model.addObject("depen", depen);
		
		return model;
	}
	
	@GetMapping("/depen-top")
	public ModelAndView editarSubdepen(HttpServletRequest request) {
		int subdepenId = Integer.parseInt(request.getParameter("clave_comp"));
		int depenId = subdepenId / 100;
		Dependencia top = new Dependencia();
		Subdependencia depen = depenDAO.get(subdepenId);
		
		if (depenDAO.getD(depenId) == null) {
			top.setId(0);
			top.setName("Sin dependencia");
		}else top = depenDAO.getD(depenId);

		ModelAndView model = new ModelAndView("depen-top");
		model.addObject("top", top);
		model.addObject("depen", depen);
		model.addObject("subdepen", subdepenId);
		
		return model;
	}
	
	@GetMapping("/del-depen")
	public ModelAndView delDepen(HttpServletRequest request) {
		int depenId = Integer.parseInt(request.getParameter("clave_comp"));
		depenDAO.deleteDepen(depenId);

		ModelAndView model = new ModelAndView("redirect:/");
		return model;
	}
	
	@GetMapping("/del-subdepen")
	public ModelAndView delSubdepen(HttpServletRequest request) {
		int subdepenId = Integer.parseInt(request.getParameter("clave_comp"));
		
		depenDAO.deleteSubdepen(subdepenId);

		ModelAndView model = new ModelAndView("redirect:/subdependencias?cve_depen=" + subdepenId/100);
		return model;
	}
	
	@GetMapping("/del-prog")
	public ModelAndView delProg(HttpServletRequest request) {
		int progId = Integer.parseInt(request.getParameter("clave_comp"));
		
		depenDAO.deleteProg(progId);

		ModelAndView model = new ModelAndView("redirect:/programas?clave_comp=" + progId/100);
		return model;
	}
	
	@GetMapping("/del-subp")
	public ModelAndView delSubProg(HttpServletRequest request) {
		int subProgId = Integer.parseInt(request.getParameter("clave_comp"));
		
		depenDAO.deleteSubProg(subProgId);

		ModelAndView model = new ModelAndView("redirect:/subprogramas?clave_comp=" + subProgId/100);
		return model;
	}
	
	@GetMapping("/edit-prog")
	public ModelAndView editarProg(HttpServletRequest request) {
		int progId = Integer.parseInt(request.getParameter("clave_comp"));
		Programa prog = depenDAO.getP(progId);
		ModelAndView model = new ModelAndView("edit-prog");
		model.addObject("prog", prog);
		model.addObject("clave_comp", progId/100);

		return model;
	}
	
	@GetMapping("/edit-depen")
	public ModelAndView editarDepen(@ModelAttribute Dependencia depenObj, HttpServletRequest request) {
		int depenId = Integer.parseInt(request.getParameter("clave_comp"));

		ModelAndView model = new ModelAndView("edit-depen");
		model.addObject("depen", depenObj);
		model.addObject("numDepen", depenId);

		return model;
	}
	
	@GetMapping("/edit-subp")
	public ModelAndView editarSubp(HttpServletRequest request) {
		int subprogId = Integer.parseInt(request.getParameter("clave_comp"));
		Subprograma subp = depenDAO.getSp(subprogId);
		ModelAndView model = new ModelAndView("edit-subp");
		model.addObject("subp", subp);

		return model;
	}
	
	@GetMapping("/subdependencias")
	public  ModelAndView listDepen(ModelAndView model, HttpServletRequest request) throws NumberFormatException{
		int depenId = 0;
		try{
			depenId = Integer.parseInt(request.getParameter("cve_depen"));
			Dependencia depenObj = depenDAO.getD(depenId);
			List<Subdependencia> listDepen = depenDAO.listSd(depenId);

			if (!listDepen.isEmpty() || depenObj != null) {
				String depen = depenDAO.nomD(depenId);
				if(depen == "")
					depenObj.setName("S/N");
				model.addObject("listDepen", listDepen);
				model.addObject("nomDepen", depen);
				model.addObject("numDepen", depenId);
				model.addObject("depenObj", depenObj);
				model.setViewName("subdependencias");
			} else {
				model.addObject("depenNotFound", true);
				model.setViewName("index");
			}
		} 
		catch (NumberFormatException e){
			if(request.getParameter("cve_depen").equals("")){
				model.setViewName("index");
			}
		}
		catch (NullPointerException f) {
			model.setViewName("subdependencias");
		}
		return model;
	}
	
	@GetMapping("/programas")
	public  ModelAndView listDepenP(ModelAndView model, HttpServletRequest request) throws IOException{
		int cve = Integer.parseInt(request.getParameter("clave_comp")) * 100;
		List<Programa> listDepenP = depenDAO.listP(cve);
		String subdepen = depenDAO.subD(cve/100);

		model.addObject("listDepenP", listDepenP);
		model.addObject("subdepen", subdepen);
		model.addObject("cvesubd", (cve/100));
		model.addObject("cved", (cve/10000));
		model.setViewName("programas");

		return model;
	}
	
	@GetMapping("/subprogramas")
	public  ModelAndView listDepenSubP(ModelAndView model, HttpServletRequest request) throws IOException{
		int cveProg = Integer.parseInt(request.getParameter("clave_comp"));
		int cve =  cveProg * 100;
		List<Subprograma> listDepenSubP = depenDAO.listSp(cve);
		Programa prog = depenDAO.getP(cveProg);

		model.addObject("listDepenSubP", listDepenSubP);
		model.addObject("nomProg", prog.getDesc());
		model.addObject("numProg", cveProg);
		model.addObject("cved", cveProg/100);
		model.setViewName("subprogramas");

		return model;
	}
}
