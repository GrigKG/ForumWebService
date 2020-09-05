package telran.ashkelon2020.forum.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
	String title;
	String content;
	Set<String>tags;
}
