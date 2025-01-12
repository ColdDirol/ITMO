package com.coursework.api_gateway_service

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class AuthorizationHeaderFilter : AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>() {

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            // Получение значения заголовка
            val headerValue = exchange.request.headers.getFirst("Authorization")

            // Проверка на наличие и правильность значения заголовка
            if (headerValue.isNullOrEmpty() || headerValue != config.expectedValue) {
                // Возвращаем ошибку 400, если заголовок отсутствует или его значение некорректно
                exchange.response.statusCode = HttpStatus.BAD_REQUEST
                return@GatewayFilter exchange.response.setComplete()
            }

            // Если заголовок валиден, продолжаем выполнение цепочки фильтров
            chain.filter(exchange)
        }
    }

    // Метод для создания новой конфигурации
    override fun newConfig(): Config {
        return Config()
    }

    // Метод для получения класса конфигурации
    override fun getConfigClass(): Class<Config> {
        return Config::class.java
    }

    // Конфигурационный класс для фильтра
    class Config(
        val expectedValue: String = "ExpectedValue"
    )
}