package com.crsms.validator;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.crsms.domain.Module;
import com.crsms.dto.ModuleForm;
import com.crsms.service.CourseService;

@Component
public class ModuleFormValidator implements Validator {
	
	@Autowired
	private CourseService courseService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ModuleForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", 
													"crsms.modules.error.name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description",
													"crsms.error.description.required");
		
		ModuleForm moduleFormDto = (ModuleForm) target;
		
		Long moduleId = moduleFormDto.getId();
		Long courseId = moduleFormDto.getCourseId();
		
		String name = moduleFormDto.getName();
		
		Set<Module> modules = courseService.getCourseById(courseId).getModules();
		
		for (Module module : modules) {
			//second condition allows you to edit other fields without "name already exists" error
			if (module.getName().equalsIgnoreCase(name) && (!module.getId().equals(moduleId))) {
				errors.rejectValue("name", "crsms.error.not.unique.name");
				break;
			}
		}
		
		if (name.length() > Module.MAX_NAME_LENGTH) {
			errors.rejectValue("name", "crsms.error.too.long", 
								new Object[]{Module.MAX_NAME_LENGTH},
								"name is too long");
		}
		
		String description = moduleFormDto.getDescription();
		
		if (description.length() > Module.MAX_DESCTIPTION_LENGTH) {
			errors.rejectValue("description", "crsms.error.too.long", 
								new Object[]{Module.MAX_DESCTIPTION_LENGTH},
								"description is too long");
		}
	}
}
