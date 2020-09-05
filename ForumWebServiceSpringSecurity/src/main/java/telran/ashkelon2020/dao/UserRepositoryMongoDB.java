package telran.ashkelon2020.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2020.model.user.UserAccount;

public interface UserRepositoryMongoDB extends MongoRepository<UserAccount, String> {
	UserAccount findByFirstName(String FirstName);
	UserAccount findByLastName(String LastName);
}
