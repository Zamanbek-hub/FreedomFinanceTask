import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Keyboard {

    //First Menu
    public static void setButtons_first(SendMessage sendMessage) throws IOException { // Create Keyboards
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup); // connecting message to keyboard
        replyKeyboardMarkup.setSelective(true); // selecting particular users
        replyKeyboardMarkup.setResizeKeyboard(true); // size optimize
        replyKeyboardMarkup.setOneTimeKeyboard(false); // hide after push

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow firstRow = new KeyboardRow(); // row

        firstRow.add(new KeyboardButton("USD")); // add button in row
        firstRow.add(new KeyboardButton("EUR"));
        firstRow.add(new KeyboardButton("RUB"));
        keyboardRows.add(firstRow);

        KeyboardRow secondRow = new KeyboardRow(); // row

        secondRow.add(new KeyboardButton("Advice")); // add button in row
        secondRow.add(new KeyboardButton("About Creator"));
        keyboardRows.add(secondRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows); // install on a telegram chat
    }

    //Second Menu
    public static void setButtons_second(SendMessage sendMessage) throws IOException {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        replyKeyboardMarkup.setSelective(true); // selecting particular users
        replyKeyboardMarkup.setResizeKeyboard(true); // size optimize
        replyKeyboardMarkup.setOneTimeKeyboard(false); // hide after push

        Bot.cur = sendMessage.getText().substring(sendMessage.getText().indexOf("1") + 2, sendMessage.getText().indexOf("=") - 1);
        Bot.desc = Currency.getDescription(Bot.cur);

        KeyboardRow firstRow = new KeyboardRow(); // row
        firstRow.add(new KeyboardButton("Converting")); // add button in row


        KeyboardRow secondRow = new KeyboardRow(); // row
        secondRow.add(new KeyboardButton("Back")); // add button in row

        keyboardRows.add(firstRow);
        keyboardRows.add(secondRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);

    }

    //Third Menu
    public static void setButtons_third(SendMessage sendMessage) throws IOException {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        replyKeyboardMarkup.setSelective(true); // selecting particular users
        replyKeyboardMarkup.setResizeKeyboard(true); // size optimize
        replyKeyboardMarkup.setOneTimeKeyboard(false); // hide after push

        KeyboardRow firstRow = new KeyboardRow(); // row
        firstRow.add(new KeyboardButton(Bot.cur + " -> KZT")); // add button in row
        firstRow.add(new KeyboardButton("KZT -> " + Bot.cur));

        KeyboardRow secondRow = new KeyboardRow(); // row
        secondRow.add(new KeyboardButton("Back"));

        keyboardRows.add(firstRow);
        keyboardRows.add(secondRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }
}
