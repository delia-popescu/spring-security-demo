package com.training.springmvc.demospringmvc.persons;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PersonController {
	
	@Autowired
	private PersonJPARepository personRepository;


	@GetMapping("/persons")
	public String list(Model model) {
		model.addAttribute("persons", personRepository.findAll());
		return "persons/list";
	}

	@GetMapping("/persons/new")
	public String newCar(Model model) {
		model.addAttribute("person", new Person());
		return "persons/edit";
	}

	@GetMapping("/persons/{id}")
	public String show(@PathVariable("id") int id, Model model) {
		Person person = personRepository.findById(id).orElse(null);
		model.addAttribute("person", person);
		return "persons/edit";
	}

	@DeleteMapping("/persons/{id}")
	public String delete(@PathVariable("id") int id) {
		personRepository.deleteById(id);
		return "redirect:/persons";
	}

	@RequestMapping(value="/persons", method = RequestMethod.POST)
	public String update(@Valid Person person, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "persons/edit";
		}
		personRepository.saveAndFlush(person);
		return "redirect:/persons";
	}
}