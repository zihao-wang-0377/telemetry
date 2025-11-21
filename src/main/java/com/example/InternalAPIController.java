package com.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin(
        origins = "http://localhost:3000",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS},
        maxAge = 3600
)
public class InternalAPIController {

    private final Logger LOG = LoggerFactory.getLogger(InternalAPIController.class);

    @PostMapping
    public ResponseEntity<Void> log(@RequestBody LogEntry logEntry) {

        String logText = String.format(
                "[FRONTEND-LOG] level=%s time=%s message=%s",
                logEntry.getLevel(),
                logEntry.getTimestamp(),
                logEntry.getMessage()
        );

        switch (logEntry.getLevel().toUpperCase()) {
            case "ERROR" -> LOG.error(logText);
            case "WARN" -> LOG.warn(logText);
            default -> LOG.info(logText);
        }

        return ResponseEntity.ok().build();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LogEntry {
        private String message;
        private String level;
        private String timestamp;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return "LogEntry{" +
                    "message='" + message + '\'' +
                    ", level='" + level + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    '}';
        }
    }
}
