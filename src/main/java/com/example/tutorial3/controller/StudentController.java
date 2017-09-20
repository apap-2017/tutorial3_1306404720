package com.example.tutorial3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tutorial3.model.StudentModel;
import com.example.tutorial3.service.InMemoryStudentService;
import com.example.tutorial3.service.StudentService;

@Controller
public class StudentController {
	
	private final StudentService studentService;
	
	public StudentController() {	
		// TODO Auto-generated constructor stub
		studentService = new InMemoryStudentService();
	}
	
	
	@RequestMapping("/student/add")
	public String add(@RequestParam(value = "npm", required = true) String npm,
				@RequestParam(value = "name", required = true) String name,
				@RequestParam(value = "gpa", required = true) double gpa) {
		StudentModel student = new StudentModel(name, npm, gpa);
		studentService.addStudent(student);
		return "add";
	}
	
	@RequestMapping("/student/view")
	public String view (Model model, @RequestParam(value = "npm", required = true) String npm) {
		StudentModel student = studentService.selectStudent(npm);
		model.addAttribute("student", student);
		
		return "view";	
	}
	

	@RequestMapping(value = {"/student/view/", "/student/view/{npm}"})
	public String viewStudent (Model model, @PathVariable Optional<String> npm) {
		if(!npm.isPresent()) {
			String Message = "Nomor NPM yang anda sediakan kosong atau tidak ada."; 
			model.addAttribute("message", Message);
			return "errortutor";
		}
		else {
			String searchNPM = npm.get();
			StudentModel student = studentService.selectStudent(searchNPM);
			if(student != null) {
				model.addAttribute("student", student);
				return "view";	
			}else {
				String Message = "Nomor NPM yang anda sediakan kosong atau tidak ada."; 
				model.addAttribute("message", Message);
				return "errortutor";
			}
		}
		
			
	}

	@RequestMapping(value = {"/student/delete/","/student/delete/{npm}"})
	public String delete(Model model, @PathVariable Optional<String> npm) {
		if (!npm.isPresent()) {
			String Message = "Nomor NPM kosong atau tidak ditemukan. Proses Delete Dibatalkan.";
			model.addAttribute("message", Message);
			return "errortutor";
		}else {
			String toDelete = npm.get();
			if(studentService.deleteStudent(toDelete)) {
				String Message = "Mahasiswa dengan NPM Nomor " + npm.get() + " berhasil dihapus.";
				model.addAttribute("message", Message);
				return "deletesuccess";
			}
			else {
				String Message = "Nomor NPM kosong atau tidak ditemukan. Proses Delete Dibatalkan.";
				model.addAttribute("message", Message);
				return "errortutor";
			}
		}
	}
	
	@RequestMapping("/student/viewall")
	public String viewAll(Model model) {
		List<StudentModel> students = studentService.selectAllStudents();
		model.addAttribute("students", students);
		return "viewall";
	}
}
