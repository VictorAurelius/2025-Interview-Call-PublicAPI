package interview.callpulicapi.controller;

import interview.callpulicapi.model.dto.PostDto;
import interview.callpulicapi.model.response.ApiResponse;
import interview.callpulicapi.service.PostService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostDto>>> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(ApiResponse.success("Posts retrieved successfully", posts));
    }

    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<Page<PostDto>>> getPagedPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<PostDto> posts = postService.getAllPosts(PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.success("Posts retrieved successfully", posts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDto>> getPostById(@PathVariable("id") Long id) {
        PostDto post = postService.getPostById(id);
        return ResponseEntity.ok(ApiResponse.success("Post retrieved successfully", post));
    }

    @GetMapping("/fetch")
    public ResponseEntity<ApiResponse<Void>> fetchAndSavePosts() {
        postService.fetchAndSavePostsFromApi();
        return ResponseEntity.ok(ApiResponse.success("Posts fetched and saved successfully"));
    }

    @GetMapping("/fetch/{limit}")
    public ResponseEntity<ApiResponse<Void>> fetchAndSavePosts(
            @PathVariable("limit") @Positive(message = "Limit must be a positive number") int limit) {
        postService.fetchAndSavePostsFromApi(limit);
        return ResponseEntity.ok(ApiResponse.success("Posts fetched and saved successfully with limit: " + limit));
    }
}