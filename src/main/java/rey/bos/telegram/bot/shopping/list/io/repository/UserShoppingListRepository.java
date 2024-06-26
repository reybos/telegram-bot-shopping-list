package rey.bos.telegram.bot.shopping.list.io.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rey.bos.telegram.bot.shopping.list.io.entity.UserShoppingList;
import rey.bos.telegram.bot.shopping.list.io.repository.params.UserShoppingListGroupParams;

import java.util.List;

@Repository
public interface UserShoppingListRepository extends CrudRepository<UserShoppingList, Long> {

    List<UserShoppingList> findByUserId(long userId);

    @Query(
        """
        SELECT ul.user_id AS user_id,
            ul.owner AS owner,
            u.user_name AS user_name,
            ul.id AS user_list_id
        FROM users_list ul
            LEFT JOIN users u on u.id = ul.user_id
        WHERE ul.list_id = :listId
            AND ul.active
        """
    )
    List<UserShoppingListGroupParams> findActiveGroupByListId(@Param("listId") long listId);

    @Query(
        """
        SELECT ul.user_id AS user_id,
            ul.owner AS owner,
            u.user_name AS user_name,
            ul.id AS user_list_id
        FROM users_list ul
            LEFT JOIN users u on u.id = ul.user_id
        WHERE ul.id = :id
        """
    )
    UserShoppingListGroupParams getUserListParamsById(@Param("id") long id);

    @Query(
        """
        SELECT *
        FROM users_list ul
        WHERE ul.list_id = (SELECT list_id FROM users_list WHERE user_id = :userId AND active)
            AND ul.active
        """
    )
    List<UserShoppingList> findActiveGroupByUserId(@Param("userId") long userId);

    List<UserShoppingList> findByUserIdAndActive(@Param("userId") long userId, @Param("active") boolean active);

    List<UserShoppingList> findByUserIdAndOwner(@Param("userId") long userId, @Param("owner") boolean owner);

}
