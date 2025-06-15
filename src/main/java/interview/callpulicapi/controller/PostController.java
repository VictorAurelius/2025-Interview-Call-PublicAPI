package interview.callpulicapi.controller;

import interview.callpulicapi.model.dto.PostDto;
import interview.callpulicapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/fetch")
    public ResponseEntity<Void> fetchAndSavePosts() {
        postService.fetchAndSavePostsFromApi();
        return ResponseEntity.ok().build();
    }
}