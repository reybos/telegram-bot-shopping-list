package rey.bos.telegram.bot.shopping.list.bot.dictionary.impl;

import org.springframework.stereotype.Component;
import rey.bos.telegram.bot.shopping.list.bot.dictionary.Dictionary;
import rey.bos.telegram.bot.shopping.list.bot.dictionary.DictionaryKey;
import rey.bos.telegram.bot.shopping.list.io.LanguageCode;

import java.util.HashMap;
import java.util.Map;

import static rey.bos.telegram.bot.shopping.list.bot.dictionary.DictionaryKey.*;

@Component
public class DictionaryEn implements Dictionary {

    private final LanguageCode languageCode;
    private final Map<DictionaryKey, String> dictionary;

    public DictionaryEn() {
        languageCode = LanguageCode.EN;
        dictionary = new HashMap<>();
        addValues();
    }

    @Override
    public boolean isSuitable(LanguageCode languageCode) {
        return languageCode == this.languageCode;
    }

    @Override
    public String get(DictionaryKey key) {
        return dictionary.get(key);
    }

    private void addValues() {
        dictionary.put(SOMETHING_WENT_WRONG, """
            Something went wrong 😿 If problems persist, write to the creator of the bot @reybos, he will try to help.
            """
        );
        dictionary.put(ERROR_ITEM_ADD_TOO_LONG_ITEM, """
            Message is too long, should be no more than 30 characters.
            """
        );
        dictionary.put(ERROR_ITEM_ADD_TOO_LONG_LIST, """
            There are too many items in the current list. Delete unnecessary ones or clear the list completely with the /clear_list command
            """
        );
        dictionary.put(ACTION_ITEM_ADDED_TO_LIST, """
            The item was successfully added to the list
            """
        );
        dictionary.put(EMPTY_LIST_MESSAGE, """
            The list is empty, send a message to the bot to add an item to the list.
            """
        );
        dictionary.put(NOT_EMPTY_LIST_MESSAGE, """
            <b>Your list 📋</b>
                    
            Click on any item to delete it.
            """
        );
        dictionary.put(UNSUPPORTED_COMMAND, """
            The command is not supported, the list of available commands can be viewed in the menu or by calling the /help command
            """
        );
        dictionary.put(JOIN_COMMAND_MESSAGE, """
            To merge shopping list with another user, send the bot the username of another user, for example @login, we will send him a request to merge.
            When combining lists, you will use a shared list with another user, your current list will become inactive.
            """
        );
        dictionary.put(ERROR_TOO_MANY_MENTION_IN_JOIN, """
            You have specified too many users in the request, you can only join one
            """
        );
        dictionary.put(ERROR_HAS_ACTIVE_JOIN_REQUEST, """
            You have already made requests to merge with users: %s, wait for confirmation from them, or cancel the current requests to make a new one.
                                                                                   
            Cancel current applications?
            """
        );
        dictionary.put(REJECT_JOINING_PROCCESS, """
            The creation of a request to merge lists has been canceled.
            """
        );
        dictionary.put(ACTIVE_JOIN_REQUEST_CLEARED, """
            Active requests to merge lists have been canceled.
            Please re-send the login of the user you want to merge the lists with.
            """
        );
        dictionary.put(CONFIRM_MSG, """
            Yes ✅"""
        );
        dictionary.put(REJECT_MSG, """
            No ❌"""
        );
        dictionary.put(USER_NOT_EXIST, """
            %s not found among the bot users. To join a group with a user, ask them to start using this bot @shoppy_guru_bot
            """
        );
        dictionary.put(ERROR_SENDER_IS_OWNER_ACTIVE_GROUP, """
            You are currently in the same group as %s, which you own. To join a group with %s, you need to dissolve the current one, after that %s will use its own shopping list.
            
            Disband the current group?
            """
        );
        dictionary.put(ERROR_SENDER_IS_MEMBER_OF_GROUP, """
            You are currently in the same group as user %s. To join a group with %s, you need to exit the current one, after that the current shopping list will be unavailable.
            
            Quit the group?
            """
        );
        dictionary.put(OWNER_ACCEPT_JOIN_REQUEST_WITHOUT_ACTIVE_GROUP, """
            The user %s wants to share a shopping list with you. If you accept the request, you will become the owner of the list and will be able to add other users to the group.
            
            Do you accept the request?
            """
        );
        dictionary.put(OWNER_ACCEPT_JOIN_REQUEST_WITH_OWN_ACTIVE_GROUP, """
            User %s wants to share a shopping list with you. But you already keep a list with %s. If you accept the request, you will use the list together.
            
            Do you accept the request?
            """
        );
        dictionary.put(OWNER_ACCEPT_JOIN_REQUEST_WITH_ACTIVE_GROUP, """
            User %s wants to share a shopping list with you. If you accept the request, you will become the owner of the list and will be able to add other users to the group. You will also leave the current group with %s and stop using the current shopping list.
            
            Do you accept the request?
            """
        );
        dictionary.put(SEND_JOIN_REQUEST_SUCCESS, """
            A request to merge the lists has been sent to the %s user, the request will be valid for 1 day. If it is not accepted during this time, you will need to resend the request.
            """
        );
        dictionary.put(CANT_SEND_MESSAGE, """
            %s blocked the bot. Ask the user to start using it @shoppy_guru_bot and repeat the request
            """
        );
        dictionary.put(CANT_FIND_ACTIVE_JOIN_REQUEST, """
            The request to merge the lists was not found. 
            It may have already expired or been canceled earlier.
            """
        );
        dictionary.put(OWNER_MSG_JOIN_REQUEST_REJECTED, """
            The request to merge the lists with the user %s has been rejected.
            """
        );
        dictionary.put(SENDER_MSG_JOIN_REQUEST_REJECTED, """
            Your request to merge lists with user %s has been rejected.
            """
        );
        dictionary.put(ERROR_MENTION_THEMSELF, """
            You cannot specify yourself in the request to merge lists.
            """
        );
        dictionary.put(JOIN_REQUEST_ACCEPTED_OWNER, """
            You have accepted the request to merge shopping lists with the user %s.
            
            Now you are the owner of the group and will be able to accept other users into it, in which case you will use the list all together.
            """
        );
        dictionary.put(JOIN_REQUEST_ACCEPTED_SENDER, """
            The request to merge the lists with %s has been approved, now you are using one list.
            """
        );
        dictionary.put(GREETING_FOR_START, """
            Greetings %s! 👋
            I will help you easily keep a shopping list. You can use the list yourself or team up with other users. Add and edit purchases conveniently and quickly!
            
            🔸 Any message sent will be added to the list. Excluding commands and user mentions.
            🔸 Call the /list command to view the current list.
            🔸 Call the /join command and I'll tell you how to make one shared list with another user.
            🔸 Call the /show_group command to see who you are sharing a list with.
            🔸 Call the /change_language command to change the language.
            🔸 Call the /clear command and after confirmation, I will clear the entire current list.
            
            Creator: @reybos
            """
        );
        dictionary.put(CHANGE_LANGUAGE_COMMAND, """
            <b>Select the language of the bot</b>
            """
        );
        dictionary.put(ENGLISH_LANGUAGE, """
            EN 🇺🇸"""
        );
        dictionary.put(RUSSIAN_LANGUAGE, """
            RU 🇷🇺"""
        );
        dictionary.put(CHANGE_LANGUAGE_SUCCESS, """
            The language has been successfully changed ✅
            """
        );
        dictionary.put(CLEAR_LIST_COMMAND, """
            All items in the current list will be deleted.
            
            Clear it?
            """
        );
        dictionary.put(CLEAR_LIST_REJECTED, """
            The list cleanup command has been canceled.
            """
        );
        dictionary.put(CLEAR_LIST_ACCEPTED, """
            The current list has been cleared.
            """
        );
        dictionary.put(REFRESH_LIST_BUTTON, """
            refresh list %s"""
        );
        dictionary.put(EMPTY_GROUP_MESSAGE, """
            You haven't joined a group with anyone yet.
            Call the /join command to find out how to do this.
            """
        );
        dictionary.put(OWNER_GROUP_MESSAGE, """
            You are the owner of the group and can remove members from it.
            
            To remove from the group, click on the button with the desired user.
            """
        );
        dictionary.put(MEMBER_GROUP_MESSAGE, """
            You keep one list together with %s.
            
            You can exit the group by clicking on the button below.
            """
        );
        dictionary.put(LEAVE_GROUP_BUTTON, """
            leave the group 🚜"""
        );
        dictionary.put(LEAVE_GROUP_CONFIRM_MESSAGE, """
            You are going to leave the current group and keep your own list.
            
            Continue?
            """
        );
        dictionary.put(USER_LEFT_YOUR_GROUP_MESSAGE, """
            User %s has left your group and lost access to your list.
            """
        );
        dictionary.put(LEAVE_GROUP_CANCEL_MESSAGE, """
            The command to leave the group has been canceled
            """
        );
        dictionary.put(LEFT_GROUP_MESSAGE, """
            You left the group and now keep your own list.
            """
        );
        dictionary.put(REMOVE_USER_FROM_GROUP_CONFIRM_MESSAGE, """
            Remove %s from the group?
            """
        );
        dictionary.put(REMOVE_FROM_GROUP_CANCEL_MESSAGE, """
            The command to remove %s from the group has been canceled.
            """
        );
        dictionary.put(USER_REMOVED_FROM_GROUP_MESSAGE, """
            User %s has been removed from the group ✅
            """
        );
        dictionary.put(YOU_REMOVED_FROM_GROUP_MESSAGE, """
            User %s has deleted you from the group, now you are maintaining your own list.
            """
        );
        dictionary.put(ERROR_OWNER_CANT_LEAVE_GROUP, """
            You are the owner of the list and cannot leave the group.
            Delete all participants using the /show_group command and repeat the request.
            """
        );
        dictionary.put(LEAVE_GROUP_BEFORE_JOIN_SUCCESS_MESSAGE, """
            You have left the current group.
            Please re-send the username of the user you want to merge the lists with.
            """
        );
        dictionary.put(ERROR_MEMBER_CANT_DISBAND_GROUP, """
            You are a member of the group and cannot disband it.
            Leave the group using the /show_group command and repeat the request.
            """
        );
        dictionary.put(DISBAND_GROUP_SUCCESS_MESSAGE, """
            You have disbanded the current group.
            Please re-send the username of the user you want to merge the lists with.
            """
        );
        dictionary.put(JOIN_REQUEST_CANCELLED, """
            %s canceled the request to merge the list with you.
            """
        );
    }
}
