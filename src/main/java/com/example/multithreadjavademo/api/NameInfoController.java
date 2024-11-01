package com.example.multithreadjavademo.api;
import com.example.multithreadjavademo.RemoteApiTester;
import com.example.multithreadjavademo.dto.NameInfoDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class NameInfoController {

    private final RemoteApiTester remoteApiTester;

    public NameInfoController(RemoteApiTester remoteApiTester) {
        this.remoteApiTester = remoteApiTester;
    }

    @GetMapping("/name-info")
    public Mono<NameInfoDTO> getNameInfo(@RequestParam String name) {
        return remoteApiTester.getNameInfo(name);
    }
}