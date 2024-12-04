package com.example.demo.service;

import com.example.demo.entity.Entry;
import com.example.demo.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EntryService {

    @Autowired
    private EntryRepository entryRepository;

    public Entry createEntry(String title, String content) {
        Entry entry = new Entry();
        entry.setTitle(title);
        entry.setContent(content);
        entry.setCreatedDate(LocalDateTime.now());
        entry.setSummary(content.length() > 50 ? content.substring(0, 50) + "..." : content);

        return entryRepository.save(entry);
    }

    public Entry updateEntry(Long id, String title, String content) {
        Entry entry = entryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Entry not found"));
        entry.setTitle(title);
        entry.setContent(content);
        entry.setSummary(content.length() > 50 ? content.substring(0, 50) + "..." : content);

        return entryRepository.save(entry);
    }

    public void deleteEntry(Long id) {
        entryRepository.deleteById(id);
    }

    public List<Entry> getAllEntries() {
        return entryRepository.findAll();
    }

    public Entry getEntryById(Long id) {
        return entryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Entry not found"));
    }
}
