package tqs;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

public class IStockmarketServiceTest {

    @Mock
    IStockmarketService stockMOCKet = mock(IStockmarketService.class);

    private StocksPortfolio portfolio;

    @BeforeEach
    public void setUp() {
        Stock stock1 = new Stock("stock1", 1);
        Stock stock2 = new Stock("stock2", 2);
        Stock stock3 = new Stock("stock3", 3);
        this.portfolio = new StocksPortfolio(stockMOCKet);
        this.portfolio.addStock(stock1);
        this.portfolio.addStock(stock2);
        this.portfolio.addStock(stock3);
    }

    @Test
    public void lookUpPriceTest() {

        when(stockMOCKet.lookUpPrice(any(String.class))).thenReturn(1.0);

        double mockStockValue = portfolio.getTotalValue();
        double actualStockValue = 3.0;

        // assertEquals(mockStockValue, actualStockValue);
        assertThat(actualStockValue, is(closeTo(mockStockValue,0)));
        Mockito.verify(stockMOCKet, times(3)).lookUpPrice(any(String.class));

    }

    @AfterEach
    public void cleanUp() {
        this.portfolio = null;
    }


}
