package com.cydeo.steps;

import com.cydeo.pages.BookPage;
import com.cydeo.pages.DashBoardPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import com.cydeo.utility.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.eo.Se;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class BooksStepDefs {
    BookPage bookPage = new BookPage();
    List<String> actualCategoryList;


    @When("the user navigates to {string} page")
    public void the_user_navigates_to_page(String moduleName) {
        new DashBoardPage().navigateModule(moduleName);


    }


    @When("the user gets all book categories in webpage")
    public void the_user_gets_all_book_categories_in_webpage() {
        actualCategoryList = BrowserUtil.getAllSelectOptions(bookPage.mainCategoryElement);
        actualCategoryList.remove(0);
        System.out.println("actualCategoryList = " + actualCategoryList);
    }

    @Then("user should be able to see following categories")
    public void user_should_be_able_to_see_following_categories(List<String> expectedCategoryList) {


        Assert.assertEquals(expectedCategoryList, actualCategoryList);

    }


    @When("I open book {string}")
    public void i_open_book(String bookName) {

        System.out.println("bookName = " + bookName);
        BrowserUtil.waitForClickablility(bookPage.search, 5).sendKeys(bookName);
        BrowserUtil.waitForClickablility(bookPage.editBook(bookName), 5).click();

    }

    @Then("verify book categories must match book categories table from db")
    public void verify_book_categories_must_match_book_categories_table_from_db() {
        String query = "select name from book_categories";
        DB_Util.runQuery(query);
        List<String> expectedCategoryList = DB_Util.getColumnDataAsList(1);

        Assert.assertEquals(actualCategoryList, expectedCategoryList);
    }

    @Then("book information must match the database for {string}")
    public void book_information_must_match_the_database_for(String string)  {
        //DB info
        String query = "SELECT b.name, isbn, year, author, b.description\n" +
                "FROM books b\n" +
                "         join book_categories c On b.book_category_id = c.id\n" +
                "where b.name = '" + string + "'";
        DB_Util.runQuery(query);
        List<String> expectedBookInformation = DB_Util.getRowDataAsList(1);
        //UI info
        List<String> actualBookInformation = new ArrayList<>();
        System.out.println("name = " + bookPage.bookName.getAttribute("value"));
        actualBookInformation.add(bookPage.bookName.getAttribute("value"));
        actualBookInformation.add(bookPage.isbn.getAttribute("value"));
        actualBookInformation.add(bookPage.year.getAttribute("value"));
        actualBookInformation.add(bookPage.author.getAttribute("value"));
        //actualBookInformation.add(dropdown.getFirstSelectedOption().getAttribute("value"));
        actualBookInformation.add(bookPage.description.getAttribute("value"));
        System.out.println(actualBookInformation+"\n"+expectedBookInformation);
        Assert.assertEquals(actualBookInformation,expectedBookInformation);


    }

}
