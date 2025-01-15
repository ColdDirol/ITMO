//package com.coursework.user_management_service.config
//
//import org.springframework.context.annotation.Configuration
//import org.springframework.web.servlet.config.annotation.CorsRegistry
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
//
//@Configuration
//class CorsConfig : WebMvcConfigurer {
//
//    override fun addCorsMappings(registry: CorsRegistry) {
//        registry.addMapping("/**") // разрешает все маршруты
//            .allowedOriginPatterns(
//                "http://localhost:5173"
//            ) // разрешает все домены с http и https
//            .allowedMethods("GET", "POST", "PUT", "DELETE") // разрешенные методы
//            .allowedHeaders("*") // разрешенные заголовки
//            .exposedHeaders("*") // выставленные заголовки
//            .allowCredentials(true) // разрешение на отправку куки
//    }
//}
