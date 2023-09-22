package ru.andreynaz4renko.tests.commands;
public abstract class CommandTest {

    public abstract void setUp();

    public abstract void testExecuteSuccess();

    public abstract void testExecuteFailure();

    public abstract void testExecuteWithArgs();

}
