package com.cydeo.steps;

import com.cydeo.pages.UsersPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;

public class UsersStepDefs {
    UsersPage usersPage = new UsersPage();
    String email;
    String expectedResult;
    Select dropdown = new Select(usersPage.statusDropdown);
    Select userDropdown = new Select(usersPage.userStatusDropdown);

    @When("the user clicks Edit User button")
    public void the_user_clicks_edit_user_button() {
        usersPage.editUser.click();

    }

    @When("the user changes user status {string} to {string}")
    public void the_user_changes_user_status_to(String string, String string2) {
        BrowserUtil.waitFor(2);
        email = usersPage.email.getAttribute("value");
        dropdown.selectByValue(string2);
        BrowserUtil.waitFor(2);
        expectedResult = dropdown.getFirstSelectedOption().getText();

    }

    @When("the user clicks save changes button")
    public void the_user_clicks_save_changes_button() {
        usersPage.saveChanges.click();
    }

    @Then("{string} message should appear")
    public void message_should_appear(String string) {
        BrowserUtil.waitFor(2);
        Assert.assertTrue(usersPage.toastMessage.isDisplayed());
    }

    @Then("the users should see same status for related user in database")
    public void the_users_should_see_same_status_for_related_user_in_database() {
        String query = "select status from users where email = '" + email + "'";
        DB_Util.runQuery(query);
        String actualResult = DB_Util.getFirstRowFirstColumn();
        Assert.assertEquals(expectedResult, actualResult);
    }
    @Then("the user changes user current status {string} to {string}")
    public void the_user_changes_user_current_status_to(String string, String string2) {
        BrowserUtil.waitFor(2);
        userDropdown.selectByValue(string);
        usersPage.searchField.sendKeys(email);
        BrowserUtil.waitFor(2);
        usersPage.editUser.click();
        BrowserUtil.waitFor(2);
        dropdown.selectByValue(string2);
        usersPage.saveChanges.click();
        String query = "select status from users where email = '" + email + "'";
        DB_Util.runQuery(query);
        System.out.println("Email "+email+" is "+DB_Util.getFirstRowFirstColumn());
    }
}
