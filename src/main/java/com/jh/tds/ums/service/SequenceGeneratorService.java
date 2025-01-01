package com.jh.tds.ums.service;

import com.jh.tds.ums.model.Sequence;
import com.jh.tds.ums.repository.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorService {

    @Autowired
    private SequenceRepository sequenceRepository;

    private static final String SEQUENCE_NAME = "user_sequence";

    public String generateUserId() {
        // Retrieve the current sequence or create a new one if it doesn't exist
        Sequence sequence = sequenceRepository.findById(SEQUENCE_NAME)
                .orElseGet(this::createNewSequence);

        // Generate the user ID based on the current sequence
        int currentSeq = sequence.getSeq();

        // Generate the user ID (starting from user_001, user_002, etc.)
        String generatedId = String.format("user_%04d", currentSeq);

        // Increment the sequence counter for future users
        sequence.setSeq(currentSeq + 1);

        // Save the updated sequence value to the database
        sequenceRepository.save(sequence);

        // Return the generated user ID
        return generatedId;
    }

    private Sequence createNewSequence() {
        Sequence sequence = new Sequence();
        sequence.setId(SEQUENCE_NAME);
        sequence.setSeq(1);  // Start from user_001
        return sequenceRepository.save(sequence);
    }
}
