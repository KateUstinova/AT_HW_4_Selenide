package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppCardDeliveryTest {

    @BeforeEach
    void openWeb() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }
    @Test
    void shouldValidApplicationData() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Новосибирск");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Екатерина Петрова-Водкина");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+79998887744");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        $x("//*[@data-test-id='notification']").shouldHave(Condition.text("Успешно! Встреча успешно забронирована на " + verificationDate), Duration.ofSeconds(15));
    }

    @Test
    void shouldNotValidName() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Новосибирск");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Kate");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+79998887744");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"name\"]").getText();
        assertEquals("Фамилия и имя\n" + "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
    }

    @Test
    void shouldZeroName() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Новосибирск");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+79998887744");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"name\"]").getText();
        assertEquals("Фамилия и имя\n" + "Поле обязательно для заполнения", text);
    }

    @Test
    void shouldNotValidCity() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Novosibirsk");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Екатерина Петрова-Водкина");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+79998887744");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"city\"]").getText();
        assertEquals("Доставка в выбранный город недоступна", text);
    }

    @Test
    void shouldZeroCity() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Екатерина Петрова-Водкина");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+79998887744");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"city\"]").getText();
        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void shouldNotValidDateMeet() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Новосибирск");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Екатерина Петрова-Водкина");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+79999999999");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[contains(text(),'Заказ на выбранную дату невозможен')]").getText();
        assertEquals("Заказ на выбранную дату невозможен", text);
    }
    //
    @Test
    void shouldZeroDate() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Новосибирск");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().format(DateTimeFormatter.ofPattern(""));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Екатерина Петрова-Водкина");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+79998887744");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[contains(text(),'Неверно введена дата')]").getText();
        assertEquals("Неверно введена дата", text);
    }


    @Test
    void shouldNotValidPhone() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Новосибирск");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Екатерина Петрова-Водкина");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("Море");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"phone\"]").getText();
        assertEquals("Мобильный телефон\n" + "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldZeroPhone() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Новосибирск");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Екатерина Петрова-Водкина");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"phone\"]").getText();
        assertEquals("Мобильный телефон\n" + "Поле обязательно для заполнения", text);
    }

    @Test
    void shouldNotValidFirstNumberPhone() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Новосибирск");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Екатерина Петрова-Водкина");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("89998887744");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"phone\"]").getText();
        assertEquals("Мобильный телефон\n" + "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldNotValidMorePhoneNumbers() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Новосибирск");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Екатерина Петрова-Водкина");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+799988877445");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"phone\"]").getText();
        assertEquals("Мобильный телефон\n" + "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldNotValidLessPhoneNumbers() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Новосибирск");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Екатерина Петрова-Водкина");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+799988877");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"phone\"]").getText();
        assertEquals("Мобильный телефон\n" + "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldNotValidCheckBox() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Новосибирск");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("Екатерина Петрова-Водкина");
        $x("//input[@name=\"phone\"]").setValue("+7999887744");
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"agreement\"]").getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных", text);
    }

}
