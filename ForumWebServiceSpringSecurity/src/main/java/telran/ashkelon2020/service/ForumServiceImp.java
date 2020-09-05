package telran.ashkelon2020.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.ashkelon2020.dao.ForumRepositoryMongoDB;
import telran.ashkelon2020.forum.dto.CommentDTO;
import telran.ashkelon2020.forum.dto.DatePeriodDTO;
import telran.ashkelon2020.forum.dto.PostDTO;
import telran.ashkelon2020.forum.dto.PostResponseDTO;
import telran.ashkelon2020.forum.dto.exception.PostNotFoundException;
import telran.ashkelon2020.model.Comment;
import telran.ashkelon2020.model.Post;

@Service
public class ForumServiceImp implements ForumService {

	@Autowired
	ForumRepositoryMongoDB forumRepository;
	
	@Autowired
	ModelMapper modelMapper;
		
	
	private PostResponseDTO convertResponseDTO(Post post) {
		return modelMapper.map(post, PostResponseDTO.class);
	} 
	
	private Post getPostByID(String id) {
	return forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
	}
	
	@Override
	public PostResponseDTO addPost(String author, PostDTO postDTO) {
		Post post = new Post(postDTO.getTitle(), postDTO.getContent(), author, postDTO.getTags());
		return convertResponseDTO(forumRepository.save(post));
	}

	@Override
	public PostResponseDTO findPostById(String id) {
		return convertResponseDTO(getPostByID(id));
	}

	@Override
	public PostResponseDTO deletePost(String id) {
		forumRepository.deleteById(id);
		return convertResponseDTO(getPostByID(id));
	}

	@Override
	public PostResponseDTO updatePost(String id, PostDTO postDTO) {
		Post post = getPostByID(id);
		post.setContent(postDTO.getContent());
		post.setTitle(postDTO.getTitle());
		post.setTags(postDTO.getTags());
		return convertResponseDTO(forumRepository.save(post));
	}

	@Override
	public Boolean addLikeToPost(String id) {
		Post post = getPostByID(id);
		post.addLike();
		forumRepository.save(post);
		return true;
	}

	@Override
	public PostResponseDTO addCommentToPost(String id, String author, CommentDTO commentDTO) {
		Comment comment = new Comment(author, commentDTO.getMessage());
		Post post = getPostByID(id);
		post.addComment(comment);
		return convertResponseDTO(forumRepository.save(post));
	}

	@Override
	public List<PostResponseDTO> findPostsByAuthor(String author) {
		return forumRepository.findByAuthor(author)
				.map(s -> modelMapper.map(s, PostResponseDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<PostResponseDTO> findPostsByTags(Set<String> tags) {		
		return forumRepository.findByTagsIn(tags).map(s -> modelMapper.map(s, PostResponseDTO.class)).collect(Collectors.toList());
	}

	@Override
	public Iterable<PostResponseDTO> findPostsByDate(DatePeriodDTO datePeriodDTO) {			
		return forumRepository.findByDateCreatedBetween(datePeriodDTO.getDateFrom(), datePeriodDTO.getDateTo())
				.map(s -> modelMapper.map(s, PostResponseDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<CommentDTO> findAllComments(String id) {
		Post post = getPostByID(id);
		return post.getComments().stream().map(s -> modelMapper.map(s, CommentDTO.class)).collect(Collectors.toList());
	}

	@Override
	public Iterable<CommentDTO> findAllCommentsByAuthor(String id, String author) {
		Post post = getPostByID(id);
		return post.getComments()
				.stream()
				.filter(s -> author
				.equalsIgnoreCase(s.getUser()))
				.map(s -> modelMapper.map(s, CommentDTO.class))
				.collect(Collectors.toList());
	}
}
