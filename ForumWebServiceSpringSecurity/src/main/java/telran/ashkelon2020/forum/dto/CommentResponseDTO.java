package telran.ashkelon2020.forum.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDTO {
	String user;
	String message;
	LocalDateTime dateCreated;
	Integer likes;
}
