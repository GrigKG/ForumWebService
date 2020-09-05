package telran.ashkelon2020.forum.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseDTO {
	String id;
	String title;
	String content;
	String author;
	LocalDateTime dateCreated;
	Set<String>tags;
	Integer likes;
	List<CommentResponseDTO> comments;
}
