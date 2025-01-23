package com.cydeo.steps;

import com.cydeo.pages.BasePage;
import com.cydeo.pages.BookPage;
import com.cydeo.pages.UsersPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;

public class LiveSessionStepDefs {
    String actualUserCount;
    String actualBookCount;


    UsersPage usersPage = new UsersPage();

    @When("the user gets {string} user count")
    public void the_user_gets_user_count(String status) {
        BrowserUtil.selectByVisibleText(usersPage.userStatusDropdown, status);
        BrowserUtil.waitFor(2);

        String userDetails = usersPage.userCount.getText();
        actualUserCount = usersPage.getCount(userDetails);


        System.out.println("actualUserCount = " + actualUserCount);


    }

    @When("the {string} user count should be equal database")
    public void the_user_count_should_be_equal_database(String status) {
        String query = "SELECT COUNT(*) FROM users WHERE status = '" + status + "' AND user_group_id <> 1";
        DB_Util.runQuery(query);

        String expectedCount = DB_Util.getFirstRowFirstColumn();

        Assert.assertEquals(expectedCount, actualUserCount);
    }

    BookPage bookPage = new BookPage();

    @When("the user gets {string} book count")
    public void the_user_gets_book_count(String categoryName) {
        BrowserUtil.waitFor(1);
        BrowserUtil.selectByVisibleText(bookPage.mainCategoryElement, categoryName);
        BrowserUtil.waitFor(1);
        actualBookCount = usersPage.getCount(usersPage.booksCount.getText());
        System.out.println("actualBookCount = " + actualBookCount);

    }

    @Then("the {string} book count should be equal with database")
    public void the_book_count_should_be_equal_with_database(String category) {
        String query = "select count(*),bc.name  from book_categories bc join books b " +
                "on bc.id = b.book_category_id where bc.name = '" + category + "' group by name";
        DB_Util.runQuery(query);
        String expectedBookCount = DB_Util.getFirstRowFirstColumn();
        System.out.println("expectedBookCount = " + expectedBookCount);
        Assert.assertEquals(expectedBookCount, actualBookCount);

    }
}
