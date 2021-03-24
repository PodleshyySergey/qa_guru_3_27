package tests;

import com.codeborne.selenide.Configuration;
import driver.LocalMobileDriver;
import io.appium.java_client.MobileBy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static io.appium.java_client.MobileBy.AccessibilityId;
import static io.qameta.allure.Allure.step;

public class LocalTests {

    @BeforeAll
    public static void configureSelenide() {
        Configuration.browser = LocalMobileDriver.class.getName();
        Configuration.startMaximized = false;
        Configuration.browserSize = null;
        Configuration.timeout = 10000;
    }

    @Test
    void searchTest() {
        step("Открытие приложения Wikipedia", ()-> {
            open();
        });
        step("Обращение к кнопке \"Back\".", ()-> {
            back();
        });
        step("Обращение к Поиску", ()-> {
            $(AccessibilityId("Поиск по Википедии")).click();
        });
        step("Ввод поискового запроса \"java\" в строку поиска.", ()-> {
            $(MobileBy.id("org.wikipedia.alpha:id/search_src_text")).setValue("java");
        });
        step("Проверка что список поисковой выдачи больше нуля.", ()-> {
            $$(MobileBy.id("org.wikipedia.alpha:id/search_results_list")).shouldHave(sizeGreaterThan(0));
        });
    }

    @Test
    void gettingStarted() {
        step("Открытие приложения Wikipedia", () -> {
            open();
            $(MobileBy.id("org.wikipedia.alpha:id/primaryTextView")).shouldHave(text("Свободная энциклопедия …более, чем на 300 языках"));
        });
        step("Первое обращение к \"Продолжить\" с проверкой текста", () -> {
            $(AccessibilityId("Продолжить")).click();
            $(MobileBy.id("org.wikipedia.alpha:id/primaryTextView")).shouldHave(text("Новые способы исследований"));
        });
        step("Второе обращение к \"Продолжить\" с проверкой текста", () -> {
            $(AccessibilityId("Продолжить")).click();
            $(MobileBy.id("org.wikipedia.alpha:id/primaryTextView")).shouldHave(text("Списки для чтения с синхронизацией"));
        });
        step("Третье обращение к \"Продолжить\" с проверкой текста", () -> {
            $(AccessibilityId("Продолжить")).click();
            $(MobileBy.id("org.wikipedia.alpha:id/primaryTextView")).shouldHave(text("Отправлять отчёты об использовании"));
        });
        step("Обращение к \"НАЧАТЬ\" с проверкой текста", () -> {
            $(MobileBy.id("org.wikipedia.alpha:id/fragment_onboarding_done_button")).click();
            $(MobileBy.id("org.wikipedia.alpha:id/view_announcement_text")).shouldHave(text("Настройте свою ленту Теперь вы можете выбрать, что будет отображаться в ленте, а также определить приоритеты для выбранных типов содержимого."));
        });

    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

}
