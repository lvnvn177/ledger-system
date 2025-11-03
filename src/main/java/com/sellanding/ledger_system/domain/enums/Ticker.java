package com.sellanding.ledger_system.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Ticker {
    SPY("SPY", "SPDR S&P 500 ETF Trust", "S&P 500 지수를 추종합니다.", null),
    IVV("IVV", "iShares CORE S&P 500 ETF", "S&P 500 지수를 추종합니다.", null),
    VOO("VOO", "Vanguard S&P 500 ETF", "S&P 500 지수를 추종합니다.", null),
    VTI("VTI", "Vanguard Total Stock Market ETF", "미국 전체 주식 시장을 추종합니다.", null),
    VXUS("VXUS", "Vanguard Total International Stock ETF", "미국 외 선진국 및 신흥 시장 주식을 추종합니다.", null),
    BND("BND", "Vanguard Total Bond Market ETF", "미국 투자 등급 채권 시장을 추종합니다.", null),
    GLD("GLD", "SPDR Gold Shares", "금 가격을 추종합니다.", null),
    QQQ("QQQ", "Invesco QQQ Trust", "나스닥-100 지수를 추종합니다.", Sector.TECHNOLOGY),
    IEMG("IEMG", "iShares Core MSCI Emerging Markets ETF", "신흥 시장 주식을 추종합니다.", null),
    VWO("VWO", "Vanguard FTSE Emerging Markets ETF", "신흥 시장 주식을 추종합니다.", null),
    EFA("EFA", "iShares MSCI EAFE ETF", "유럽, 호주, 극동 지역의 선진국 시장 주식을 추종합니다.", null),
    AGG("AGG", "iShares Core U.S. Aggregate Bond ETF", "미국 투자 등급 채권 시장을 추종합니다.", null),
    LQD("LQD", "iShares iBoxx $ Investment Grade Corporate Bond ETF", "미국 투자 등급 회사채를 추종합니다.", null),
    HYG("HYG", "iShares iBoxx $ High Yield Corporate Bond ETF", "미국 고수익 회사채를 추종합니다.", null),
    VNQ("VNQ", "Vanguard Real Estate ETF", "미국 부동산 시장을 추종합니다.", Sector.REAL_ESTATE),

    // TECHNOLOGY
    VGT("VGT", "Vanguard Information Technology ETF", "미국 IT 기업에 투자합니다.", Sector.TECHNOLOGY),
    XLK("XLK", "Technology Select Sector SPDR Fund", "S&P 500의 기술 부문 기업에 투자합니다.", Sector.TECHNOLOGY),
    IXN("IXN", "iShares Global Tech ETF", "전세계 기술 기업에 투자합니다.", Sector.TECHNOLOGY),

    // HEALTHCARE
    VHT("VHT", "Vanguard Health Care ETF", "미국 헬스케어 기업에 투자합니다.", Sector.HEALTHCARE),
    XLV("XLV", "Health Care Select Sector SPDR Fund", "S&P 500의 헬스케어 부문 기업에 투자합니다.", Sector.HEALTHCARE),
    IYH("IYH", "iShares U.S. Healthcare ETF", "미국 헬스케어 기업에 투자합니다.", Sector.HEALTHCARE),

    // FINANCIALS
    VFH("VFH", "Vanguard Financials ETF", "미국 금융 기업에 투자합니다.", Sector.FINANCIALS),
    XLF("XLF", "Financial Select Sector SPDR Fund", "S&P 500의 금융 부문 기업에 투자합니다.", Sector.FINANCIALS),
    IYF("IYF", "iShares U.S. Financials ETF", "미국 금융 기업에 투자합니다.", Sector.FINANCIALS),

    // CONSUMER_DISCRETIONARY
    VCR("VCR", "Vanguard Consumer Discretionary ETF", "미국 소비재 기업에 투자합니다.", Sector.CONSUMER_DISCRETIONARY),
    XLY("XLY", "Consumer Discretionary Select Sector SPDR Fund", "S&P 500의 소비재 부문 기업에 투자합니다.", Sector.CONSUMER_DISCRETIONARY),
    IYC("IYC", "iShares U.S. Consumer Discretionary ETF", "미국 소비재 기업에 투자합니다.", Sector.CONSUMER_DISCRETIONARY),

    // COMMUNICATION_SERVICES
    VOX("VOX", "Vanguard Communication Services ETF", "미국 통신 서비스 기업에 투자합니다.", Sector.COMMUNICATION_SERVICES),
    XLC("XLC", "Communication Services Select Sector SPDR Fund", "S&P 500의 통신 서비스 부문 기업에 투자합니다.", Sector.COMMUNICATION_SERVICES),
    IXP("IXP", "iShares Global Comm Services ETF", "전세계 통신 서비스 기업에 투자합니다.", Sector.COMMUNICATION_SERVICES),

    // INDUSTRIALS
    VIS("VIS", "Vanguard Industrials ETF", "미국 산업재 기업에 투자합니다.", Sector.INDUSTRIALS),
    XLI("XLI", "Industrial Select Sector SPDR Fund", "S&P 500의 산업재 부문 기업에 투자합니다.", Sector.INDUSTRIALS),
    IYJ("IYJ", "iShares U.S. Industrials ETF", "미국 산업재 기업에 투자합니다.", Sector.INDUSTRIALS),

    // CONSUMER_STAPLES
    VDC("VDC", "Vanguard Consumer Staples ETF", "미국 필수소비재 기업에 투자합니다.", Sector.CONSUMER_STAPLES),
    XLP("XLP", "Consumer Staples Select Sector SPDR Fund", "S&P 500의 필수소비재 부문 기업에 투자합니다.", Sector.CONSUMER_STAPLES),
    IYK("IYK", "iShares U.S. Consumer Staples ETF", "미국 필수소비재 기업에 투자합니다.", Sector.CONSUMER_STAPLES),

    // ENERGY
    VDE("VDE", "Vanguard Energy ETF", "미국 에너지 기업에 투자합니다.", Sector.ENERGY),
    XLE("XLE", "Energy Select Sector SPDR Fund", "S&P 500의 에너지 부문 기업에 투자합니다.", Sector.ENERGY),
    IXC("IXC", "iShares Global Energy ETF", "전세계 에너지 기업에 투자합니다.", Sector.ENERGY),

    // UTILITIES
    VPU("VPU", "Vanguard Utilities ETF", "미국 유틸리티 기업에 투자합니다.", Sector.UTILITIES),
    XLU("XLU", "Utilities Select Sector SPDR Fund", "S&P 500의 유틸리티 부문 기업에 투자합니다.", Sector.UTILITIES),
    IDU("IDU", "iShares U.S. Utilities ETF", "미국 유틸리티 기업에 투자합니다.", Sector.UTILITIES),

    // MATERIALS
    VAW("VAW", "Vanguard Materials ETF", "미국 소재 기업에 투자합니다.", Sector.MATERIALS),
    XLB("XLB", "Materials Select Sector SPDR Fund", "S&P 500의 소재 부문 기업에 투자합니다.", Sector.MATERIALS),
    IYM("IYM", "iShares U.S. Materials ETF", "미국 소재 기업에 투자합니다.", Sector.MATERIALS);

    private final String code;
    private final String name;
    private final String description;
    private final Sector sector;
}