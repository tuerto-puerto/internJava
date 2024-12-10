package com.example.demo.service;

import com.example.demo.entity.Entry;
import com.example.demo.repository.EntryRepository;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EntryService {

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private UserService userService;

    public Entry addEntry(String title, String content, MultipartFile image) {
        User currentUser = userService.getCurrentUser();

        Entry entry = new Entry();
        entry.setTitle(title);
        entry.setContent(content);
        entry.setOwner(currentUser);

        try {
            if (image != null && !image.isEmpty()) {
                entry.setImage(image.getBytes());
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload image", e);
        }

        return entryRepository.save(entry);
    }
    public Entry createEntry(String title, String content) {

        User currentUser = userService.getCurrentUser();

        Entry entry = new Entry();
        entry.setTitle(title);
        entry.setContent(content);
        entry.setSummary(content.length() > 50 ? content.substring(0, 50) + "..." : content);
        entry.setOwner(currentUser);

        return entryRepository.save(entry);
    }

    public Entry updateEntry(Long id, String title, String content) {
        User currentUser = userService.getCurrentUser();

        Entry entry = entryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Entry not found"));
        if (!entry.getOwner().getId().equals(currentUser.getId())){
            throw new IllegalStateException("You're not allowed to edit this entry!!!");
        }
        entry.setTitle(title);
        entry.setContent(content);
        entry.setSummary(content.length() > 50 ? content.substring(0, 50) + "..." : content);

        return entryRepository.save(entry);
    }

    public void deleteEntry(Long id) {
        User currentUser = userService.getCurrentUser();
        Entry entry = entryRepository.findById(id)
                        .orElseThrow(()-> new IllegalArgumentException("Entry not found"));
        if(!entry.getOwner().getId().equals(currentUser.getId())){
            throw new IllegalStateException("You're not allowed to delete this entry!!!");
        }
        entryRepository.deleteById(id);
    }

    public List<Entry> getAllEntries() {
        User currentUser = userService.getCurrentUser();
        return entryRepository.findAll()
                .stream()
                .filter(entry -> entry.getOwner().getId().equals(currentUser.getId()))
                .collect(Collectors.toList());
    }

    public Entry getEntryById(Long id) {
        User currentUser = userService.getCurrentUser();
        Entry entry = entryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entry not found"));
        if(!entry.getOwner().getId().equals(currentUser.getId())){
            throw new IllegalStateException("You're not allowed to view this entry!!!");
        }
        return entry;
    }
    public void deleteImage(Long id) {
        Entry entry = entryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entry not found"));

        entry.setImage(null);
        entryRepository.save(entry);
    }


}
