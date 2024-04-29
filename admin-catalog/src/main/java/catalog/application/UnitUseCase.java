package catalog.application;

// Receive something and return NOTHING
public abstract class UnitUseCase<IN> {

    public abstract void execute(IN anIn);
}
