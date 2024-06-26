package rey.bos.telegram.bot.shopping.list.io.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rey.bos.telegram.bot.shopping.list.io.entity.JoinRequest;
import rey.bos.telegram.bot.shopping.list.io.repository.params.JoinRequestParams;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface JoinRequestRepository extends CrudRepository<JoinRequest, Long> {

    @Query(
        """
        SELECT jr.id AS requestId,
            '@' || u.user_name AS owner_user_name
        FROM join_request jr
        LEFT JOIN users u on u.id = jr.owner_id
        WHERE jr.user_id = :userId
            AND NOT jr.approved
            AND NOT jr.expired
            AND NOT jr.rejected
        """
    )
    List<JoinRequestParams> findActiveJoinRequest(@Param("userId") long userId);

    @Query(
        """
        SELECT *
        FROM join_request
        WHERE owner_id = :ownerId
            AND message_id = :messageId
            AND NOT approved
            AND NOT expired
            AND NOT rejected
        """
    )
    Optional<JoinRequest> findByOwnerIdAndMessageId(@Param("ownerId") long ownerId, @Param("messageId") int messageId);

    @Query(
        """
        SELECT *
        FROM join_request
        WHERE user_id = :userId
            AND NOT approved
            AND NOT expired
            AND NOT rejected
        """
    )
    List<JoinRequest> findActiveRequestByUserId(@Param("userId") long userId);

    @Query(
        """
        SELECT *
        FROM join_request
        WHERE created_at < :timeToExpire
            AND NOT approved
            AND NOT expired
            AND NOT rejected
        """
    )
    List<JoinRequest> findExpiredRequests(@Param("timeToExpire") Instant timeToExpire);

}
