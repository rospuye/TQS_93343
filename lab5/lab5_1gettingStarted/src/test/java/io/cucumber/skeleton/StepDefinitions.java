package io.cucumber.skeleton;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {

    private Belly belly;

    @Given("I have {int} cukes in my belly")
    public void I_have_cukes_in_my_belly(int cukes) {
        this.belly = new Belly();
        this.belly.eat(cukes);
    }

    @When("I wait {int} hour")
    public void i_wait_hour(int hours) throws InterruptedException {
        System.out.println("Belly has waited " + hours + "hours");
    }

    @Then("my belly should growl")
    public void my_belly_should_growl() {
        System.out.println("GROWL");
    }
}
