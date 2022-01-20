package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
		System.out.println(personList.toString());
		
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
	//@RequestParam은 URL 파라미터로 값을 넘기는 방식
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

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping(value="/test", method = {RequestMethod.GET, RequestMethod.POST})
	public String test(@RequestParam(value="n") String name,
	@RequestParam(value="age", required =false, defaultValue = "-1" ) int age ) {
	
	System.out.println(name);
	System.out.println(age);
	
	
	return "writeForm";
	}
	
	
	@RequestMapping(value="/view", method = {RequestMethod.GET, RequestMethod.POST})
	public String view(@RequestParam(value="no") int no)  {
	System.out.println("@RequestParam");
	System.out.println( no + "번글 가져오기");
	
	
	return "writeForm";
	}
	
	// localhost:8088/phonebook3/phone/aaa
	// localhost:8088/phonebook3/phone/bbb
	//@PathVariable은 URL경로에 변수를 넣는것
	@RequestMapping(value="/{id}", method = {RequestMethod.GET, RequestMethod.POST})
	public String blog(@PathVariable(value="id") String id)  {
	System.out.println(id +"의 블로그입니다.");
	
	return "writeForm";
	}
	
	
	
	
	
	@RequestMapping(value="/{no}/{num}/view", method = {RequestMethod.GET, RequestMethod.POST})
	public String view11(@PathVariable("no") int no, @PathVariable("num") int num)  {
	System.out.println("@PathVariable");
	System.out.println(no +"번글 가져오기");
	System.out.println(num);
	
	
	return "writeForm";
	}

}