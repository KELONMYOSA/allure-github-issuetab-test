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

    private static final GithubAllureStepsAnnotationPage stepsPage = new GithubAllureStepsAnnotationPage();
    private static final String
            TestURL = "https://github.com",
            TestRepository = "eroshenkoam/allure-example",
            TestIssue = "С Новым Годом (2022)";

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
    @Link(value = "Testing URL", url = TestURL)
    public void githubIssueCheckTest() {
        Allure.parameter("Название репозитория", TestRepository);
        Allure.parameter("Название Issue", TestIssue);

        SelenideLogger.addListener("allure", new AllureSelenide());

        open(TestURL);

        $("[data-test-selector=\"nav-search-input\"]").click();
        $("[data-test-selector=\"nav-search-input\"]").setValue(TestRepository).pressEnter();
        $(By.linkText(TestRepository)).click();
        $(By.partialLinkText("Issue")).click();

        $(withText(TestIssue)).shouldBe(visible);
    }

    @Test
    @Owner("KELONMYOSA")
    @Feature("Изучаем подходы к разметке в Allure")
    @DisplayName("Проверка видимости Issue через лямбда-шаги")
    @Link(value = "Testing URL", url = TestURL)
    public void githubIssueCheckTestSteps() {
        Allure.parameter("Название репозитория", TestRepository);
        Allure.parameter("Название Issue", TestIssue);

        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем главную страницу GitHub", () -> open(TestURL));
        step("Ищем репозиторий " + TestRepository, () -> {
            $("[data-test-selector=\"nav-search-input\"]").click();
            $("[data-test-selector=\"nav-search-input\"]").setValue(TestRepository).pressEnter();
        });
        step("Открываем репозиторий " + TestRepository, () -> $(By.linkText(TestRepository)).click());
        step("Переходим во вкладку \"Issue\"", () -> $(By.partialLinkText("Issue")).click());
        step("Проверяем наличие заголовка \"" + TestIssue + "\"", () -> $(withText(TestIssue)).shouldBe(visible));
    }

    @Test
    @Owner("KELONMYOSA")
    @Feature("Изучаем подходы к разметке в Allure")
    @DisplayName("Проверка видимости Issue через аннотацию @Step")
    @Link(value = "Testing URL", url = TestURL)
    public void githubIssueCheckTestAnnotationSteps() {
        Allure.parameter("Название репозитория", TestRepository);
        Allure.parameter("Название Issue", TestIssue);
        stepsPage
                .openPage(TestURL)
                .searchForRepository(TestRepository)
                .openRepository(TestRepository)
                .openIssueTab()
                .shouldSeeIssueText(TestIssue);
    }
}
