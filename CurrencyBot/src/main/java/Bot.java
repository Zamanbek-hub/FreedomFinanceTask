import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.regex.Pattern;

public class Bot  extends TelegramLongPollingBot {

    public static String cur; // current Currency
    public static double desc; // current Currency description
    public static boolean converting; // to know what type of converting
    public static boolean is_number; // to remove unwanted messages

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new Bot());
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    // sendMessage to User
    public void sendMsg(Message message, String text, int buttons){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString()); // define to which particular chat we should send Message
        sendMessage.setReplyToMessageId(message.getMessageId()); // define to which particular chat we should send Message
        sendMessage.setText(text.replaceAll("([_*`])", "\\\\$1")); // remove characters that don't refer to response
        try{
            if(buttons == 1) Keyboard.setButtons_first(sendMessage);
            else if(buttons == 2) Keyboard.setButtons_second(sendMessage);
            else if(buttons == 3) Keyboard.setButtons_third(sendMessage);
            execute(sendMessage); // send

        }catch (TelegramApiException | IOException e){
            e.printStackTrace();
        }

    }

    public void sendSticker(Message message, String stic_code) throws TelegramApiException {
        SendSticker sendSticker = new SendSticker();
        sendSticker.setSticker(stic_code);
        sendSticker.setChatId(message.getChatId().toString());
        execute(sendSticker);
    }

    //to round double after common
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage();
        String decimalPattern = "([0-9]*)\\.([0-9]*)"; // check to Double
        String integerPattern = "[+-]?[0-9][0-9]*"; // check to Integer

        if (message != null && message.hasText()) {
            System.out.println(message.getText());

            /* Start */
            if (message.getText().equals("/start")) {
                User user = message.getFrom();
                try {
                    sendSticker(message,"CAACAgIAAxkBAALWx17BTOWMdzZWqJS2ygFWY3KU4UGcAAKdAAPZvGoaM_IT9oY2Jn4ZBA");
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                sendMsg(message, "hello " + user.getFirstName() + "\nI am a currency bot \n With my help you can see the exchange rate in Tenge(KZT)", 1);
            }

            /* First Menu */
            else if (message.getText().equals("Advice")) {
                sendMsg(message, "Мистер Леприкон думает что будет лучше если вы купите доллар $", 1);
                try {
                    sendSticker(message, "CAACAgIAAxkBAALWgF7BJoiLStlrjzDrq2UpruP08SyxAAKhAAPZvGoaw2hVsC6XppEZBA");
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            else if (message.getText().equals("About Creator")) {
                sendMsg(message, "Turukbayev Zamanbek +77080505268", 1);
            }


            /* Second Menu */
            else if (message.getText().equals("Converting")) {
                sendMsg(message, "Choose type of converting ", 3);
            }

            else if(message.getText().equals("Back")){
                is_number = false;
                sendMsg(message, "Choose currency ", 1);
            }

            /* Third Menu */
            else if (message.getText().equals(Bot.cur + " -> KZT")){
                converting = true;
                is_number = true;
                sendMsg(message, "Enter the amount", 3);
            }

            else if (message.getText().equals("KZT -> " + Bot.cur)){
                converting = false;
                is_number = true;
                sendMsg(message, "Enter the amount", 3);
            }


            /* Converter */
            else if (Pattern.matches(decimalPattern, message.getText()) || Pattern.matches(integerPattern, message.getText())) { // Check to Numeric
                if (!message.getText().startsWith("-") && is_number) { // Check to Positive Numeric
                    try {
                        double d = Double.parseDouble(message.getText());
                        if(converting) {
                            double res = round(d * desc, 2);
                            sendMsg(message, res +" KZT", 3);
                        }

                        else {
                            double res = round(d / desc, 2);
                            sendMsg(message,res + " " + cur, 3);
                        }

                    } catch (NumberFormatException nfe) {
                        System.out.println("false value");
                        sendMsg(message, "Please enter true value", 1);
                    }
                } else { // Check to unwanted message
                    sendMsg(message, "Please enter true value", 1);
                }

            }

            // Currencies (Валюты)
            else {
                try {
                    model = Currency.getCurrency(message.getText());

                    if (model.getTitle() == null)
                        sendMsg(message, "Please enter buttons", 1);

                    String answer = "1 " + model.getTitle() + " = " + model.getDescription() + " KZT " + "\n if you want convert push - 'Converting'";
                    System.out.println("answer = " + answer);
                    sendMsg(message, answer, 2);
                } catch (IOException e) {
                    sendMsg(message, "There isn't such currency", 1);
                }
            }

        }
    }



    public String getBotUsername() {
        return "currencyBot";
    }

    public String getBotToken() {
        return "1170155406:AAGi4La8feDSz-YSZxYceLPR-S2NzHjoajI";
    }
}
