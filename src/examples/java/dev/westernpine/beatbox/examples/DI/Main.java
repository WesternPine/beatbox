package dev.westernpine.beatbox.examples.DI;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;

public class Main {

    public static class Test {
        public int i = 9990123;
    }

    @Inject
    public Main.Test test;

    public static void main(String[] args) {
        TestInterface testInterface = DaggerTestInterface.create();
        Main main = new Main();
        testInterface.inject(main);
        System.out.println(main.test.i);
    }
}

@Component(modules = TestModule.class)
interface TestInterface {
    void inject(Main main);
}

@Module
class TestModule {
    @Provides
    Main.Test provideTest() {
        return new Main.Test();
    }
}

