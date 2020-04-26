package br.com.adriano.swde.flow.step;

public interface Step<T> {
    void run(T sparkContext);
}
