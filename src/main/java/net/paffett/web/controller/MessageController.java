package net.paffett.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.paffett.web.form.MessageForm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/")
@Controller
public class MessageController {

	Map<Integer, MessageForm> recipeList = new HashMap<Integer, MessageForm>();
	static int id = 0;

	/** Save a recipe and return back to display all recipe	 **/
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public ModelAndView send(MessageForm form, BindingResult errors,
			HttpServletRequest request, HttpServletResponse response) {

		ModelMap modelMap = new ModelMap();
		modelMap.put("message", ((MessageForm)form).getMessageText());
		return new ModelAndView("show", modelMap);
	}

	@RequestMapping(value = "/newmessage", method = RequestMethod.GET)
	public ModelAndView newRecipe() {
		MessageForm form = new MessageForm();
		ModelMap map = new ModelMap();
		map.put("message", form);
		return new ModelAndView("recipe", map);
	}

	/** Display all recipes	 **/
	@RequestMapping(method = RequestMethod.GET)
	public String displayAll(HttpServletRequest request) {
		ModelMap map = new ModelMap();
		Iterator<Integer> iter = recipeList.keySet().iterator();
		List<MessageForm> newMap = new ArrayList<MessageForm>();
		while (iter.hasNext()) {
			Object key = iter.next();
			if (key != null)
				newMap.add(recipeList.get(key));
		}
		map.put("recipes", newMap);
		request.setAttribute("recipes", newMap);
		return "show";
	}

	/** Delete a recipe and return back to all list **/
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable String id, HttpServletRequest request) {
		recipeList.remove(Integer.parseInt(id));
		Iterator<Integer> iter = recipeList.keySet().iterator();
		List<MessageForm> newMap = new ArrayList<MessageForm>();
		while (iter.hasNext()) {
			Object key = iter.next();
			if (key != null)
				newMap.add(recipeList.get(key));
		}
		request.setAttribute("recipes", newMap);
		return "show";
	}

	/** Update a server and return back to all list **/
	@RequestMapping(value = "/update/{id}")
	public ModelAndView update(@PathVariable String id) {
		ModelMap map = new ModelMap();
		map.put("recipe", recipeList.get(Integer.parseInt(id)));
		return new ModelAndView("recipe", map);

	}

}
