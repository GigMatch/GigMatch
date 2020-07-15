package com.gigmatch.demo.controllers;

import com.gigmatch.demo.daos.EventsRepository;
import com.gigmatch.demo.daos.UsersRepository;
import com.gigmatch.demo.models.Event;
import com.gigmatch.demo.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EventController {
    private EventsRepository eventsDao;
    private UsersRepository usersDao;

    public EventController(EventsRepository eventsRepository, UsersRepository usersRepository){
        this.eventsDao = eventsRepository;
        this.usersDao = usersRepository;

    }

    @GetMapping("/feed/events")
//    @RequestMapping(value = "/ads", method = RequestMethod.GET)
    public String index(Model model) {
        List<Event> eventList = eventsDao.findAll();
        model.addAttribute("noEventFound", eventList.size() == 0);
        model.addAttribute("events", eventList);
        return "/homeFeed";

    }

    @GetMapping("/events/create")
    public String showForm(Model viewModel){
        viewModel.addAttribute("event", new Event());
        return "/events/CreateAnEvent";
    }

    @PostMapping("/events/create")
    public String save(@ModelAttribute Event eventToBeSaved) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        eventToBeSaved.setOwner(currentUser);
        Event savedEvent = eventsDao.save(eventToBeSaved);
        return "redirect:/feed" + savedEvent.getId();
    }

    @GetMapping("/events/{id}/edit")
    public String showEditForm(Model model, @PathVariable long id){
        // find an ad
        Event eventToEdit = eventsDao.getOne(id);
        model.addAttribute("event", eventToEdit);
        return "events/editAnEvent";
    }

    @PostMapping("/events/{id}/edit")
    public String update(@ModelAttribute Event eventToEdit){
        User currentUser = usersDao.getOne(1L);
        eventToEdit.setOwner(currentUser);
        // save the changes
        eventsDao.save(eventToEdit); // update ads set title = ? where id = ?
        return "redirect:/feed" + eventToEdit.getId();
    }
}
