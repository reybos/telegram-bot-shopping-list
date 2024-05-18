package rey.bos.telegram.bot.shopping.list.bot.handler.impl.callback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import rey.bos.telegram.bot.shopping.list.bot.handler.BotHandler;
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
import static rey.bos.telegram.bot.shopping.list.bot.handler.impl.callback.CallBackCommand.CONFIRM;

@Slf4j
@Component
@RequiredArgsConstructor
public class AcceptJoinRequestHandler extends BotHandler {

    private final JoinRequestService joinRequestService;
    private final BotUtil botUtil;
    private final AcceptJoinRequestHelper acceptJoinRequestHelper;
    private final UserService userService;
    private final MessageUtil messageUtil;
    private final UserShoppingListService userShoppingListService;
    private final TransactionTemplate transactionTemplate;

    @Override
    public boolean handle(Update update, UserDto user) {
        CallbackQuery query = update.getCallbackQuery();
        String data = query.getData();
        long userId = messageUtil.getIdByText(data, ACCEPT_JOIN_REQUEST.getCommand());
        if (user.getId() != userId) {
            return false;
        }
        int messageId = query.getMessage().getMessageId();

        if (data.endsWith(CONFIRM.getCommand())) {
            try {
                handleAccept(user, messageId);
            } catch (IllegalStateException e) {
                log.error(e.getMessage(), e);
                return false;
            }
        } else {
            handleReject(user, messageId);
        }
        return true;
    }

    private void handleAccept(UserDto user, int messageId) {
        Optional<JoinRequest> joinRequestO = joinRequestService.findRequest(user.getId(), messageId);
        if (joinRequestO.isEmpty()) {
            EditMessageText message = acceptJoinRequestHelper.buildCantFindActiveJoinRequest(user, messageId);
            botUtil.executeMethod(message);
            return;
        }
        JoinRequest joinRequest = joinRequestO.get();
        transactionTemplate.execute(status -> {
            UserShoppingList activeList = userShoppingListService.findActiveUserShoppingList(user.getId());
            if (!activeList.isOwner()) {
                activeList = userShoppingListService.restoreMainList(user, activeList);
            }
            userShoppingListService.changeSenderActiveList(joinRequest, activeList);
            return null;
        });
        UserDto sender = userService.findUserById(joinRequest.getUserId());
        EditMessageText ownerMessage = acceptJoinRequestHelper.buildJoinRequestAcceptedOwner(
            user, messageUtil.getLogin(sender.getUserName()), messageId
        );
        botUtil.executeMethod(ownerMessage);
        String senderMessage = botUtil.getText(sender.getLanguageCode(), JOIN_REQUEST_ACCEPTED_SENDER)
            .formatted(messageUtil.getLogin(user.getUserName()));
        botUtil.sendMessage(sender.getTelegramId(), senderMessage);
    }

    private void handleReject(UserDto user, int messageId) {
        Optional<JoinRequest> joinRequestO = joinRequestService.rejectRequest(user.getId(), messageId);
        if (joinRequestO.isEmpty()) {
            EditMessageText message = acceptJoinRequestHelper.buildCantFindActiveJoinRequest(user, messageId);
            botUtil.executeMethod(message);
            return;
        }
        JoinRequest joinRequest = joinRequestO.get();
        UserDto sender = userService.findUserById(joinRequest.getUserId());
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
    }

    @Override
    public boolean support(Update update) {
        return supportCallbackCommand(update, ACCEPT_JOIN_REQUEST);
    }

}