package interview.callpulicapi.service;

import interview.callpulicapi.model.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    void fetchAndSavePostsFromApi();

    void fetchAndSavePostsFromApi(int limit);

    List<PostDto> getAllPosts();

    Page<PostDto> getAllPosts(Pageable pageable);

    PostDto getPostById(Long id);
}