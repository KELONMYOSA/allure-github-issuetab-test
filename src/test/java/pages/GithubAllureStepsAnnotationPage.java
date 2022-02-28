package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class GithubAllureStepsAnnotationPage {

    @Step("Открываем главную страницу GitHub")
    public GithubAllureStepsAnnotationPage openPage(String testURl) {
        open(testURl);

        return this;
    }

    @Step("Ищем репозиторий {testRepository}")
    public GithubAllureStepsAnnotationPage searchForRepository(String testRepository) {
        $("[data-test-selector=\"nav-search-input\"]").click();
        $("[data-test-selector=\"nav-search-input\"]").setValue(testRepository).pressEnter();

        return this;
    }

    @Step("Открываем репозиторий {testRepository}")
    public GithubAllureStepsAnnotationPage openRepository(String testRepository) {
        $(By.linkText(testRepository)).click();

        return this;
    }

    @Step("Переходим во вкладку \"Issue\"")
    public GithubAllureStepsAnnotationPage openIssueTab() {
        $(By.partialLinkText("Issue")).click();

        return this;
    }

    @Step("Проверяем наличие заголовка \"{testIssue}\"")
    public void shouldSeeIssueText(String testIssue) {
        $(withText(testIssue)).shouldBe(visible);
    }
}
