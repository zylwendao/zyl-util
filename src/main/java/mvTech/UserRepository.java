package mvTech;

import mvTech.enumDemo.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select * from users where id = '1' ", nativeQuery = true)
    List<User> find1();

    @Override
    boolean existsById(Integer integer);

    long countById(Integer id);
}