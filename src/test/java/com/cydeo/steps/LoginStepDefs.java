package com.cydeo.steps;

import com.cydeo.pages.DashBoardPage;
import com.cydeo.pages.LoginPage;
import com.cydeo.utility.BrowserUtil;


import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LoginStepDefs {
    LoginPage loginPage = new LoginPage();
    DashBoardPage dashBoardPage = new DashBoardPage();
    String actualUserName;
    String email;

    @Given("the user logged in {string} and {string}")
    public void the_user_logged_in_and(String email, String password) {
        this.email = email;
        loginPage.login(email,password);
        BrowserUtil.waitFor(2);

    }
    @When("user gets username from user fields")
    public void user_gets_username_from_user_fields() {
        actualUserName = dashBoardPage.accountHolderName.getText();
    }

    @Then("the username should be same with database")
    public void the_username_should_be_same_with_database() {
        //get data from db
        DB_Util.runQuery("select full_name from users where email='"+email+"'");
        String expectedUsername = DB_Util.getFirstRowFirstColumn();
        Assert.assertEquals(actualUserName,expectedUsername);

    }
}
