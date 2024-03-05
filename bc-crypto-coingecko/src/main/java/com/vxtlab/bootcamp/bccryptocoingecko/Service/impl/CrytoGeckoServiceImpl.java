package com.vxtlab.bootcamp.bccryptocoingecko.Service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vxtlab.bootcamp.bccryptocoingecko.Service.CryptoGeckoService;
import com.vxtlab.bootcamp.bccryptocoingecko.Service.RedisService;
import com.vxtlab.bootcamp.bccryptocoingecko.dto.jph.Coin;
import com.vxtlab.bootcamp.bccryptocoingecko.dto.jph.Market;
import com.vxtlab.bootcamp.bccryptocoingecko.exception.InvalidCoinException;
import com.vxtlab.bootcamp.bccryptocoingecko.infra.CoinId;
import com.vxtlab.bootcamp.bccryptocoingecko.infra.Currency;
import com.vxtlab.bootcamp.bccryptocoingecko.infra.Scheme;
import com.vxtlab.bootcamp.bccryptocoingecko.infra.Syscode;
import com.vxtlab.bootcamp.bccryptocoingecko.mapper.UriCompBuilder;


@Service
public class CrytoGeckoServiceImpl implements CryptoGeckoService {

        @Value(value = "${api.jph.domain}")
        private String domain;

        @Value(value = "${api.jph.basepath}")
        private String basepath;

        @Value(value = "${api.jph.endpoints.markets}")
        private String endpointMarkets;

        @Value(value = "${api.jph.endpoints.list}")
        private String endpointCoins;

        @Value(value = "${api.jph.key}")
        private String key;

        @Autowired
        private RestTemplate restTemplate;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private RedisService redisService;

        @Override
        public List<Market> getMarkets(Currency cur, String... ids)
                        throws Exception {

                List<Market> marketList = new ArrayList<>();

                if (ids == null) {
                        String urlString = UriCompBuilder.url(Scheme.HTTPS,
                                        domain, basepath, endpointMarkets, cur,
                                        key);
                        Market[] markets = restTemplate.getForObject(urlString,
                                        Market[].class);

                        // String markets = restTemplate.getForObject(urlString,String.class);
                        // System.out.println(markets));

                        return Arrays.stream(markets)
                                        .collect(Collectors.toList());
                } else {

                        for (String id : ids) {
                                String redisKey = new StringBuilder(
                                                "crytpo:coingecko:coin-markets:")
                                                                .append(cur.name()
                                                                                .toLowerCase()) //
                                                                .append(":")//
                                                                .append(id)//
                                                                .toString();
                                // System.out.println("key=" + redisKey);
                                String value = "";

                                try {
                                        value = redisService.getValue(redisKey);
                                } catch (Exception ex) {

                                }

                                if (value == null || value.isEmpty()) {

                                        // System.out.println("coin=" + id);

                                        List<Coin> coins = this.getCoins();

                                        if (!(Coin.isValidCoin(coins, id))) {
                                                // System.out.println("Not valid Coin");
                                                throw new InvalidCoinException(
                                                                Syscode.INVALID_COIN);
                                        }
                                        // System.out.println("Valid Coin");

                                        String urlString = UriCompBuilder.url(
                                                        Scheme.HTTPS, domain,
                                                        basepath,
                                                        endpointMarkets, cur,
                                                        key, ids);

                                        // System.out.println(urlString);

                                        Market[] markets = restTemplate
                                                        .getForObject(urlString,
                                                                        Market[].class);

                                        // System.out.println(Arrays
                                        //                 .toString(markets));

                                        return Arrays.stream(markets).collect(
                                                        Collectors.toList());
                                }
                                // System.out.println("value from redis=" + value);
                                Market market = objectMapper.readValue(value,
                                                Market.class);
                                marketList.add(market);
                        }

                        return marketList;
                }

        }

        @Override
        public void getDataToRedis()
                        throws JsonProcessingException, RestClientException {

                for (Currency cur : Currency.values()) {

                        String currency = cur.name().toLowerCase();

                        for (CoinId coin : CoinId.values()) {
                                String coinId = coin.getId();

                                List<Market> markets =
                                                this.getMarket(cur, coinId);

                                String key = new StringBuilder(
                                                "crytpo:coingecko:coin-markets:")
                                                                .append(currency)
                                                                .append(":")
                                                                .append(coinId)
                                                                .toString();

                                Market market = markets.get(0);
                                String serializedValue = objectMapper
                                                .writeValueAsString(market);
                                redisService.setValue(key, serializedValue);

                        }

                }

        }

        @Override
        public List<Coin> getCoins() {

                String urlString = UriCompBuilder.url(Scheme.HTTPS, domain,
                                basepath, endpointCoins, key);

                Coin[] coins = restTemplate.getForObject(urlString,
                                Coin[].class);

                return Arrays.stream(coins).collect(Collectors.toList());
        }

        private List<Market> getMarket(Currency cur, String id) {

                String urlString = UriCompBuilder.url(Scheme.HTTPS, domain,
                                basepath, endpointMarkets, cur, key, id);

                Market[] market = restTemplate.getForObject(urlString,
                                Market[].class);
                return Arrays.stream(market).collect(Collectors.toList());

        }



}
