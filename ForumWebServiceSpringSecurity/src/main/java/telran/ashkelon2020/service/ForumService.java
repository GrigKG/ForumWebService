package telran.ashkelon2020.service;

import java.util.List;
import java.util.Set;

import telran.ashkelon2020.forum.dto.CommentDTO;
import telran.ashkelon2020.forum.dto.DatePeriodDTO;
import telran.ashkelon2020.forum.dto.PostDTO;
import telran.ashkelon2020.forum.dto.PostResponseDTO;

public interface ForumService {
	PostResponseDTO addPost(String author, PostDTO postDTO);
	PostResponseDTO findPostById(String id);
	PostResponseDTO deletePost(String id);
	PostResponseDTO updatePost(String id, PostDTO postDTO);
	Boolean addLikeToPost(String id);
	PostResponseDTO addCommentToPost(String id, String author, CommentDTO commentDTO);
	List<PostResponseDTO> findPostsByAuthor(String author);
	
	Iterable<PostResponseDTO> findPostsByTags(Set<String> tags);
	Iterable<PostResponseDTO> findPostsByDate(DatePeriodDTO datePeriodDTO);
	Iterable<CommentDTO> findAllComments(String id);
	Iterable<CommentDTO> findAllCommentsByAuthor(String id, String author);

}
