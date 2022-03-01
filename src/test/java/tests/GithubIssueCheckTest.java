package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pages.GithubAllureStepsAnnotationPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class GithubIssueCheckTest {

    private GithubAllureStepsAnnotationPage stepsPage = new GithubAllureStepsAnnotationPage();
    private static final String
            TEST_URL = "https://github.com",
            TEST_REPOSITORY = "eroshenkoam/allure-example",
            TEST_ISSUE = "С Новым Годом (2022)";

    @BeforeEach
    void precondition() {
        Configuration.browserSize = "1920x1080";
    }

    @AfterEach
    void closeBrowser() {
        closeWebDriver();
    }

    @Test
    @Owner("KELONMYOSA")
    @Feature("Изучаем подходы к разметке в Allure")
    @DisplayName("Проверка видимости Issue через Listener")
    @Link(value = "Testing URL", url = TEST_URL)
    public void githubIssueCheckTest() {
        Allure.parameter("Название репозитория", TEST_REPOSITORY);
        Allure.parameter("Название Issue", TEST_ISSUE);

        SelenideLogger.addListener("allure", new AllureSelenide());

        open(TEST_URL);

        $("[data-test-selector=\"nav-search-input\"]").click();
        $("[data-test-selector=\"nav-search-input\"]").setValue(TEST_REPOSITORY).pressEnter();
        $(By.linkText(TEST_REPOSITORY)).click();
        $(By.partialLinkText("Issue")).click();

        $(withText(TEST_ISSUE)).shouldBe(visible);
    }

    @Test
    @Owner("KELONMYOSA")
    @Feature("Изучаем подходы к разметке в Allure")
    @DisplayName("Проверка видимости Issue через лямбда-шаги")
    @Link(value = "Testing URL", url = TEST_URL)
    public void githubIssueCheckTestSteps() {
        Allure.parameter("Название репозитория", TEST_REPOSITORY);
        Allure.parameter("Название Issue", TEST_ISSUE);

        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем главную страницу GitHub", () -> open(TEST_URL));
        step("Ищем репозиторий " + TEST_REPOSITORY, () -> {
            $("[data-test-selector=\"nav-search-input\"]").click();
            $("[data-test-selector=\"nav-search-input\"]").setValue(TEST_REPOSITORY).pressEnter();
        });
        step("Открываем репозиторий " + TEST_REPOSITORY, () -> $(By.linkText(TEST_REPOSITORY)).click());
        step("Переходим во вкладку \"Issue\"", () -> $(By.partialLinkText("Issue")).click());
        step("Проверяем наличие заголовка \"" + TEST_ISSUE + "\"", () -> $(withText(TEST_ISSUE)).shouldBe(visible));
    }

    @Test
    @Owner("KELONMYOSA")
    @Feature("Изучаем подходы к разметке в Allure")
    @DisplayName("Проверка видимости Issue через аннотацию @Step")
    @Link(value = "Testing URL", url = TEST_URL)
    public void githubIssueCheckTestAnnotationSteps() {
        Allure.parameter("Название репозитория", TEST_REPOSITORY);
        Allure.parameter("Название Issue", TEST_ISSUE);
        stepsPage
                .openPage(TEST_URL)
                .searchForRepository(TEST_REPOSITORY)
                .openRepository(TEST_REPOSITORY)
                .openIssueTab()
                .shouldSeeIssueText(TEST_ISSUE);
    }
}
