package com.ls.contactapi.controller;

import com.ls.contactapi.domain.Contact;
import com.ls.contactapi.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact){
        return ResponseEntity.created(URI.create("/contacts/userID")).body(contactService.createContact(contact));
    }

    @GetMapping
    public ResponseEntity<Page<Contact>> getContacts(@RequestParam(value="page",defaultValue="0") int page,
                                                     @RequestParam(value="size", defaultValue="10") int size){
        return ResponseEntity.ok().body(contactService.getAllContacts(page, size));
    }
}
