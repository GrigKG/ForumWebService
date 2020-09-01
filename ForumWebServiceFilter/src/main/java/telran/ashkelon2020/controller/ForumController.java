package telran.ashkelon2020.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2020.forum.dto.CommentDTO;
import telran.ashkelon2020.forum.dto.DatePeriodDTO;
import telran.ashkelon2020.forum.dto.PostDTO;
import telran.ashkelon2020.forum.dto.PostResponseDTO;
import telran.ashkelon2020.service.ForumService;

@RestController
@RequestMapping("/forum")
public class ForumController implements ForumService {
	
	@Autowired
	ForumService forumService;

	
	@PostMapping("/post/{author}")
	@Override
	public PostResponseDTO addPost(@PathVariable String author,@RequestBody PostDTO postDTO) {
		return forumService.addPost(author, postDTO);
	}

	@GetMapping("/post/{id}")
	@Override
	public PostResponseDTO findPostById(@PathVariable String id) {
		return forumService.findPostById(id);
	}

	@DeleteMapping("/post/{id}")
	@Override
	public PostResponseDTO deletePost(@PathVariable String id) {
		return forumService.deletePost(id);
	}
	
	@PutMapping("/post/{id}")
	@Override
	public PostResponseDTO updatePost(@PathVariable String id,@RequestBody PostDTO postDTO) {
		return forumService.updatePost(id, postDTO);
	}

	@PutMapping("/post/{id}/like")
	@Override
	public Boolean addLikeToPost(@PathVariable String id) {
		return forumService.addLikeToPost(id);
	}

	@PutMapping("/post/{id}/comment/{author}")
	@Override
	public PostResponseDTO addCommentToPost(@PathVariable String id, @PathVariable String author,@RequestBody CommentDTO commentDTO) {
		return forumService.addCommentToPost(id, author, commentDTO);
	}

	@GetMapping("/posts/author/{author}")
	@Override
	public List<PostResponseDTO> findPostsByAuthor(@PathVariable String author) {
		return forumService.findPostsByAuthor(author);
	}
	
	@PutMapping("/posts/tags")
	@Override
	public Iterable<PostResponseDTO> findPostsByTags(@RequestBody Set<String> tags) {
		return forumService.findPostsByTags(tags);
	}

	@PutMapping("/posts/period")
	@Override
	public Iterable<PostResponseDTO> findPostsByDate(@RequestBody DatePeriodDTO datePeriodDTO) {
		return forumService.findPostsByDate(datePeriodDTO);
	}
	@GetMapping("/posts/comments/{id}")
	@Override
	public Iterable<CommentDTO> findAllComments(@PathVariable String id) {
		return forumService.findAllComments(id);
	}
	
	@PutMapping("/posts/comments/{id}/author/{author}")
	@Override
	public Iterable<CommentDTO> findAllCommentsByAuthor(@PathVariable String id, @PathVariable String author) {
		return forumService.findAllCommentsByAuthor(id, author);
	}
	
	
}
