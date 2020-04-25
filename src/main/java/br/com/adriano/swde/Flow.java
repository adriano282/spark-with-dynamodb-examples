package br.com.adriano.swde;

public class Flow {

    public static FlowBuilder builder() {
        return new FlowBuilder();
    }

    public void run() {}

    public static class FlowBuilder {

        public FlowBuilder addStep(Step step) {
            return this;
        }

        public Flow build() {
            return new Flow();
        }
    }
}
