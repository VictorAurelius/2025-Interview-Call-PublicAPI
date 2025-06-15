package interview.callpulicapi.mapper;

import interview.callpulicapi.model.dto.PostDto;
import interview.callpulicapi.model.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDto toDto(Post post);

    Post toEntity(PostDto postDto);
}