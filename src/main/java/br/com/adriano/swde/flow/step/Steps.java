package br.com.adriano.swde.flow.step;

public enum Steps {
    SHARE_HIGHEST_PRICE_MONTHLY_STEP(new SharesHighestPriceMonthlyStep());

    private Step step;

    public Step get() {
        return step;
    }

    Steps(Step step) {
        this.step = step;
    }
}
