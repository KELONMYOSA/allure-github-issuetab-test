package tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class GithubIssueCheckTest {

    @Test
    public void githubIssueCheckTest () {
        open("https://github.com/");
        $("[data-test-selector=\"nav-search-input\"]").click();
        $("[data-test-selector=\"nav-search-input\"]").setValue("eroshenkoam/allure-example").pressEnter();
        $(By.linkText("eroshenkoam/allure-example")).click();
        $(By.partialLinkText("Issue")).click();
        $(withText("С Новым Годом (2022)")).shouldBe(visible);
    }
}
