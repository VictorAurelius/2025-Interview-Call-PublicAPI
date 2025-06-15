package interview.callpulicapi.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;

    @NotNull(message = "userId must not be null")
    @Positive(message = "userId must be a positive number")
    private Long userId;

    @NotBlank(message = "title must not be blank")
    private String title;

    @NotBlank(message = "body must not be blank")
    private String body;
}