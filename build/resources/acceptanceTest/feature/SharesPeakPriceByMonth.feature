Feature: Testing the stock shares peak price by month


  Background:

    Given there were registered these shares prices history:
        | exchange | stockSymbol  | date       | stockPriceOpen   | stockPriceHigh   | stockPriceLow   | stockPriceClose   | stockVolume  | stockPriceAdjClose    |
        | NYSE     | ASP          | 2001-12-31 | 12.55            | 12.8             | 12.42           | 12.8              | 11300        | 6.91                  |
        | NYSE     | ASP          | 2001-12-28 | 12.5             | 12.55            | 12.42           | 12.55             | 4800         | 6.78                  |
        | NYSE     | ASP          | 2001-12-27 | 12.59            | 12.59            | 12.5            | 12.57             | 5400         | 6.79                  |
        | NYSE     | ASP          | 2001-12-26 | 12.45            | 12.6             | 12.45           | 12.55             | 5400         | 6.78                  |
        | NYSE     | ASP          | 2001-12-24 | 12.61            | 12.61            | 12.61           | 12.61             | 1400         | 6.76                  |
        | NYSE     | ASP          | 2001-12-21 | 50.4             | 50.4            | 12.4            | 12.6              | 18200        | 6.75                  |
        | NYSE     | ASP          | 2001-12-20 | 12.35            | 12.58            | 12.35           | 12.4              | 4200         | 6.65                  |
        | NYSE     | ASP          | 2001-12-19 | 12.42            | 12.6             | 12.35           | 12.6              | 10100        | 6.75                  |
        | NYSE     | ASP          | 2001-12-18 | 12.37            | 12.5             | 12.37           | 12.41             | 10100        | 6.65                  |
        | NYSE     | ASP          | 2001-11-17 | 59.4             | 59.4            | 12.4            | 12.52             | 8000         | 6.71                  |
        | NYSE     | ASP          | 2001-11-14 | 12.54            | 12.54            | 12.32           | 12.4              | 283000       | 6.65                  |
        | NYSE     | ASP          | 2001-12-13 | 12.4             | 12.55            | 12.4            | 12.54             | 13700        | 6.72                  |
        | NYSE     | ASP          | 2001-12-12 | 12.55            | 12.55            | 12.4            | 12.4              | 6900         | 6.65                  |
        | NYSE     | ASP          | 2001-12-11 | 12.6             | 12.6             | 12.45           | 12.55             | 8900         | 6.73                  |
        | NYSE     | ASP          | 2001-12-10 | 12.5             | 12.6             | 12.43           | 12.6              | 4400         | 6.75                  |
        | NYSE     | ASP          | 2001-12-07 | 12.6             | 12.65            | 12.43           | 12.6              | 9600         | 6.75                  |
        | NYSE     | ASP          | 2001-12-06 | 12.7             | 12.71            | 12.65           | 12.65             | 3400         | 6.78                  |
        | NYSE     | ASP          | 2001-12-05 | 12.63            | 12.81            | 12.45           | 12.7              | 15800        | 6.81                  |
        | NYSE     | ASP          | 2001-12-04 | 12.79            | 12.79            | 12.6            | 12.65             | 10800        | 6.78                  |

  Scenario: Successfully run the job

    When the sharesHighestPriceMonthly job run

    Then should be saved the following result
        | stockShareSymbol | month   | peakPrice |
        | ASP              | 2001-11 |  59.4     |
        | ASP              | 2001-12 |  50.4     |
