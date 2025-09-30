package com.sellanding.ledger_system.domain.enums;



/*
 * 미국 주요 ETF 티커 
 */
public enum AssetTicker {
    SPY("SPY", "SPDR S&P 500 ETF", "S&P 500 지수 추종"),
    QQQ("QQQ", "Invesco QQQ Trust", "나스닥 100 지수 추종"),
    IWM("IWM", "iShares Russell 2000 ETF", "소형주 지수 추종"),
    DIA("DIA", "SPDR Dow Jones Industrial Average ETF", "다우존스 지수 추종"),
    VTI("VTI", "Vanguard Total Stock Market ETF", "미국 전체 주식시장 추종"),
    
    // 섹터 ETF
    XLF("XLF", "Financial Select Sector SPDR Fund", "금융 섹터"),
    XLK("XLK", "Technology Select Sector SPDR Fund", "기술 섹터"),
    XLE("XLE", "Energy Select Sector SPDR Fund", "에너지 섹터"),
    
    // 채권 ETF
    AGG("AGG", "iShares Core U.S. Aggregate Bond ETF", "미국 채권"),
    TLT("TLT", "iShares 20+ Year Treasury Bond ETF", "장기 국채");
    
    private final String ticker;
    private final String name;
    private final String description;

    AssetTicker(String ticker, String name, String description){
        this.ticker = ticker;
        this.name = name;
        this.description = description;
    }

    public String getTicker() {
        return ticker;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static AssetTicker fromTicker(String ticker) {
        for (AssetTicker assetTicker : AssetTicker.values()) {
            if (assetTicker.getTicker().equals(ticker)) {
                return assetTicker;
            }
        }

        throw new IllegalArgumentException("Unknown asset ticker: " + ticker);
    }
    
}
