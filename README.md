## FreedomFinanceTask 
## > Run file CurrencyBot 
# Telegram Bot on Java☕ to currency exchange. This project done to vacancy of Freedom Finance
**Этот Телеграм Бот Обменник Валют был сделан на языке Java☕ </br>**
Freedom_Finance_Bot [currencyTengeJavabot](t.me/currencyTengeJavabot).

***Что умеет этот Бот:***
``` 
Показывать в режиме реального времени курсы Валют(USD,EUR,RUB) относительно к Тенге(KZT) 💰
Конвертировать Валюту в Тенге(KZT) и обратно 🔗
```
***Бот имеет 3 уровня меню***

Начальное меню:
```
USD,EUR,RUB
Advice, About Creator
```

Среднее меню:
```
Converting,
Back
```

Последнее меню:
```
Currency -> KZT, KZT -> Currency
Back
```

# Источник Курс Валют
Все курсы валют взяты из National Bank KZ [nationalBankKZ](https://nationalbank.kz/?docid=3346&switch=russian).

> В этом проекте мы не используем API_KEY </br> так как nationalBankKZ предоставляет нам ответ </br> по ссылке в виде XML

``` 
  URL url = new URL("https://nationalbank.kz/rss/rates_all.xml?switch=russian"); // Url Response
  Scanner in = new Scanner((InputStream) url.getContent());
```

> Так как наш Response приходит в виде XMl </br> мы конвертируем его в JSON
``` 
        String result = "";
        while(in.hasNext()){
            result += in.nextLine();
        }

        System.out.println(result);

        // Convert XML to JSON
        JSONObject xmlJSONObj = XML.toJSONObject(result);
```

**Json object after filter responce**:
``` 
    {
        "change": 0,
        "link": "",
        "description": 5.73,
        "index": "",
        "title": "RUB",
        "quant": 1,
        "pubDate": "18.05.20"
    },
```

> Дальще наш выбранный по валюте Наш JSONObject конвертируем в object Model который мы создали </br> и дальше работаем с Model : 
``` 
      for(int i = 0; i < getArray.length(); i++){
            JSONObject obj = getArray.getJSONObject(i);
            if(obj.getString("title").equals(cur)){
                model.setChange(obj.getDouble("change"));
                model.setDescription(obj.getDouble("description"));
                model.setTitle(obj.getString("title"));

            }
        }
```

***Maven<br>***
**Dependencies:**
``` 
    <dependencies>
            <dependency>
                <groupId>org.telegram</groupId>
                <artifactId>telegrambots</artifactId>
                <version>4.8.1</version>
            </dependency>
            <dependency>
                <groupId>org.json</groupId>
                <artifactId>json</artifactId>
                <version>20180813</version>
            </dependency>
            <dependency>
                <groupId>org.apache.commons</groupId>
                <artifactId>commons-lang3</artifactId>
                <version>3.9</version>
            </dependency>
        </dependencies>
 ```
