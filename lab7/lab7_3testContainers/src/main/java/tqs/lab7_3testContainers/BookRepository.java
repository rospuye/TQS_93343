package tqs.lab7_3testContainers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    public Optional<Book> findById(Long id);
    public List<Book> getByName(String name);
    public List<Book> getByAuthor(String author);
    
}
