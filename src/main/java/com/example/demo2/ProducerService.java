package com.example.demo2;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public int factorial(int n) {
        if (n == 0)
            return 1;

        return n * factorial(n - 1);
    }

    public void sendMessage(String topic, Object message) {
        Map<String, String> test1 = Map.of(
                "a", "b",
                "c", "d");
        this.kafkaTemplate.send("testTopic", "hey", test1);
    }

}
