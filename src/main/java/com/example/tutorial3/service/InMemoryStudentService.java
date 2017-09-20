package com.example.tutorial3.service;
import java.util.ArrayList;
import com.example.tutorial3.model.StudentModel;
import java.util.List;


public class InMemoryStudentService implements StudentService{
	private static List<StudentModel> studentList = new ArrayList<StudentModel>();
	

	@Override 
	public StudentModel selectStudent(String npm) {
		// TODO Auto-generated method stub
		StudentModel item = null;
		boolean isFound = false;
		int a = 0;
		while (!isFound && a < studentList.size()) {
			if(studentList.get(a).getNpm().equals(npm)){
				isFound = true;
				item = studentList.get(a);
			}
			a++;
		}
		
		return item;
	}

	@Override
	public List<StudentModel> selectAllStudents() {
		// TODO Auto-generated method stub
		return studentList;
	}

	@Override
	public void addStudent(StudentModel student) {
		// TODO Auto-generated method stub
		studentList.add(student);
	}

	@Override
	public boolean deleteStudent(String npm) {
		Boolean checkExist = false;
		int a = 0;
		while (checkExist == false && a < studentList.size()) {
			if(studentList.get(a).getNpm().equals(npm)){
				studentList.remove(a);
				checkExist = true;
			}
			a++;
		}
		
		return checkExist;
	}
}
