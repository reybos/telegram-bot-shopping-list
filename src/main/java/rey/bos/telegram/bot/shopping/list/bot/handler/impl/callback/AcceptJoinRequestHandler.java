package rey.bos.telegram.bot.shopping.list.bot.handler.impl.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import rey.bos.telegram.bot.shopping.list.bot.util.AcceptJoinRequestHelper;
import rey.bos.telegram.bot.shopping.list.bot.util.BotUtil;
import rey.bos.telegram.bot.shopping.list.bot.util.MessageUtil;
import rey.bos.telegram.bot.shopping.list.io.entity.JoinRequest;
import rey.bos.telegram.bot.shopping.list.io.entity.UserShoppingList;
import rey.bos.telegram.bot.shopping.list.service.JoinRequestService;
import rey.bos.telegram.bot.shopping.list.service.UserService;
import rey.bos.telegram.bot.shopping.list.service.UserShoppingListService;
import rey.bos.telegram.bot.shopping.list.shared.dto.UserDto;

import java.util.Optional;

import static rey.bos.telegram.bot.shopping.list.bot.dictionary.DictionaryKey.JOIN_REQUEST_ACCEPTED_SENDER;
import static rey.bos.telegram.bot.shopping.list.bot.handler.impl.callback.CallBackCommand.ACCEPT_JOIN_REQUEST;

@Slf4j
@Component
public class AcceptJoinRequestHandler extends BotHandlerDecision {

    private final JoinRequestService joinRequestService;
    private final BotUtil botUtil;
    private final AcceptJoinRequestHelper acceptJoinRequestHelper;
    private final UserService userService;
    private final UserShoppingListService userShoppingListService;
    private final TransactionTemplate transactionTemplate;

    public AcceptJoinRequestHandler(
        MessageUtil messageUtil, JoinRequestService joinRequestService, BotUtil botUtil,
        AcceptJoinRequestHelper acceptJoinRequestHelper, UserService userService,
        UserShoppingListService userShoppingListService, TransactionTemplate transactionTemplate
    ) {
        super(ACCEPT_JOIN_REQUEST, messageUtil);
        this.joinRequestService = joinRequestService;
        this.botUtil = botUtil;
        this.acceptJoinRequestHelper = acceptJoinRequestHelper;
        this.userService = userService;
        this.userShoppingListService = userShoppingListService;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public boolean handleAccept(UserDto user, int messageId, long callbackId) {
        Optional<JoinRequest> joinRequestO = joinRequestService.findRequest(user.getId(), messageId);
        if (joinRequestO.isEmpty()) {
            EditMessageText message = acceptJoinRequestHelper.buildCantFindActiveJoinRequest(user, messageId);
            botUtil.executeMethod(message);
            return true;
        }
        JoinRequest joinRequest = joinRequestO.get();
        try {
            transactionTemplate.execute(status -> {
                UserShoppingList activeList = userShoppingListService.findActiveUserShoppingList(user.getId());
                if (!activeList.isOwner()) {
                    activeList = userShoppingListService.restoreMainList(user.getId(), activeList);
                }
                userShoppingListService.changeSenderActiveList(joinRequest, activeList);
                return null;
            });
        } catch (IllegalStateException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        UserDto sender = userService.findByIdOrThrow(joinRequest.getUserId());
        EditMessageText ownerMessage = acceptJoinRequestHelper.buildJoinRequestAcceptedOwner(
            user, messageUtil.getLogin(sender.getUserName()), messageId
        );
        botUtil.executeMethod(ownerMessage);
        String senderMessage = botUtil.getText(sender.getLanguageCode(), JOIN_REQUEST_ACCEPTED_SENDER)
            .formatted(messageUtil.getLogin(user.getUserName()));
        botUtil.sendMessage(sender.getTelegramId(), senderMessage);
        return true;
    }

    @Override
    public boolean handleReject(UserDto user, int messageId, long callbackId) {
        Optional<JoinRequest> joinRequestO = joinRequestService.rejectRequest(user.getId(), messageId);
        if (joinRequestO.isEmpty()) {
            EditMessageText message = acceptJoinRequestHelper.buildCantFindActiveJoinRequest(user, messageId);
            botUtil.executeMethod(message);
            return true;
        }
        JoinRequest joinRequest = joinRequestO.get();
        UserDto sender = userService.findByIdOrThrow(joinRequest.getUserId());
        String senderLogin = messageUtil.getLogin(sender.getUserName());
        EditMessageText ownerMsg = acceptJoinRequestHelper.buildOwnerMsgJoinRequestRejected(
            user, senderLogin, messageId
        );
        botUtil.executeMethod(ownerMsg);
        String ownerLogin = messageUtil.getLogin(user.getUserName());
        SendMessage senderMessage = acceptJoinRequestHelper.buildSenderMsgJoinRequestRejected(
            sender, ownerLogin
        );
        botUtil.executeMethod(senderMessage);
        return true;
    }

    @Override
    public boolean support(Update update) {
        return supportCallbackCommand(update, command);
    }

}
