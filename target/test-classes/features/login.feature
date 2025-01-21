Feature: Login Functionality

   @db
  Scenario: Login with valid credential
    Given the user logged in "librarian56@library" and "libraryUser"
    When user gets username from user fields
    Then the username should be same with database
    @db
  Scenario Outline: Login with valid credential
    Given the user logged in "<email>" and "<password>"
    When user gets username from user fields
    Then the username should be same with database
    Examples:
      | email               | password    |
      | librarian55@library | libraryUser |
      | librarian56@library | libraryUser |
      | librarian57@library | libraryUser |
