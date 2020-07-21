package com.gigmatch.demo.controllers;

import com.gigmatch.demo.daos.EventsRepository;
import com.gigmatch.demo.daos.ProfilesRepository;
import com.gigmatch.demo.daos.UsersRepository;
import com.gigmatch.demo.models.Event;
import com.gigmatch.demo.models.Post;
import com.gigmatch.demo.models.Profile;
import com.gigmatch.demo.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EventController {
    private EventsRepository eventsDao;
    private UsersRepository usersDao;
    private ProfilesRepository profilesDao;

    public EventController(EventsRepository eventsRepository, UsersRepository usersRepository, ProfilesRepository profilesDao){
        this.eventsDao = eventsRepository;
        this.usersDao = usersRepository;
        this.profilesDao = profilesDao;
    }

    //shows all events
    @GetMapping("/feed/events")
    public String index(Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("profileId", profilesDao.findByOwner(currentUser).getId());
        List<Event> eventList = eventsDao.findAll();
        model.addAttribute("noEventsFound", eventList.size() == 0);
        model.addAttribute("events", eventList);
        return "events/eventsFeed";
    }

    //creates an event
    @GetMapping("/events/create")
    public String showForm(Model viewModel){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        viewModel.addAttribute("event", new Event());
        viewModel.addAttribute("profileId", profilesDao.findByOwner(currentUser).getId());
        return "events/CreateAnEvent";
    }

    //saves the event made
    @PostMapping("/events/create")
    public String save(@ModelAttribute Event eventToBeSaved, Model viewModel) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        eventToBeSaved.setOwner(currentUser);
        Event savedEvent = eventsDao.save(eventToBeSaved);
        viewModel.addAttribute("profileId", profilesDao.findByOwner(currentUser).getId());

        return "redirect:/feed/events";
    }

    //finds event to edit
    @GetMapping("/events/{id}/edit")
    public String showEditForm(Model model, @PathVariable long id){
        // find an event
        Event eventToEdit = eventsDao.getOne(id);
        model.addAttribute("event", eventToEdit);
        return "events/editAnEvent";
    }

    //saves event to be edited
    @PostMapping("/events/{id}/edit")
    public String update(@ModelAttribute Event eventToEdit, Model model){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("profileId", profilesDao.findByOwner(currentUser).getId());
        eventToEdit.setOwner(currentUser);
        // save the changes
        eventsDao.save(eventToEdit); // update ads set title = ? where id = ?
        return "redirect:/feed/events";
    }

    //deletes event
    @PostMapping("/events/{id}/delete")
    public String destroy(@PathVariable long id){
        eventsDao.deleteById(id);
        return "redirect:/feed/events";
    }

    //searches events by description, city, or zip code
    @GetMapping("/search/events")
    public String searchByDescription(Model model, @RequestParam(name = "term") String term){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("profileId", profilesDao.findByOwner(currentUser).getId());
        List<Event> resultList = eventsDao.searchByDescription(term);
        model.addAttribute("results", resultList);
        model.addAttribute("noResultsFound", resultList.size() == 0);
        return "events/searchEvents";
    }



    // Reading current user My Events Feed
    @GetMapping("/feed/myEvents")
    public String showMyEvents(Model model){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("profileId", profilesDao.findByOwner(currentUser).getId());
        List<Event> eventList = eventsDao.findAllByOwner(currentUser);
        model.addAttribute("noEventsFound", eventList.size() == 0);
        model.addAttribute("userEvents", eventList);
        return "events/myEventsFeed";
    }


    @PostMapping("/events/{id}/interests")
    public String eventInterests(Model model, @PathVariable long id){
        //Getting current USER OBJECT, all properties
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //Getting the "RIGHT" post id
        Event event = eventsDao.getOne(id);
        //Get the current reactions list from that post
        List<User> interestsList = event.getEventInterests();
        //Add user's reaction to the reaction List
        interestsList.add(currentUser);
        //Overriding the reactions List by adding the current user's reaction to the list
        event.setEventInterests(interestsList);
        //Save the Post back to database
        eventsDao.save(event);
        //returning the view to this feed
        return "redirect:/feed/events";
    }

}
