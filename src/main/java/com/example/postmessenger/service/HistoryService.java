package com.example.postmessenger.service;
import com.example.postmessenger.entity.History;
import com.example.postmessenger.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public History findById(Long id) {
        return historyRepository.findById(id).get();
    }

    public List<History> getAllHistory() {
        return historyRepository.findAll();
    }

    public History save(History history) {
        return historyRepository.save(history);
    }

    public void delete(Long id) {
        historyRepository.deleteById(id);
    }

    public History update(Long id, History updatedHistory) {
        Optional<History> optionalHistory = historyRepository.findById(id);
        if (optionalHistory.isPresent()) {
            History existingHistory = optionalHistory.get();
            existingHistory.setStatus(updatedHistory.getStatus());
            return historyRepository.save(existingHistory);
        } else {
            throw new IllegalArgumentException("History not found with id: " + id);
        }
    }
}
