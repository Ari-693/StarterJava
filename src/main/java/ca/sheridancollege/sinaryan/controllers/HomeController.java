package ca.sheridancollege.sinaryan.controllers;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ca.sheridancollege.sinaryan.beans.SampleGroup;
import ca.sheridancollege.sinaryan.beans.SampleObject;
import ca.sheridancollege.sinaryan.repositories.SampleRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class HomeController {

	private SampleRepository mainRepo;

	@GetMapping("/")
	String homePage() {
		return "index.html";
	}

	@GetMapping("/addObject")
	String addObjectPage(Model model) {
		model.addAttribute("object", new SampleObject());
		ArrayList<SampleGroup> groups = mainRepo.getGroups();
		model.addAttribute("groups", groups);
		return "addObjectpage.html";
	}

	@PostMapping("/addObject")
	String addObject(@ModelAttribute SampleObject object, Model model) {
		model.addAttribute("object", new SampleObject());
		mainRepo.addObject(object);
		return "redirect:/viewObject";
	}

	@GetMapping("/viewObject")
	String viewObjectPage(Model model) {
		ArrayList<SampleObject> objects = mainRepo.getObjects();
		model.addAttribute("objects", objects);
		return "viewObjectpage.html";
	}

	@GetMapping("/editObject/{id}")
	String editObjectPage(@PathVariable int id, Model model) {
		SampleObject object = mainRepo.findObjectById(id);
		model.addAttribute("object", object);
		ArrayList<SampleGroup> groups = mainRepo.getGroups();
		model.addAttribute("groups", groups);
		return "editObjectpage.html";
	}

	@PostMapping("/editObject")
	String editObject(@ModelAttribute SampleObject object, Model model) {
		model.addAttribute("object", new SampleObject());
		mainRepo.editObject(object);
		return "redirect:/viewObject";
	}

	@GetMapping("/deleteObject/{id}")
	String deleteObject(@PathVariable int id) {
		mainRepo.removeObjectById(id);
		return "redirect:/viewObject";
	}

	@GetMapping("/addGroup")
	String addGroupPage(Model model) {
		model.addAttribute("group", new SampleGroup());
		return "addGrouppage.html";
	}

	@PostMapping("/addGroup")
	String addGroup(@ModelAttribute SampleGroup group, Model model) {
		model.addAttribute("group", new SampleGroup());
		mainRepo.addGroup(group);
		return "redirect:/viewGroup";
	}

	@GetMapping("/viewGroup")
	String viewGroupPage(Model model) {
		ArrayList<SampleGroup> groups = mainRepo.getGroups();
		model.addAttribute("groups", groups);
		return "viewGrouppage.html";
	}

	@GetMapping("/editGroup/{id}")
	String editGroupPage(@PathVariable int id, Model model) {
		SampleGroup group = mainRepo.findGroupById(id);
		model.addAttribute("group", group);
		return "editGrouppage.html";
	}

	@PostMapping("/editGroup")
	String editGroup(@ModelAttribute SampleGroup group, Model model) {
		model.addAttribute("group", new SampleGroup());
		mainRepo.editGroup(group);
		return "redirect:/viewGroup";
	}

	@GetMapping("/deleteGroup/{id}")
	String deleteGroup(@PathVariable int id) {
		mainRepo.removeGroupById(id);
		return "redirect:/viewGroup";
	}

}
