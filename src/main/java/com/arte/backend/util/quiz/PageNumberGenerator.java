package com.arte.backend.util.quiz;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class PageNumberGenerator {

    public String generateRandomPageNumber() {
        Random rand = new Random();
        return String.valueOf(rand.nextInt(100) + 1);
    }
}
