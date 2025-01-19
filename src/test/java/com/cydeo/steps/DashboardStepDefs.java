package com.cydeo.steps;

import com.cydeo.pages.DashBoardPage;
import com.cydeo.pages.LoginPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class DashboardStepDefs
{
    String actualUserNumbers;
    String actualBookNumbers;
    String actualBorrowedBookNumbers;
    LoginPage loginPage=new LoginPage();
    DashBoardPage dashBoardPage=new DashBoardPage();


    @Given("the user logged in as {string}")
    public void the_user_logged_in_as(String user) {
        loginPage.login(user);
         BrowserUtil.waitFor(4);
    }
    @When("user gets all information from modules")
    public void user_gets_all_information_from_modules() {

        actualUserNumbers = dashBoardPage.usersNumber.getText();
        System.out.println("actualUserNumbers = " + actualUserNumbers);
        actualBookNumbers = dashBoardPage.booksNumber.getText();
        System.out.println("actualBookNumbers = " + actualBookNumbers);
        actualBorrowedBookNumbers = dashBoardPage.borrowedBooksNumber.getText();
        System.out.println("actualBorrowedBookNumbers = " + actualBorrowedBookNumbers);

    }
    @Then("the information should be same with database")
    public void the_information_should_be_same_with_database() {
        //Books
        //run query for of the result (3 different queries)
        DB_Util.runQuery("SELECT count(*) from books");

        String expectedBookNumbers = DB_Util.getFirstRowFirstColumn();
        System.out.println("expectedBookNumbers = " + expectedBookNumbers);



        //Users
        DB_Util.runQuery("SELECT COUNT(*) FROM book_borrow where is_returned = 0");
        String expectedBorrowedBooksNumbers = DB_Util.getFirstRowFirstColumn();


        //Borrowed books

        DB_Util.runQuery("SELECT COUNT(*) FROM users");
        String expectedUserNumbers = DB_Util.getFirstRowFirstColumn();

        //compare results with UI results(Actual result)
        Assert.assertEquals(actualBookNumbers,expectedBookNumbers);
        Assert.assertEquals(actualUserNumbers,expectedUserNumbers);
        Assert.assertEquals(actualBorrowedBookNumbers,expectedBorrowedBooksNumbers);


    }


}
