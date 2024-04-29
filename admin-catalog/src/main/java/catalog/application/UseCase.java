package catalog.application;

// Use Generic IN and OUT
public abstract class UseCase<IN, OUT> {

    // By default useCase implement the COMMAND
    // The Execute is the public method
    // Create..UseCase
    // Delete..UseCase
    public abstract OUT execute(IN aIn);

}