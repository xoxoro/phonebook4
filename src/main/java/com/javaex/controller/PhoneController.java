package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;

@Controller
@RequestMapping(value="/phone")
public class PhoneController {
	
	// 필드
	@Autowired
	private PhoneDao phoneDao;
	
	// 생성자
	// 메소드 g/s
	
	// 메소드 일반
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model) {
		System.out.println("PhoneController>list");
		
		List<PersonVo> personList = phoneDao.getPersonList();	
		model.addAttribute("personList", personList);
		
		return "list";
	}
	
	@RequestMapping(value = "/writeForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String writeForm() {
		System.out.println("PhoneController>writeForm");

		return "writeForm";
	}
	
	@RequestMapping(value = "/write", method = { RequestMethod.GET, RequestMethod.POST })
	public String write(@ModelAttribute PersonVo personVo) {
		System.out.println("PhoneController>write");

		phoneDao.personInsert(personVo);
		
		return "redirect:/phone/list";
	}
	
	@RequestMapping(value = "/updateForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String updateForm(@RequestParam("personId") int personId,
		Model model) {
		System.out.println("PhoneController>updateForm");

		PersonVo personVo = phoneDao.getPerson(personId);
		model.addAttribute("personVo", personVo);
		
		return "updateForm";
	}
	
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public String update(@ModelAttribute PersonVo personVo) {
		System.out.println("PhoneController>update");

		phoneDao.personUpdate(personVo);
		
		return "redirect:/phone/list";
	}
	
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(@RequestParam("personId") int personId) {
		System.out.println("PhoneController>delete");

		phoneDao.personDelete(personId);
		
		return "redirect:/phone/list";
	}

}