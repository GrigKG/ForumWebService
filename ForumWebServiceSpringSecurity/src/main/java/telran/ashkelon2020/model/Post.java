package telran.ashkelon2020.model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = { "id" })
@ToString
@Document(collection = "ForumPosts")
public class Post {
	String id;
	@Setter 
	String title;
	@Setter 
	String content; 
	String author;
	LocalDateTime dateCreated;
	@Setter
	Set<String> tags;
	int likes;
	List<Comment> comments = new ArrayList<>();
	public Post(String title, String content, String author, Set<String> tags) {
		this.title = title;
		this.content = content;
		this.author = author;
		this.tags = tags;
		dateCreated = LocalDateTime.now();
	}
	
	public Post(String title, String content, String author) {
		this(title, content, author, new HashSet<>());
	}
	
	public void addLike() {
		likes++;
	}
	
	public boolean addComment(Comment comment) {
		return comments.add(comment);
	}
	
	public boolean addTag(String tag) {
		return tags.add(tag);
	}
	
	public boolean removeTag(String tag) {
		return tags.remove(tag);
	}
	
	
}