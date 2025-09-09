package it.elca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.elca.dto.GenerationResponse;
import it.elca.generate.ProjectConfig;
import it.elca.service.GenerationService;

@RestController
@RequestMapping("/api")
public class GenerationController {

    @Autowired
    private GenerationService generationService;

    @Value("${info.app.version}")
    private String appVersion;

    @GetMapping("/version")
    public ResponseEntity<String> getVersion() {
        return ResponseEntity.ok(appVersion);
    }

    @PostMapping("/generate")
    public ResponseEntity<GenerationResponse> generateProject(@RequestBody ProjectConfig projectConfig) {
        try {
            generationService.generate(projectConfig);
            GenerationResponse response = new GenerationResponse("SUCCESS", "Project generated successfully", projectConfig.getPathname(), projectConfig);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            GenerationResponse response = new GenerationResponse("ERROR", "Failed to generate project: " + e.getMessage(), null, projectConfig);
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
