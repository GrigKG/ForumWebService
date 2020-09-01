package telran.ashkelon2020.dao;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.repository.PagingAndSortingRepository;

import telran.ashkelon2020.model.Post;

public interface ForumRepositoryMongoDB extends PagingAndSortingRepository<Post, String> {
	Stream<Post> findByAuthor(String author);
	Stream<Post> findByTagsIn(Set<String> tags);
	Stream<Post> findByDateCreatedBetween(LocalDate from, LocalDate to);
}
