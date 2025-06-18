#Feature: Validate chatbot intent and response
#  Scenario: Booking an appointment
#    Given I launch the chatbot UI
#    When I enter "I want to book an appointment"
#    Then I should see the bot respond with "Sure, who would you like to see?"
Feature: Validate chatbot intent and response
  Scenario: Booking an appointment
    Given I launch the chatbot UI
    When I enter "I want to book an appointment"
    Then I should see the bot respond with "Sure, who would you like to see?, Intent Book_Appointment"
