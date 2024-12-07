package com.example.demo.service;

import com.example.demo.entity.Entry;
import com.example.demo.exceptions.ApiException;
import com.example.demo.repository.EntryRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class EntryService {

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    public Entry createEntry(String title, String content) {

        User currentUser = userService.getCurrentUser(); // Получаем пользователя

        Entry entry = new Entry();
        entry.setTitle(title);
        entry.setContent(content);
        entry.setCreatedDate(LocalDateTime.now());
        entry.setSummary(content.length() > 50 ? content.substring(0, 50) + "..." : content);
        entry.setOwner(currentUser);  // Связываем запись с текущим пользователем

        return entryRepository.save(entry);  // Сохраняем запись
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
}
