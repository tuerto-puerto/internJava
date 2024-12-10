package com.example.demo.controller;

import com.example.demo.dto.EntryRequest;
import com.example.demo.entity.Entry;
import com.example.demo.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/entries")
public class EntryController {

    @Autowired
    private EntryService entryService;

    @Autowired
    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<String> addEntry(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        entryService.addEntry(title, content, image);
        return ResponseEntity.ok("Entry created successfully!");
    }
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
    @DeleteMapping("/{id}/image")
    public void deleteImage(@PathVariable Long id) {
        entryService.deleteImage(id);
    }
}
