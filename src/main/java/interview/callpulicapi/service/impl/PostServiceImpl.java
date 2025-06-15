package interview.callpulicapi.service.impl;

import interview.callpulicapi.exception.PostApiException;
import interview.callpulicapi.mapper.PostMapper;
import interview.callpulicapi.model.dto.PostDto;
import interview.callpulicapi.model.entity.Post;
import interview.callpulicapi.repository.PostRepository;
import interview.callpulicapi.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final RestTemplate restTemplate;

    @Value("${api.jsonplaceholder.base-url}")
    private String baseUrl;

    @Override
    public void fetchAndSavePostsFromApi() {
        fetchAndSavePostsFromApi(0);
    }

    @Override
    public void fetchAndSavePostsFromApi(int limit) {
        try {
            log.info("Fetching posts from JSONPlaceholder API" + (limit > 0 ? " with limit: " + limit : ""));
            PostDto[] posts = restTemplate.getForObject(baseUrl + "/posts", PostDto[].class);

            if (posts == null) {
                throw new PostApiException("Failed to fetch posts from API: Response was null");
            }

            List<PostDto> validPosts = Arrays.asList(posts);
            if (limit > 0 && limit < validPosts.size()) {
                validPosts = validPosts.subList(0, limit);
            }

            log.info("Saving {} posts to database", validPosts.size());
            postRepository.saveAll(
                    validPosts.stream()
                            .map(postMapper::toEntity)
                            .collect(Collectors.toList()));

            log.info("Successfully saved posts to database");
        } catch (Exception e) {
            log.error("Error occurred while fetching and saving posts: {}", e.getMessage());
            throw new PostApiException("Failed to fetch and save posts: " + e.getMessage());
        }
    }

    @Override
    public List<PostDto> getAllPosts() {
        try {
            log.info("Retrieving all posts from database");
            return postRepository.findAll().stream()
                    .map(postMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while retrieving posts: {}", e.getMessage());
            throw new PostApiException("Failed to retrieve posts: " + e.getMessage());
        }
    }

    @Override
    public Page<PostDto> getAllPosts(Pageable pageable) {
        try {
            log.info("Retrieving posts with pagination: page={}, size={}",
                    pageable.getPageNumber(), pageable.getPageSize());
            Page<Post> postPage = postRepository.findAll(pageable);
            return postPage.map(postMapper::toDto);
        } catch (Exception e) {
            log.error("Error occurred while retrieving paginated posts: {}", e.getMessage());
            throw new PostApiException("Failed to retrieve paginated posts: " + e.getMessage());
        }
    }

    @Override
    public PostDto getPostById(Long id) {
        try {
            log.info("Retrieving post with id: {}", id);
            return postRepository.findById(id)
                    .map(postMapper::toDto)
                    .orElseThrow(() -> new PostApiException("Post not found with id: " + id));
        } catch (PostApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while retrieving post with id {}: {}", id, e.getMessage());
            throw new PostApiException("Failed to retrieve post: " + e.getMessage());
        }
    }
}