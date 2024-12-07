package com.example.demo.controller;

import com.example.demo.dto.EntryRequest;
import com.example.demo.entity.Entry;
import com.example.demo.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entries")
public class EntryController {

    @Autowired
    private EntryService entryService;

    @PostMapping
    public Entry createEntry(@RequestBody EntryRequest entryRequest) {
        return entryService.createEntry(entryRequest.getTitle(), entryRequest.getContent());
    }

    @PutMapping("/{id}")
    public Entry updateEntry(@PathVariable Long id,
                             @RequestParam String title,
                             @RequestParam String content) {
        return entryService.updateEntry(id, title, content);
    }

    @DeleteMapping("/{id}")
    public void deleteEntry(@PathVariable Long id) {
        entryService.deleteEntry(id);
    }

    @GetMapping
    public List<Entry> getAllEntries() {
        return entryService.getAllEntries();
    }

    @GetMapping("/{id}")
    public Entry getEntryById(@PathVariable Long id) {
        return entryService.getEntryById(id);
    }
}
