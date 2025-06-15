package interview.callpulicapi.service;

import interview.callpulicapi.model.dto.PostDto;
import java.util.List;

public interface PostService {
    void fetchAndSavePostsFromApi();

    List<PostDto> getAllPosts();
}