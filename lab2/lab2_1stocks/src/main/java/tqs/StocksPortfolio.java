package tqs;

import java.util.List;
import java.util.ArrayList;

public class StocksPortfolio {

    private List<Stock> stocks;
    private IStockmarketService stockmarket;

    public StocksPortfolio(IStockmarketService stockmarket) {
        this.stockmarket = stockmarket;
        this.stocks = new ArrayList<Stock>();
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }
    public double getTotalValue() {
        double ret = 0;
        for (Stock stock : this.stocks) {
            ret += this.stockmarket.lookUpPrice(stock.getLabel());
        }
        return ret;
    }
    
}
